package com.example.model

data class ReferenceCoin(
    val id: String,
    val name: String,
    val country: String,
    val denomination: String,
    val year: String,
    val mint: String,
    val composition: String,
    val weight: String,
    val diameter: String,
    val rarity: String,
    val estimatedValueRange: String,
    val valueMin: Double,
    val valueMax: Double,
    val description: String,
    val historicalContext: String,
    val visualHighlights: List<String>,
    val commonVariantsOrErrors: List<String>,
    val category: String, // "Raridades Brasileiras", "Moedas do Real", "Império & Colônia", "Moedas Internacionais"
    val isVerifiedCatalog: Boolean = true
)
