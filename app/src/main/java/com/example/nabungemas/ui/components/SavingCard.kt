package com.example.nabungemas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nabungemas.data.SavingGoal
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success500
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatIdr(amount: Double): String {
    val symbols = DecimalFormatSymbols(Locale("in", "ID"))
    val formatter = DecimalFormat("#,###", symbols)
    return "Rp " + formatter.format(amount)
}

@Composable
fun SavingCard(
    goal: SavingGoal,
    currentGoldPriceIdr: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isMini: Boolean = false
) {
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) Neutral800 else Neutral050
    val cardShadow = Modifier.shadow(
        elevation = 1.dp,
        shape = RoundedCornerShape(16.dp),
        spotColor = Color(0x0F000000),
        ambientColor = Color(0x0F000000),
        clip = false
    )

    val borderModifier = if (goal.isCompleted) {
        Modifier.border(1.dp, Success500, RoundedCornerShape(16.dp))
    } else {
        Modifier.border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
    }

    if (isMini) {
        // Mini card for horizontal row on dashboard
        Card(
            colors = CardDefaults.cardColors(containerColor = bgColor),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .width(180.dp)
                .then(cardShadow)
                .then(borderModifier)
                .clickable(onClick = onClick)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = goal.title,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${goal.accumulatedGrams} g / ${goal.targetGrams} g",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.height(12.dp))
                GoldProgressBar(
                    progress = goal.progress,
                    showLabel = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                val estimateValue = goal.accumulatedGrams * currentGoldPriceIdr
                Text(
                    text = formatIdr(estimateValue),
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Gold400
                )
            }
        }
    } else {
        // Standard full-width card
        Card(
            colors = CardDefaults.cardColors(containerColor = bgColor),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .then(cardShadow)
                .then(borderModifier)
                .clickable(onClick = onClick)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = goal.title,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (goal.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = goal.description,
                                fontFamily = PlusJakartaSans,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = MutedText,
                                maxLines = 1
                            )
                        }
                    }
                    if (goal.isCompleted) {
                        Text(
                            text = "Selesai",
                            color = Success500,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .background(Success500.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${goal.accumulatedGrams} gram / ${goal.targetGrams} gram",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    val estimateValue = goal.accumulatedGrams * currentGoldPriceIdr
                    Text(
                        text = "Est. ${formatIdr(estimateValue)}",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Gold400
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                GoldProgressBar(
                    progress = goal.progress,
                    showLabel = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
