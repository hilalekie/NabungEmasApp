package com.example.nabungemas.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.Neutral200
import com.example.nabungemas.ui.theme.Neutral700
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun GoldProgressBar(
    progress: Float, // 0.0f to 1.0f
    modifier: Modifier = Modifier,
    showLabel: Boolean = true
) {
    // Local state to trigger expand animation on launch
    var targetProgress by remember { mutableStateOf(0f) }
    
    LaunchedEffect(progress) {
        targetProgress = progress
    }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 800),
        label = "ProgressAnimation"
    )

    val trackColor = if (isSystemInDarkTheme()) Neutral700 else Neutral200
    val gradient = Brush.horizontalGradient(
        colors = listOf(Gold400, Gold300)
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Track
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(trackColor)
        ) {
            // Fill
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(4.dp))
                    .background(gradient)
            )
        }
        
        if (showLabel) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (isSystemInDarkTheme()) Color.White else Neutral700
            )
        }
    }
}
