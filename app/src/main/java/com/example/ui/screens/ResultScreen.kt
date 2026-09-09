package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ai.ImageUtils
import com.example.model.CoinAnalysisResult
import com.example.model.ConfidenceTier
import com.example.ui.CoinViewModel
import com.example.ui.components.ConfidenceBadge
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintVerified
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.RubyAlert
import com.example.ui.theme.RubyContainer
import com.example.ui.theme.SilverAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    viewModel: CoinViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToCollection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val analysis by viewModel.currentAnalysis.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showSaveDialog by remember { mutableStateOf(false) }
    var userNotes by remember { mutableStateOf("") }
    var isSavedToCollection by remember { mutableStateOf(false) }

    if (analysis == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(ObsidianDark),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Nenhuma análise encontrada.",
                    color = TextWhite,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onNavigateToScanner,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("Abrir Scanner", color = ObsidianDark)
                }
            }
        }
        return
    }

    val result = analysis!!
    val obverseBitmap = remember(result.obverseImagePath) {
        result.obverseImagePath?.let { ImageUtils.loadBitmapFromPath(it) }
    }
    val reverseBitmap = remember(result.reverseImagePath) {
        result.reverseImagePath?.let { ImageUtils.loadBitmapFromPath(it) }
    }

    Box(
        modifier = modifier
            .testTag("result_screen")
            .fillMaxSize()
            .background(ObsidianDark)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Bar
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .testTag("back_button")
                            .clip(CircleShape)
                            .background(CardSurfaceDark)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = TextWhite
                        )
                    }

                    Text(
                        text = "LAUDO NUMISMÁTICO",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        letterSpacing = 1.5.sp
                    )

                    IconButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Relatório numismático copiado.")
                            }
                        },
                        modifier = Modifier
                            .testTag("share_button")
                            .clip(CircleShape)
                            .background(CardSurfaceDark)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartilhar",
                            tint = TextWhite
                        )
                    }
                }
            }

            // Coin Photo Previews & Confidence Badge
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Obverse & Reverse Photos Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Obverse
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(110.dp)
                                        .clip(CircleShape)
                                        .background(CardSurfaceElevated)
                                        .border(2.dp, GoldPrimary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (obverseBitmap != null) {
                                        Image(
                                            bitmap = obverseBitmap.asImageBitmap(),
                                            contentDescription = "Anverso",
                                            modifier = Modifier.size(110.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.coinscan_app_icon_1788949779593),
                                            contentDescription = "Anverso padrão",
                                            modifier = Modifier.size(110.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Anverso (Frente)",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }

                            if (reverseBitmap != null) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(110.dp)
                                            .clip(CircleShape)
                                        .background(CardSurfaceElevated)
                                        .border(2.dp, SilverAccent, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            bitmap = reverseBitmap.asImageBitmap(),
                                            contentDescription = "Reverso",
                                            modifier = Modifier.size(110.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Reverso (Verso)",
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Confidence Rating Badge
                        ConfidenceBadge(
                            confidence = result.confidence,
                            tier = result.confidenceTier
                        )
                    }
                }
            }

            // Inconclusive Alert Banner (If Confidence < 50% or failed image)
            if (result.isInconclusive || result.confidenceTier == ConfidenceTier.INCONCLUSIVE) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = RubyContainer.copy(alpha = 0.5f)),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(RubyAlert.copy(alpha = 0.6f))),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                    tint = RubyAlert,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Identificação Inconclusiva",
                                    fontWeight = FontWeight.Bold,
                                    color = RubyAlert,
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = result.inconclusiveReason ?: "Não foi possível identificar esta moeda com segurança numismática devido à baixa definição ou falta de elementos visíveis.",
                                color = TextWhite,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            if (result.tipsForBetterScan.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Recomendações para uma melhor identificação:",
                                    fontWeight = FontWeight.SemiBold,
                                    color = GoldLight,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                result.tipsForBetterScan.forEach { tip ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(text = "• ", color = GoldPrimary, fontSize = 12.sp)
                                        Text(text = tip, color = TextMuted, fontSize = 12.sp, lineHeight = 16.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = onNavigateToScanner,
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = ObsidianDark),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Tirar Nova Foto com Mais Luz", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Identification Summary & Face Value Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = result.country.uppercase(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary,
                                letterSpacing = 1.sp
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(GoldContainer)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = result.rarity,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${result.denomination} (${result.year})",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = BorderSubtle)
                        Spacer(modifier = Modifier.height(14.dp))

                        // Technical Specifications Grid
                        SpecRow("Casa da Moeda", result.mint)
                        SpecRow("Metal / Liga", result.composition)
                        SpecRow("Tipo & Variante", result.variant)
                        SpecRow("Estado de Conservação", result.conservationState)
                        if (!result.diameter.isNullOrBlank()) SpecRow("Diâmetro", result.diameter!!)
                        if (!result.weight.isNullOrBlank()) SpecRow("Peso Oficial", result.weight!!)
                    }
                }
            }

            // Estimated Market Value Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceElevated),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(GoldPrimary.copy(alpha = 0.5f))),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MonetizationOn,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ESTIMATIVA DE VALOR DE MERCADO",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = result.estimatedValueRangeFormatted,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Baseado em estado aparente: ${result.conservationState}",
                            fontSize = 12.sp,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Numismatic Disclaimer (Legal/Ethical Requirement)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(ObsidianDark.copy(alpha = 0.6f))
                                .padding(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Aviso: Valores calculados a partir de catálogos numismáticos, leilões recentes e tiragem. O valor real depende de inspeção presencial, verificação de peso milimétrico e pátina original.",
                                    fontSize = 11.sp,
                                    color = TextMuted,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Historical Context & "Why is this coin interesting?"
            if (result.whyInteresting.isNotBlank()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "CONTEXTO HISTÓRICO & RELEVÂNCIA",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight,
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = result.whyInteresting,
                                fontSize = 13.sp,
                                color = TextWhite,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }

            // Visual Characteristics Checklist
            if (result.visualFeatures.isNotEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "CARACTERÍSTICAS VISUAIS DETECTADAS",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            result.visualFeatures.forEach { feature ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MintVerified,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = feature,
                                        fontSize = 13.sp,
                                        color = TextWhite
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Minting Errors / Anomalies (Crucial for rare coins)
            if (result.mintingErrors.isNotEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = AmberWarning,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "ERROS DE CUNHAGEM & VARIANTES",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberWarning,
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            result.mintingErrors.forEach { errorItem ->
                                Row(
                                    modifier = Modifier.padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(text = "• ", color = AmberWarning, fontSize = 13.sp)
                                    Text(
                                        text = errorItem,
                                        fontSize = 13.sp,
                                        color = TextWhite,
                                        lineHeight = 17.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Value Influencing Factors
            if (result.valueFactors.isNotEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurfaceDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "FATORES QUE INFLUENCIAM O VALOR",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary,
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            result.valueFactors.forEach { factor ->
                                Row(
                                    modifier = Modifier.padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(text = "• ", color = GoldLight, fontSize = 13.sp)
                                    Text(
                                        text = factor,
                                        fontSize = 12.sp,
                                        color = TextMuted,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Primary Actions: Save to Collection, Scan Another
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { showSaveDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSavedToCollection) MintVerified else GoldPrimary,
                            contentColor = ObsidianDark
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .testTag("save_to_collection_button")
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(
                            imageVector = if (isSavedToCollection) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSavedToCollection) "Salvo na Minha Coleção" else "Salvar na Minha Coleção",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    OutlinedButton(
                        onClick = onNavigateToScanner,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                        border = ButtonDefaults.outlinedButtonBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .testTag("scan_another_button")
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Escanear Outra Moeda", fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Notes and Collection Dialog
        if (showSaveDialog) {
            AlertDialog(
                onDismissRequest = { showSaveDialog = false },
                title = {
                    Text(
                        text = "Salvar na Minha Coleção",
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Adicione anotações pessoais sobre como adquiriu a moeda, preço pago ou localização física.",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = userNotes,
                            onValueChange = { userNotes = it },
                            label = { Text("Anotações (Opcional)") },
                            placeholder = { Text("Ex: Moeda recebida de herança, guardada no álbum #2") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = BorderSubtle,
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.saveCurrentToCollection(userNotes)
                            isSavedToCollection = true
                            showSaveDialog = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Moeda adicionada à sua coleção com sucesso!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = ObsidianDark)
                    ) {
                        Text("Salvar", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSaveDialog = false }) {
                        Text("Cancelar", color = TextMuted)
                    }
                },
                containerColor = CardSurfaceDark
            )
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = TextMuted)
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TextWhite,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
