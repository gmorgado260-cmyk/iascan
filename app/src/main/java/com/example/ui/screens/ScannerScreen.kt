package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.R
import com.example.ai.ImageUtils
import com.example.ui.CoinSide
import com.example.ui.CoinViewModel
import com.example.ui.components.DualFaceCaptureWidget
import com.example.ui.components.ScanningProgressOverlay
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.SlateDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun ScannerScreen(
    viewModel: CoinViewModel,
    onAnalysisFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val obverseBitmap by viewModel.obverseBitmap.collectAsState()
    val reverseBitmap by viewModel.reverseBitmap.collectAsState()
    val activeSide by viewModel.activeSide.collectAsState()
    val isAnalyzing by viewModel.isAnalyzing.collectAsState()
    val analysisStep by viewModel.analysisStep.collectAsState()
    val analysisProgress by viewModel.analysisProgress.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var isFlashOn by remember { mutableStateOf(false) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    // Photo picker launcher (zero-permission standard)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val bitmap = ImageUtils.loadBitmapFromUri(context, uri)
            if (bitmap != null) {
                if (activeSide == CoinSide.OBVERSE) {
                    viewModel.setObverseImage(bitmap)
                } else {
                    viewModel.setReverseImage(bitmap)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(
        modifier = modifier
            .testTag("scanner_screen")
            .fillMaxSize()
            .background(ObsidianDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Scanner Top Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Scanner Numismático",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Posicione a moeda no alvo",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Flashlight toggle
                    IconButton(
                        onClick = { isFlashOn = !isFlashOn },
                        modifier = Modifier
                            .testTag("flash_toggle_button")
                            .clip(CircleShape)
                            .background(CardSurfaceDark)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Alternar Flash",
                            tint = if (isFlashOn) GoldPrimary else TextMuted
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Reset / Clear captures
                    if (obverseBitmap != null || reverseBitmap != null) {
                        IconButton(
                            onClick = { viewModel.clearCapturedImages() },
                            modifier = Modifier
                                .testTag("reset_captures_button")
                                .clip(CircleShape)
                                .background(CardSurfaceDark)
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Limpar capturas",
                                tint = TextMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dual Face Capture Status Widget
            DualFaceCaptureWidget(
                obverseBitmap = obverseBitmap,
                reverseBitmap = reverseBitmap,
                activeSide = activeSide,
                onSelectSide = { side -> viewModel.setActiveSide(side) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Main Circular Viewfinder / Reticle Frame
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SlateDark)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Background Viewfinder Reticle
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = (size.minDimension / 2) - 36.dp.toPx()

                    // Circular target boundary
                    drawCircle(
                        color = GoldPrimary,
                        radius = radius,
                        center = center,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 15f), 0f)
                        )
                    )

                    // Corner guide crosshairs
                    val crossLen = 24.dp.toPx()
                    // Top
                    drawLine(
                        color = GoldLight,
                        start = Offset(center.x, center.y - radius - crossLen),
                        end = Offset(center.x, center.y - radius + 8.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                    // Bottom
                    drawLine(
                        color = GoldLight,
                        start = Offset(center.x, center.y + radius - 8.dp.toPx()),
                        end = Offset(center.x, center.y + radius + crossLen),
                        strokeWidth = 2.dp.toPx()
                    )
                    // Left
                    drawLine(
                        color = GoldLight,
                        start = Offset(center.x - radius - crossLen, center.y),
                        end = Offset(center.x - radius + 8.dp.toPx(), center.y),
                        strokeWidth = 2.dp.toPx()
                    )
                    // Right
                    drawLine(
                        color = GoldLight,
                        start = Offset(center.x + radius - 8.dp.toPx(), center.y),
                        end = Offset(center.x + radius + crossLen, center.y),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                // Instructions in viewfinder
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(ObsidianDark.copy(alpha = 0.85f))
                            .border(1.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (activeSide == CoinSide.OBVERSE) "FOTOGRAFAR FRENTE (ANVERSO)" else "FOTOGRAFAR VERSO (REVERSO)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Centralize a moeda dentro do círculo.\nEvite sombras, reflexos e fundos com estampa.",
                        fontSize = 12.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Quick test sample coins loader
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardSurfaceDark.copy(alpha = 0.8f))
                            .clickable {
                                // Load high-res specimen (1 Real Direitos Humanos)
                                val sampleBitmap = BitmapFactory.decodeResource(
                                    context.resources,
                                    R.drawable.coinscan_app_icon_1788949779593
                                )
                                if (sampleBitmap != null) {
                                    if (activeSide == CoinSide.OBVERSE) {
                                        viewModel.setObverseImage(sampleBitmap)
                                    } else {
                                        viewModel.setReverseImage(sampleBitmap)
                                    }
                                }
                            }
                            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Usar Moeda de Exemplo (1 Real 1998)",
                                fontSize = 11.sp,
                                color = TextWhite
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Shutter & Action Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gallery button
                IconButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .testTag("gallery_picker_button")
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(CardSurfaceDark)
                        .border(1.dp, BorderSubtle, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Collections,
                        contentDescription = "Carregar da Galeria",
                        tint = GoldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Primary Shutter Capture Button
                Box(
                    modifier = Modifier
                        .testTag("shutter_button")
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary)
                        .border(4.dp, ObsidianDark, CircleShape)
                        .clickable {
                            // Capture photo or sample
                            val sampleBitmap = BitmapFactory.decodeResource(
                                context.resources,
                                R.drawable.coinscan_app_icon_1788949779593
                            )
                            if (sampleBitmap != null) {
                                if (activeSide == CoinSide.OBVERSE) {
                                    viewModel.setObverseImage(sampleBitmap)
                                } else {
                                    viewModel.setReverseImage(sampleBitmap)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                            .background(ObsidianDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Fotografar",
                            tint = GoldPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                // Side toggle button
                IconButton(
                    onClick = {
                        viewModel.setActiveSide(
                            if (activeSide == CoinSide.OBVERSE) CoinSide.REVERSE else CoinSide.OBVERSE
                        )
                    },
                    modifier = Modifier
                        .testTag("side_toggle_button")
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(CardSurfaceDark)
                        .border(1.dp, BorderSubtle, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Flip,
                        contentDescription = "Alternar Lado",
                        tint = TextWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Primary Start Analysis Button (Enabled when at least Obverse is captured)
            if (obverseBitmap != null) {
                Button(
                    onClick = {
                        viewModel.startAnalysis(onSuccess = onAnalysisFinished)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = ObsidianDark
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .testTag("start_analysis_button")
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (reverseBitmap != null) "Analisar Frente e Verso" else "Analisar Moeda (1 Face)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }

        // Animated Scanning Overlay while processing
        if (isAnalyzing) {
            ScanningProgressOverlay(
                stepDescription = analysisStep,
                progress = analysisProgress
            )
        }
    }
}
