package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CoinEntity
import com.example.ui.CoinViewModel
import com.example.ui.components.CoinCard
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MintVerified
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun CollectionScreen(
    viewModel: CoinViewModel,
    onCoinSelected: (CoinEntity) -> Unit,
    onNavigateToScanner: () -> Unit,
    modifier: Modifier = Modifier
) {
    val collection by viewModel.collectionList.collectAsState()
    val favorites by viewModel.favoritesList.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todas") }

    val filterOptions = listOf("Todas", "Favoritas", "Rara", "Incomum", "Comum")

    val filteredList = collection.filter { coin ->
        val matchesSearch = searchQuery.isBlank() ||
            coin.title.contains(searchQuery, ignoreCase = true) ||
            coin.country.contains(searchQuery, ignoreCase = true) ||
            coin.year.contains(searchQuery, ignoreCase = true) ||
            coin.denomination.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            "Todas" -> true
            "Favoritas" -> coin.isFavorite
            "Rara" -> coin.rarity.contains("Rara", ignoreCase = true)
            "Incomum" -> coin.rarity.contains("Incomum", ignoreCase = true)
            "Comum" -> coin.rarity.contains("Comum", ignoreCase = true)
            else -> true
        }

        matchesSearch && matchesFilter
    }

    val totalPortfolioMin = filteredList.sumOf { it.estimatedValueMin }
    val totalPortfolioMax = filteredList.sumOf { it.estimatedValueMax }

    Column(
        modifier = modifier
            .testTag("collection_screen")
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Title Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Minha Coleção",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "${collection.size} moedas catalogadas",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            IconButton(
                onClick = onNavigateToScanner,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(GoldPrimary)
                    .size(42.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Adicionar moeda",
                    tint = ObsidianDark
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Portfolio Valuation Banner
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(GoldPrimary.copy(alpha = 0.3f))),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "PATRIMÔNIO ESTIMADO",
                        style = MaterialTheme.typography.labelSmall,
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (totalPortfolioMax > 0) {
                            "R$ ${"%.0f".format(totalPortfolioMin)} – R$ ${"%.0f".format(totalPortfolioMax)}"
                        } else {
                            "R$ 0,00"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = GoldPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GoldContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar por nome, ano, país...", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextMuted)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpar", tint = TextMuted)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = BorderSubtle,
                focusedContainerColor = CardSurfaceDark,
                unfocusedContainerColor = CardSurfaceDark,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            modifier = Modifier
                .testTag("collection_search_field")
                .fillMaxWidth()
                .height(52.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Pills Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterOptions) { filter ->
                val isSelected = selectedFilter == filter
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) GoldPrimary else CardSurfaceDark)
                        .border(1.dp, if (isSelected) GoldPrimary else BorderSubtle, RoundedCornerShape(20.dp))
                        .clickable { selectedFilter = filter }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = filter,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) ObsidianDark else TextWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Collection List or Empty State
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(CardSurfaceDark)
                            .border(1.dp, BorderSubtle, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CollectionsBookmark,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (searchQuery.isNotBlank()) "Nenhuma moeda encontrada com este termo." else "Sua coleção ainda está vazia.",
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Escaneie suas primeiras peças para catalogá-las e acompanhar o valor de mercado.",
                        color = TextMuted,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onNavigateToScanner,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = ObsidianDark),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Escanear Primeira Moeda", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList, key = { it.id }) { coin ->
                    CoinCard(
                        coin = coin,
                        onClick = { onCoinSelected(coin) },
                        onToggleFavorite = { viewModel.toggleFavorite(coin.id, coin.isFavorite) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
