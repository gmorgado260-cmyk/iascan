package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val country: String,
    val denomination: String,
    val year: String,
    val mint: String,
    val composition: String,
    val variant: String,
    val rarity: String,
    val estimatedValueRange: String,
    val estimatedValueMin: Double,
    val estimatedValueMax: Double,
    val confidence: Float,
    val conservationState: String,
    val whyInteresting: String,
    val visualFeaturesString: String = "",
    val mintingErrorsString: String = "",
    val valueFactorsString: String = "",
    val obverseImagePath: String? = null,
    val reverseImagePath: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val isInCollection: Boolean = false,
    val userNotes: String? = null
) {
    fun toAnalysisResult(): CoinAnalysisResult {
        val tier = when {
            confidence >= 0.90f -> ConfidenceTier.HIGH
            confidence >= 0.70f -> ConfidenceTier.GOOD
            confidence >= 0.50f -> ConfidenceTier.LOW
            else -> ConfidenceTier.INCONCLUSIVE
        }

        return CoinAnalysisResult(
            country = country,
            denomination = denomination,
            year = year,
            mint = mint,
            composition = composition,
            variant = variant,
            rarity = rarity,
            estimatedValueMin = estimatedValueMin,
            estimatedValueMax = estimatedValueMax,
            currency = "R$",
            confidence = confidence,
            confidenceTier = tier,
            conservationState = conservationState,
            visualFeatures = if (visualFeaturesString.isBlank()) emptyList() else visualFeaturesString.split("||"),
            mintingErrors = if (mintingErrorsString.isBlank()) emptyList() else mintingErrorsString.split("||"),
            whyInteresting = whyInteresting,
            valueFactors = if (valueFactorsString.isBlank()) emptyList() else valueFactorsString.split("||"),
            similarCoins = emptyList(),
            isInconclusive = confidence < 0.50f,
            obverseImagePath = obverseImagePath,
            reverseImagePath = reverseImagePath
        )
    }

    companion object {
        fun fromAnalysisResult(result: CoinAnalysisResult, inCollection: Boolean = false, favorite: Boolean = false, notes: String? = null): CoinEntity {
            val title = "${result.denomination} ${result.year}".trim()
            return CoinEntity(
                title = title.ifBlank { "Moeda identificada" },
                country = result.country,
                denomination = result.denomination,
                year = result.year,
                mint = result.mint,
                composition = result.composition,
                variant = result.variant,
                rarity = result.rarity,
                estimatedValueRange = result.estimatedValueRangeFormatted,
                estimatedValueMin = result.estimatedValueMin,
                estimatedValueMax = result.estimatedValueMax,
                confidence = result.confidence,
                conservationState = result.conservationState,
                whyInteresting = result.whyInteresting,
                visualFeaturesString = result.visualFeatures.joinToString("||"),
                mintingErrorsString = result.mintingErrors.joinToString("||"),
                valueFactorsString = result.valueFactors.joinToString("||"),
                obverseImagePath = result.obverseImagePath,
                reverseImagePath = result.reverseImagePath,
                timestamp = System.currentTimeMillis(),
                isFavorite = favorite,
                isInCollection = inCollection,
                userNotes = notes
            )
        }
    }
}
