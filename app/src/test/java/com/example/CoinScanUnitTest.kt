package com.example

import com.example.data.repository.ReferenceCatalogRepository
import com.example.model.CoinAnalysisResult
import com.example.model.CoinEntity
import com.example.model.ConfidenceTier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CoinScanUnitTest {

    private val catalogRepository = ReferenceCatalogRepository()

    @Test
    fun testCatalogHasVerifiedCoins() {
        val coins = catalogRepository.getAllReferenceCoins()
        assertTrue("Catalog should contain reference coins", coins.isNotEmpty())
        assertTrue("Catalog should contain at least 10 authentic entries", coins.size >= 10)
    }

    @Test
    fun testCatalogSearchByError() {
        val results = catalogRepository.search("Sem o Zero")
        assertTrue("Should find '50 Centavos Sem o Zero'", results.isNotEmpty())
        assertEquals("50 Centavos (Sem Zero)", results.first().denomination)
    }

    @Test
    fun testCatalogSearchByDireitosHumanos() {
        val results = catalogRepository.search("Direitos Humanos")
        assertTrue("Should find 1 Real 1998 Direitos Humanos", results.isNotEmpty())
        assertEquals("1998", results.first().year)
    }

    @Test
    fun testCatalogFilterByCategory() {
        val realCoins = catalogRepository.findByCategory("Moedas do Real")
        assertTrue("Should find coins from Real standard", realCoins.isNotEmpty())
        assertTrue(realCoins.all { it.category == "Moedas do Real" })
    }

    @Test
    fun testCoinAnalysisResultFormatting() {
        val result = CoinAnalysisResult(
            country = "Brasil",
            denomination = "1 Real",
            year = "1998",
            estimatedValueMin = 250.0,
            estimatedValueMax = 1800.0,
            currency = "R$",
            confidence = 0.94f,
            confidenceTier = ConfidenceTier.HIGH
        )

        assertEquals("94%", result.confidencePercentageFormatted)
        assertTrue(result.estimatedValueRangeFormatted.contains("R$ 250,00"))
        assertTrue(result.estimatedValueRangeFormatted.contains("R$ 1800,00"))
        assertFalse(result.isInconclusive)
    }

    @Test
    fun testInconclusiveResultFormatting() {
        val inconclusive = CoinAnalysisResult(
            country = "Desconhecido",
            denomination = "Não identificada",
            isInconclusive = true,
            confidence = 0.35f,
            confidenceTier = ConfidenceTier.INCONCLUSIVE
        )

        assertEquals("35%", inconclusive.confidencePercentageFormatted)
        assertEquals("Não foi possível estimar o valor com segurança", inconclusive.estimatedValueRangeFormatted)
    }

    @Test
    fun testCoinEntityConversionRoundtrip() {
        val originalResult = CoinAnalysisResult(
            country = "Brasil",
            denomination = "1 Real",
            year = "1998",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Alpaca e Cuproníquel",
            variant = "Comemorativa",
            rarity = "Rara",
            estimatedValueMin = 250.0,
            estimatedValueMax = 1800.0,
            confidence = 0.92f,
            conservationState = "Soberba (SOB)",
            visualFeatures = listOf("Orla perfeita", "Efígie nítida"),
            whyInteresting = "Tiragem de 600 mil moedas."
        )

        val entity = CoinEntity.fromAnalysisResult(originalResult, inCollection = true, favorite = true)
        val converted = entity.toAnalysisResult()

        assertEquals(originalResult.country, converted.country)
        assertEquals(originalResult.denomination, converted.denomination)
        assertEquals(originalResult.year, converted.year)
        assertEquals(originalResult.rarity, converted.rarity)
        assertEquals(originalResult.confidence, converted.confidence, 0.01f)
        assertEquals(ConfidenceTier.HIGH, converted.confidenceTier)
        assertEquals(2, converted.visualFeatures.size)
    }
}
