package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun ScanningProgressOverlay(
    stepDescription: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scan_transition")
    val laserOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_animation"
    )

    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_animation"
    )

    Box(
        modifier = modifier
            .testTag("scanning_progress_overlay")
            .fillMaxSize()
            .background(ObsidianDark.copy(alpha = 0.92f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            // High-tech scanner reticle
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, GoldPrimary.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Animated radar pulse rings
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val baseRadius = size.width / 2

                    drawCircle(
                        color = GoldPrimary.copy(alpha = 0.15f),
                        radius = (baseRadius * 0.75f) * pulseRadius,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    drawCircle(
                        color = GoldLight.copy(alpha = 0.25f),
                        radius = (baseRadius * 0.5f) * pulseRadius,
                        style = Stroke(width = 1.dp.toPx())
                    )

                    // Laser sweep line
                    val lineY = size.height * laserOffset
                    drawLine(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                GoldLight,
                                GoldPrimary,
                                GoldLight,
                                Color.Transparent
                            )
                        ),
                        start = Offset(0f, lineY),
                        end = Offset(size.width, lineY),
                        strokeWidth = 3.dp.toPx()
                    )
                }

                // Center spinner
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(72.dp),
                    color = GoldPrimary,
                    trackColor = GoldPrimary.copy(alpha = 0.2f),
                    strokeWidth = 4.dp
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = GoldLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "PROCESSAMENTO NUMISMÁTICO",
                style = MaterialTheme.typography.labelMedium,
                color = GoldPrimary,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stepDescription.ifBlank { "Analisando detalhes da moeda..." },
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = GoldPrimary,
                trackColor = CardSurfaceDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Avaliando orla, inscrições, relevo de cunhagem e catálogo oficial...",
                fontSize = 12.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}
