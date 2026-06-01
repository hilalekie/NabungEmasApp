package com.example.nabungemas.data

import java.util.UUID

data class SavingGoal(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val targetGrams: Double,
    val accumulatedGrams: Double = 0.0,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
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
