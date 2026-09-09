package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ConfidenceTier
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintVerified
import com.example.ui.theme.RubyAlert
import com.example.ui.theme.RubyContainer

@Composable
fun ConfidenceBadge(
    confidence: Float,
    tier: ConfidenceTier,
    modifier: Modifier = Modifier
) {
    val (bgColor, borderColor, textColor, icon) = when (tier) {
        ConfidenceTier.HIGH -> Quadruple(
            MintContainer.copy(alpha = 0.6f),
            MintVerified,
            MintVerified,
            Icons.Default.Verified
        )
        ConfidenceTier.GOOD -> Quadruple(
            GoldContainer.copy(alpha = 0.6f),
            GoldPrimary,
            GoldLight,
            Icons.Default.CheckCircle
        )
        ConfidenceTier.LOW -> Quadruple(
            AmberContainer.copy(alpha = 0.6f),
            AmberWarning,
            AmberWarning,
            Icons.Default.Warning
        )
        ConfidenceTier.INCONCLUSIVE -> Quadruple(
            RubyContainer.copy(alpha = 0.6f),
            RubyAlert,
            RubyAlert,
            Icons.Default.Info
        )
    }

    Box(
        modifier = modifier
            .testTag("confidence_badge")
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(1.dp, borderColor.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${tier.label} (${(confidence * 100).toInt()}%)",
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
