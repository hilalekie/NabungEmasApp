package com.example.nabungemas.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NabungEmasRepository {


    // Pre-populate mock data
    private val _goals = MutableStateFlow<List<SavingGoal>>(initialGoals)
    val goals: StateFlow<List<SavingGoal>> = _goals.asStateFlow()

    private val _transactions = MutableStateFlow<List<GoldTransaction>>(initialTransactions)
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
        val newGoal = SavingGoal(
            title = title,
            targetGrams = targetGrams,
            description = description
        )
        _goals.value = _goals.value + newGoal
        return newGoal.id
    }

    suspend fun updateGoal(id: String, title: String, targetGrams: Double, description: String) {
        _goals.value = _goals.value.map { goal ->
            if (goal.id == id) {
                goal.copy(
                    title = title,
                    targetGrams = targetGrams,
                    description = description
                )
            } else {
                goal
            }
        }
    }

    suspend fun deleteGoal(id: String) {
        // Remove target and all associated transactions
        _goals.value = _goals.value.filterNot { it.id == id }
        _transactions.value = _transactions.value.filterNot { it.goalId == id }
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

    private fun recalculateGoalAccumulatedGrams(goalId: String) {
        val sum = _transactions.value
            .filter { it.goalId == goalId }
            .sumOf { it.grams }

        _goals.value = _goals.value.map { goal ->
            if (goal.id == goalId) {
                goal.copy(accumulatedGrams = sum)
            } else {
                goal
            }
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
        private val goalId1 = "goal-1"
        private val goalId2 = "goal-2"
        private val goalId3 = "goal-3"

        private val initialGoals = listOf(
            SavingGoal(
                id = goalId1,
                title = "Dana Darurat Emas",
                targetGrams = 10.0,
                accumulatedGrams = 4.5,
                description = "Tabungan emas khusus dana darurat pribadi."
            ),
            SavingGoal(
                id = goalId2,
                title = "Pernikahan Impian",
                targetGrams = 50.0,
                accumulatedGrams = 12.0,
                description = "Persiapan dana mahar dan pesta pernikahan 2027."
            ),
            SavingGoal(
                id = goalId3,
                title = "Beli Laptop Baru",
                targetGrams = 5.0,
                accumulatedGrams = 5.0, // Completed!
                description = "Tabungan untuk upgrade laptop kerja akhir tahun."
            )
        )

        private val initialTransactions = listOf(
            GoldTransaction(
                id = "tx-1",
                goalId = goalId1,
                goalTitle = "Dana Darurat Emas",
                grams = 2.5,
                pricePerGram = 1210000.0,
                date = System.currentTimeMillis() - 86400000L * 1L, // 1 day ago
                notes = "Bonus bulanan disisihkan"
            ),
            GoldTransaction(
                id = "tx-2",
                goalId = goalId2,
                goalTitle = "Pernikahan Impian",
                grams = 5.0,
                pricePerGram = 1205000.0,
                date = System.currentTimeMillis() - 86400000L * 3L, // 3 days ago
                notes = "Tabungan berkala Mei"
            ),
            GoldTransaction(
                id = "tx-3",
                goalId = goalId1,
                goalTitle = "Dana Darurat Emas",
                grams = 2.0,
                pricePerGram = 1200000.0,
                date = System.currentTimeMillis() - 86400000L * 5L, // 5 days ago
                notes = "Beli saat harga turun"
            ),
            GoldTransaction(
                id = "tx-4",
                goalId = goalId2,
                goalTitle = "Pernikahan Impian",
                grams = 7.0,
                pricePerGram = 1195000.0,
                date = System.currentTimeMillis() - 86400000L * 10L, // 10 days ago
                notes = "Awal nabung nikah"
            ),
            GoldTransaction(
                id = "tx-5",
                goalId = goalId3,
                goalTitle = "Beli Laptop Baru",
                grams = 5.0,
                pricePerGram = 1180000.0,
                date = System.currentTimeMillis() - 86400000L * 15L, // 15 days ago
                notes = "Sekaligus lunasi target gadget"
            )
        ).sortedByDescending { it.date }

        // Singleton instance helper for dependency injection/Viewmodel usage
        val INSTANCE = NabungEmasRepository()
    }
}
