package com.example.ai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.math.max
import kotlin.math.min

object ImageUtils {

    fun Bitmap.toBase64(quality: Int = 85): String {
        val outputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    fun Bitmap.resizePreservingAspect(maxDimension: Int = 1024): Bitmap {
        val currentMax = max(width, height)
        if (currentMax <= maxDimension) return this

        val ratio = maxDimension.toFloat() / currentMax.toFloat()
        val targetWidth = (width * ratio).toInt()
        val targetHeight = (height * ratio).toInt()

        return Bitmap.createScaledBitmap(this, targetWidth, targetHeight, true)
    }

    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, prefix: String = "coin"): String {
        val dir = File(context.filesDir, "coin_scans")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, "${prefix}_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return file.absolutePath
    }

    fun loadBitmapFromPath(path: String): Bitmap? {
        val file = File(path)
        if (!file.exists()) return null
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    fun loadBitmapFromUri(context: Context, uri: Uri, maxDimension: Int = 1024): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val original = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            original?.resizePreservingAspect(maxDimension)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Calculates average luminosity (0 to 255) to detect if image is too dark or overexposed.
     */
    fun checkLuminosity(bitmap: Bitmap): Float {
        val sampleSize = 20
        var totalBrightness = 0.0
        var pixelCount = 0

        val stepX = max(1, bitmap.width / sampleSize)
        val stepY = max(1, bitmap.height / sampleSize)

        for (x in 0 until bitmap.width step stepX) {
            for (y in 0 until bitmap.height step stepY) {
                val color = bitmap.getPixel(x, y)
                val r = (color shr 16) and 0xFF
                val g = (color shr 8) and 0xFF
                val b = color and 0xFF
                // Standard perceptual luminance formula
                val luminance = 0.299 * r + 0.587 * g + 0.114 * b
                totalBrightness += luminance
                pixelCount++
            }
        }
        return if (pixelCount > 0) (totalBrightness / pixelCount).toFloat() else 128f
    }
}
