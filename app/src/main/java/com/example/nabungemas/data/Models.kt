package com.example.nabungemas.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SavingGoal(
    @SerialName("id") val id: String = UUID.randomUUID().toString(),
    @SerialName("user_id") val userId: String = "",
    @SerialName("name") val title: String,
    @SerialName("description") val description: String? = null,
    @SerialName("target_gram") val targetGrams: Double,
    @SerialName("current_gram") val accumulatedGrams: Double = 0.0,
    @SerialName("is_active") val isActive: Boolean = true
) {
    val progress: Float
        get() = if (targetGrams > 0) (accumulatedGrams / targetGrams).toFloat().coerceIn(0f, 1f) else 0f

    val isCompleted: Boolean
        get() = accumulatedGrams >= targetGrams
}

data class GoldTransaction(
    val id: String = UUID.randomUUID().toString(),
    val goalId: String,
    val goalTitle: String,
    val grams: Double,
    val pricePerGram: Double,
    val date: Long = System.currentTimeMillis(),
    val notes: String = ""
) {
    val totalNominal: Double
        get() = grams * pricePerGram
}

data class GoldPrice(
    val pricePerGramIdr: Double,
    val pricePerGramUsd: Double,
    val changePercentage: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class PriceHistoryPoint(
    val dateLabel: String,
    val priceIdr: Double
)
