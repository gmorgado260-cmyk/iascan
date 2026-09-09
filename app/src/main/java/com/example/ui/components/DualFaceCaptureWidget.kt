package com.example.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CoinSide
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MintVerified
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun DualFaceCaptureWidget(
    obverseBitmap: Bitmap?,
    reverseBitmap: Bitmap?,
    activeSide: CoinSide,
    onSelectSide: (CoinSide) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardSurfaceDark)
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "FACES DA MOEDA",
                style = MaterialTheme.typography.labelMedium,
                color = GoldPrimary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Flip,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Frente & Verso",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Obverse (Frente)
            FaceCircleItem(
                label = "Frente (Anverso)",
                bitmap = obverseBitmap,
                isSelected = activeSide == CoinSide.OBVERSE,
                isRequired = true,
                onClick = { onSelectSide(CoinSide.OBVERSE) }
            )

            // Reverse (Verso)
            FaceCircleItem(
                label = "Verso (Reverso)",
                bitmap = reverseBitmap,
                isSelected = activeSide == CoinSide.REVERSE,
                isRequired = false,
                onClick = { onSelectSide(CoinSide.REVERSE) }
            )
        }

        // Precision advice note
        if (obverseBitmap != null && reverseBitmap == null) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(GoldContainer.copy(alpha = 0.5f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = AmberWarning,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Para aumentar a precisão, fotografe o outro lado da moeda.",
                    fontSize = 11.sp,
                    color = TextWhite,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun FaceCircleItem(
    label: String,
    bitmap: Bitmap?,
    isSelected: Boolean,
    isRequired: Boolean,
    onClick: () -> Unit
) {
    val borderColor = when {
        isSelected -> GoldPrimary
        bitmap != null -> MintVerified
        else -> BorderSubtle
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .testTag("face_item_${label.take(6)}")
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(CardSurfaceElevated)
                .border(if (isSelected) 2.5.dp else 1.5.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = label,
                    modifier = Modifier.size(76.dp),
                    contentScale = ContentScale.Crop
                )
                // Checked badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(2.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MintVerified),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Capturado",
                        tint = TextWhite,
                        modifier = Modifier.size(14.dp)
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = label,
                        tint = if (isSelected) GoldPrimary else TextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isRequired) "Obrigatória" else "Opcional",
                        fontSize = 9.sp,
                        color = TextMuted
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) GoldPrimary else TextWhite
        )
    }
}
