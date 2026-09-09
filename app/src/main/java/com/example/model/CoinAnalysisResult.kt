package com.example.model

enum class ConfidenceTier(val label: String, val thresholdDesc: String) {
    HIGH("Alta confiança", "90% – 100%"),
    GOOD("Boa confiança", "70% – 89%"),
    LOW("Baixa confiança", "50% – 69%"),
    INCONCLUSIVE("Não foi possível identificar com segurança", "Abaixo de 50%")
}

data class CoinAnalysisResult(
    val country: String = "Desconhecido",
    val denomination: String = "Moeda não identificada",
    val year: String = "Desconhecido",
    val mint: String = "Não identificada",
    val composition: String = "Liga metálica não confirmada",
    val diameter: String? = null,
    val weight: String? = null,
    val variant: String = "Padrão de circulação",
    val rarity: String = "Comum",
    val estimatedValueMin: Double = 0.0,
    val estimatedValueMax: Double = 0.0,
    val currency: String = "R$",
    val confidence: Float = 0.0f,
    val confidenceTier: ConfidenceTier = ConfidenceTier.INCONCLUSIVE,
    val conservationState: String = "Estado aparente não avaliado",
    val visualFeatures: List<String> = emptyList(),
    val mintingErrors: List<String> = emptyList(),
    val whyInteresting: String = "",
    val valueFactors: List<String> = emptyList(),
    val similarCoins: List<String> = emptyList(),
    val isInconclusive: Boolean = false,
    val inconclusiveReason: String? = null,
    val tipsForBetterScan: List<String> = emptyList(),
    val obverseImagePath: String? = null,
    val reverseImagePath: String? = null
) {
    val estimatedValueRangeFormatted: String
        get() {
            return if (isInconclusive || estimatedValueMax <= 0.0) {
                "Não foi possível estimar o valor com segurança"
            } else if (estimatedValueMin == estimatedValueMax) {
                "$currency ${"%.2f".format(estimatedValueMin)}"
            } else {
                "$currency ${"%.2f".format(estimatedValueMin)} – $currency ${"%.2f".format(estimatedValueMax)}"
            }
        }

    val confidencePercentageFormatted: String
        get() = "${(confidence * 100).toInt()}%"
}
