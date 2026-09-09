package com.example.ai

import android.graphics.Bitmap
import com.example.model.CoinAnalysisResult

interface AIService {
    /**
     * Analyzes one or two coin images (obverse and optional reverse).
     * Returns a structured CoinAnalysisResult with confidence rating, market estimate,
     * conservation assessment, and error/variant detection.
     */
    suspend fun analyzeCoin(
        obverseBitmap: Bitmap,
        reverseBitmap: Bitmap? = null,
        userHint: String? = null
    ): CoinAnalysisResult
}
