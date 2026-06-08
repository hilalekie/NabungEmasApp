package com.example.nabungemas.data

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NabungEmasRepository {

    private val _goals = MutableStateFlow<List<SavingGoal>>(emptyList())
    val goals: StateFlow<List<SavingGoal>> = _goals.asStateFlow()

    private val _transactions = MutableStateFlow<List<GoldTransaction>>(emptyList())
    val transactions: StateFlow<List<GoldTransaction>> = _transactions.asStateFlow()

    private val _currentPrice = MutableStateFlow(
        GoldPrice(
            pricePerGramIdr = 1215000.0,
            pricePerGramUsd = 78.5,
            changePercentage = 1.45 // +1.45% today
        )
    )
    val currentPrice: StateFlow<GoldPrice> = _currentPrice.asStateFlow()

    // 7-day history points
    val priceHistory7d = listOf(
        PriceHistoryPoint("19 Mei", 1195000.0),
        PriceHistoryPoint("20 Mei", 1200000.0),
        PriceHistoryPoint("21 Mei", 1198000.0),
        PriceHistoryPoint("22 Mei", 1205000.0),
        PriceHistoryPoint("23 Mei", 1212000.0),
        PriceHistoryPoint("24 Mei", 1210000.0),
        PriceHistoryPoint("25 Mei", 1215000.0)
    )

    // 30-day history points
    val priceHistory30d = listOf(
        PriceHistoryPoint("W1", 1170000.0),
        PriceHistoryPoint("W2", 1185000.0),
        PriceHistoryPoint("W3", 1195000.0),
        PriceHistoryPoint("W4", 1215000.0)
    )

    suspend fun fetchGoals() {
        val user = SupabaseClient.client.auth.currentUserOrNull() ?: return
        try {
            val result = SupabaseClient.client.postgrest["savings"].select {
                filter {
                    eq("user_id", user.id)
                }
            }.decodeList<SavingGoal>()
            _goals.value = result
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getGoalById(id: String): SavingGoal? {
        return _goals.value.find { it.id == id }
    }

    fun getTransactionById(id: String): GoldTransaction? {
        return _transactions.value.find { it.id == id }
    }

    fun getTransactionsForGoal(goalId: String): Flow<List<GoldTransaction>> {
        return transactions.map { list -> list.filter { it.goalId == goalId } }
    }

    suspend fun addGoal(title: String, targetGrams: Double, description: String): String {
        val user = SupabaseClient.client.auth.currentUserOrNull() ?: return ""
        return try {
            // Insert menggunakan JSON object agar tidak mengirimkan field 'id' (biar Supabase yang generate)
            val inserted = SupabaseClient.client.postgrest["savings"].insert(
                kotlinx.serialization.json.buildJsonObject {
                    put("user_id", kotlinx.serialization.json.JsonPrimitive(user.id))
                    put("name", kotlinx.serialization.json.JsonPrimitive(title))
                    put("target_gram", kotlinx.serialization.json.JsonPrimitive(targetGrams))
                    put("current_gram", kotlinx.serialization.json.JsonPrimitive(0.0))
                    put("is_active", kotlinx.serialization.json.JsonPrimitive(true))
                    if (description.isNotBlank()) {
                        put("description", kotlinx.serialization.json.JsonPrimitive(description))
                    }
                }
            ) {
                select()
            }.decodeSingle<SavingGoal>()
            fetchGoals()
            inserted.id
        } catch (e: Exception) {
            e.printStackTrace()
            throw e // lempar error agar UI bisa menampilkan pesan
        }
    }

    suspend fun updateGoal(id: String, title: String, targetGrams: Double, description: String) {
        val user = SupabaseClient.client.auth.currentUserOrNull() ?: return
        try {
            SupabaseClient.client.postgrest["savings"].update(
                {
                    set("name", title)
                    set("target_gram", targetGrams)
                    set("description", description)
                }
            ) {
                filter {
                    eq("id", id)
                    eq("user_id", user.id)
                }
            }
            fetchGoals()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteGoal(id: String) {
        val user = SupabaseClient.client.auth.currentUserOrNull() ?: return
        try {
            SupabaseClient.client.postgrest["savings"].delete {
                filter {
                    eq("id", id)
                    eq("user_id", user.id)
                }
            }
            _transactions.value = _transactions.value.filterNot { it.goalId == id }
            fetchGoals()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun addTransaction(goalId: String, grams: Double, pricePerGram: Double, date: Long, notes: String): String {
        val goal = getGoalById(goalId) ?: return ""
        val newTx = GoldTransaction(
            goalId = goalId,
            goalTitle = goal.title,
            grams = grams,
            pricePerGram = pricePerGram,
            date = date,
            notes = notes
        )
        _transactions.value = (listOf(newTx) + _transactions.value).sortedByDescending { it.date }
        recalculateGoalAccumulatedGrams(goalId)
        return newTx.id
    }

    suspend fun updateTransaction(
        id: String,
        goalId: String,
        grams: Double,
        pricePerGram: Double,
        date: Long,
        notes: String
    ) {
        val oldTx = getTransactionById(id) ?: return
        val targetGoal = getGoalById(goalId) ?: return

        _transactions.value = _transactions.value.map { tx ->
            if (tx.id == id) {
                tx.copy(
                    goalId = goalId,
                    goalTitle = targetGoal.title,
                    grams = grams,
                    pricePerGram = pricePerGram,
                    date = date,
                    notes = notes
                )
            } else {
                tx
            }
        }.sortedByDescending { it.date }

        recalculateGoalAccumulatedGrams(goalId)
        if (oldTx.goalId != goalId) {
            recalculateGoalAccumulatedGrams(oldTx.goalId)
        }
    }

    suspend fun deleteTransaction(id: String) {
        val tx = getTransactionById(id) ?: return
        _transactions.value = _transactions.value.filterNot { it.id == id }
        recalculateGoalAccumulatedGrams(tx.goalId)
    }

    private suspend fun recalculateGoalAccumulatedGrams(goalId: String) {
        val sum = _transactions.value
            .filter { it.goalId == goalId }
            .sumOf { it.grams }

        val user = SupabaseClient.client.auth.currentUserOrNull() ?: return
        try {
            SupabaseClient.client.postgrest["savings"].update(
                {
                    set("current_gram", sum)
                }
            ) {
                filter {
                    eq("id", goalId)
                    eq("user_id", user.id)
                }
            }
            fetchGoals()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refreshPrice() {
        val current = _currentPrice.value
        // Simulate minor fluctuation
        val variance = (Math.random() - 0.48) * 5000.0 // +/- 2500 IDR
        val newPrice = current.pricePerGramIdr + variance
        val percent = (newPrice - 1200000.0) / 1200000.0 * 100.0
        _currentPrice.value = GoldPrice(
            pricePerGramIdr = newPrice,
            pricePerGramUsd = newPrice / 15500.0,
            changePercentage = percent,
            lastUpdated = System.currentTimeMillis()
        )
    }

    companion object {
        // Singleton instance helper for dependency injection/Viewmodel usage
        val INSTANCE = NabungEmasRepository()
    }
}
