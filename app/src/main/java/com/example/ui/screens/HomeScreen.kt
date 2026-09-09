package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CoinEntity
import com.example.ui.CoinViewModel
import com.example.ui.components.CoinCard
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
fun HomeScreen(
    viewModel: CoinViewModel,
    onNavigateToScanner: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToCollection: () -> Unit,
    onCoinSelected: (CoinEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val collection by viewModel.collectionList.collectAsState()
    val recentScans by viewModel.recentScans.collectAsState()

    val totalCollectionValue = collection.sumOf { it.estimatedValueMax }
    val rareCount = collection.count {
        it.rarity.contains("Rara", ignoreCase = true) || it.rarity.contains("Escassa", ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .testTag("home_screen")
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // App Header
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "CoinScan",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(GoldPrimary)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "AI",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = ObsidianDark
                            )
                        }
                    }
                    Text(
                        text = "Identificação & Avaliação Numismática",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(CardSurfaceDark)
                        .border(1.dp, BorderSubtle, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Certificado",
                        tint = GoldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Hero Banner Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.numismatic_hero_banner_1788949792008),
                            contentDescription = "CoinScan AI Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(
                                            androidx.compose.ui.graphics.Color.Transparent,
                                            CardSurfaceDark.copy(alpha = 0.95f)
                                        )
                                    )
                                )
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Avaliação Numismática de Precisão",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Identifique país, ano, casa da moeda, variantes, erros de cunhagem e valor de mercado com Inteligência Artificial.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onNavigateToScanner,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = ObsidianDark
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .testTag("scan_coin_button")
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Escanear Moeda Agora",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Quick Stats / Portfolio Summary
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RESUMO DA SUA COLEÇÃO",
                        style = MaterialTheme.typography.labelMedium,
                        color = GoldPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Ver Coleção",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        modifier = Modifier.clickable(onClick = onNavigateToCollection)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PortfolioStatCard(
                        title = "Moedas Salvas",
                        value = "${collection.size}",
                        icon = Icons.Default.CollectionsBookmark,
                        accentColor = GoldPrimary,
                        modifier = Modifier.weight(1f)
                    )

                    PortfolioStatCard(
                        title = "Peças Raras",
                        value = "$rareCount",
                        icon = Icons.Default.AutoAwesome,
                        accentColor = MintVerified,
                        modifier = Modifier.weight(1f)
                    )

                    PortfolioStatCard(
                        title = "Valor Estimado",
                        value = if (totalCollectionValue > 0) "R$ ${"%.0f".format(totalCollectionValue)}" else "R$ 0",
                        icon = Icons.Default.MonetizationOn,
                        accentColor = GoldLight,
                        modifier = Modifier.weight(1.2f)
                    )
                }
            }
        }

        // Numismatic Curatorship Tip
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = GoldContainer.copy(alpha = 0.4f)),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(GoldPrimary.copy(alpha = 0.3f))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Dica Profissional de Numismática",
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Nunca limpe ou pula suas moedas com produtos químicos, pastas ou escovas. A pátina original de oxidação é fundamental para a autenticidade e responde por até 80% do valor comercial da peça.",
                            fontSize = 12.sp,
                            color = TextWhite,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Quick Catalog Reference Shortcut
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceElevated),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToCatalog)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(GoldContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Catálogo de Raridades & Erros",
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Consulte moedas do Real, Império e peças mundiais",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Recent Scans Section
        if (recentScans.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ÚLTIMAS MOEDAS ESCANEADAS",
                        style = MaterialTheme.typography.labelMedium,
                        color = GoldPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            items(recentScans) { coin ->
                CoinCard(
                    coin = coin,
                    onClick = { onCoinSelected(coin) },
                    onToggleFavorite = { viewModel.toggleFavorite(coin.id, coin.isFavorite) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PortfolioStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextWhite
            )
            Text(
                text = title,
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}
