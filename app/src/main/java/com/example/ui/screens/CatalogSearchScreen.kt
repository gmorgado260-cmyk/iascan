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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ReferenceCoin
import com.example.ui.CoinViewModel
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MintVerified
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun CatalogSearchScreen(
    viewModel: CoinViewModel,
    onNavigateToScanner: () -> Unit,
    modifier: Modifier = Modifier
) {
    val catalog by viewModel.filteredCatalog.collectAsState()
    val searchQuery by viewModel.catalogQuery.collectAsState()
    val selectedCategory by viewModel.selectedCatalogCategory.collectAsState()
    val categories = viewModel.catalogCategories

    var selectedCoinForDetail by remember { mutableStateOf<ReferenceCoin?>(null) }

    Column(
        modifier = modifier
            .testTag("catalog_screen")
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Catálogo Numismático",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "Base de referência oficial com valores e erros",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CardSurfaceDark)
                    .border(1.dp, BorderSubtle, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setCatalogQuery(it) },
            placeholder = { Text("Pesquisar Direitos Humanos, Mula, Dobrão...", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextMuted)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setCatalogQuery("") }) {
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
                .testTag("catalog_search_field")
                .fillMaxWidth()
                .height(52.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Categories Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { cat ->
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) GoldPrimary else CardSurfaceDark)
                        .border(1.dp, if (isSelected) GoldPrimary else BorderSubtle, RoundedCornerShape(20.dp))
                        .clickable { viewModel.setCatalogCategory(cat) }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = cat,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) ObsidianDark else TextWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Results List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(catalog, key = { it.id }) { item ->
                ReferenceCatalogCard(
                    coin = item,
                    onClick = { selectedCoinForDetail = item }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Detailed Numismatic Sheet Dialog
        if (selectedCoinForDetail != null) {
            val ref = selectedCoinForDetail!!
            AlertDialog(
                onDismissRequest = { selectedCoinForDetail = null },
                title = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = ref.country.uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary,
                                letterSpacing = 1.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(GoldContainer)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = ref.rarity,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = ref.name,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontSize = 17.sp
                        )
                    }
                },
                text = {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        item {
                            // Valuation
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(CardSurfaceElevated)
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "VALOR ESTIMADO DE MERCADO",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = ref.estimatedValueRange,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = GoldPrimary
                                    )
                                }
                            }
                        }

                        item {
                            Text(
                                text = ref.description,
                                fontSize = 12.sp,
                                color = TextWhite,
                                lineHeight = 16.sp
                            )
                        }

                        item {
                            HorizontalDivider(color = BorderSubtle)
                        }

                        item {
                            Text(
                                text = "ESPECIFICAÇÕES TÉCNICAS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Casa da Moeda: ${ref.mint}", fontSize = 12.sp, color = TextMuted)
                            Text(text = "Composição: ${ref.composition}", fontSize = 12.sp, color = TextMuted)
                            Text(text = "Diâmetro: ${ref.diameter} • Peso: ${ref.weight}", fontSize = 12.sp, color = TextMuted)
                        }

                        item {
                            Text(
                                text = "PONTOS DE ATENÇÃO & VARIANTES",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            ref.commonVariantsOrErrors.forEach { v ->
                                Text(text = "• $v", fontSize = 12.sp, color = TextMuted, lineHeight = 15.sp)
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedCoinForDetail = null
                            onNavigateToScanner()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = ObsidianDark)
                    ) {
                        Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Escanear Esta Moeda", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedCoinForDetail = null }) {
                        Text("Fechar", color = TextMuted)
                    }
                },
                containerColor = CardSurfaceDark
            )
        }
    }
}

@Composable
private fun ReferenceCatalogCard(
    coin: ReferenceCoin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(CardSurfaceElevated)
                    .border(1.dp, GoldPrimary.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${coin.country} • ${coin.composition}",
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GoldContainer.copy(alpha = 0.7f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = coin.rarity,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldLight
                        )
                    }

                    Text(
                        text = coin.estimatedValueRange,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
