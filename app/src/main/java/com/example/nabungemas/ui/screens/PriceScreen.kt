package com.example.nabungemas.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nabungemas.data.NabungEmasRepository
import com.example.nabungemas.data.PriceHistoryPoint
import com.example.nabungemas.ui.components.formatIdr
import com.example.nabungemas.ui.theme.Error100
import com.example.nabungemas.ui.theme.Error500
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral100
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success100
import com.example.nabungemas.ui.theme.Success500
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PriceScreen(
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val goldPrice by repository.currentPrice.collectAsState()
    var selectedTimeframe by remember { mutableStateOf("7H") }
    val isDark = isSystemInDarkTheme()
    
    val scrollState = rememberScrollState()
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("in", "ID")) }

    val historyPoints = if (selectedTimeframe == "7H") {
        repository.priceHistory7d
    } else {
        repository.priceHistory30d
    }

    val isPositive = goldPrice.changePercentage >= 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
            .verticalScroll(scrollState)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Harga Emas",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Gold400,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { repository.refreshPrice() }) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "Refresh Price",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Golden Hero Price Card - matches design
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .shadow(6.dp, RoundedCornerShape(20.dp), clip = false, spotColor = Gold400.copy(alpha = 0.3f))
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.linearGradient(listOf(Color(0xFFB8860B), Color(0xFFD4A017), Gold400)))
                .padding(24.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "HARGA JUAL EMAS",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formatIdr(goldPrice.pricePerGramIdr),
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = Color.White
                        )
                        Text(
                            text = "per gram",
                            fontFamily = PlusJakartaSans,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                    // Percentage badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.25f))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPositive) Icons.Rounded.TrendingUp else Icons.Rounded.TrendingDown,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "%s%.2f%%".format(if (isPositive) "+" else "", goldPrice.changePercentage),
                                fontFamily = PlusJakartaSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "USD / gram", fontFamily = PlusJakartaSans, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                        Text(
                            text = "${"$"}${String.format(Locale.US, "%.2f", goldPrice.pricePerGramUsd)}",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Diperbarui", fontFamily = PlusJakartaSans, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                        Text(
                            text = dateFormat.format(Date(goldPrice.lastUpdated)),
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Chart Card
        Card(
            colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .shadow(2.dp, RoundedCornerShape(20.dp), clip = false)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Grafik Harga",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    // Timeframe tabs: 7H | 1B | 3B matching design
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isDark) Color.Black.copy(alpha = 0.2f) else Color(0xFFF0EDE6))
                            .padding(2.dp)
                    ) {
                        listOf("7H", "1B", "3B").forEach { tf ->
                            TimeframeTab(
                                label = tf,
                                isSelected = selectedTimeframe == tf,
                                onClick = { selectedTimeframe = tf }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                GoldLineChart(
                    points = historyPoints,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Currency boxes - side-by-side layout like design
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CurrencyBox(
                currency = "USD",
                flag = "🇺🇸",
                rate = "Rp 15.545",
                change = "+0,12%",
                isPositive = true,
                modifier = Modifier.weight(1f)
            )
            CurrencyBox(
                currency = "EUR",
                flag = "🇪🇺",
                rate = "Rp 16.780",
                change = "-0,05%",
                isPositive = false,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Detail Perubahan Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (isDark) Neutral800 else Color.White)
                .padding(20.dp)
        ) {
            Text(
                text = "Detail Perubahan",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            PriceChangeRow(label = "Perubahan Hari Ini", value = if (isPositive) "+Rp 2.500" else "-Rp 1.200", isPositive = isPositive)
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(12.dp))
            PriceChangeRow(label = "Tertinggi 7 Hari", value = formatIdr(historyPoints.maxOfOrNull { it.priceIdr } ?: goldPrice.pricePerGramIdr), isPositive = true)
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(12.dp))
            PriceChangeRow(label = "Terendah 7 Hari", value = formatIdr(historyPoints.minOfOrNull { it.priceIdr } ?: goldPrice.pricePerGramIdr), isPositive = false)
        }

        // Bottom safe space
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CurrencyBox(
    currency: String,
    flag: String,
    rate: String,
    change: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.shadow(2.dp, RoundedCornerShape(16.dp), clip = false)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = flag, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = currency,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = rate,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isPositive) Success500.copy(alpha = 0.12f) else Error500.copy(alpha = 0.12f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = change,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = if (isPositive) Success500 else Error500
                )
            }
        }
    }
}

@Composable
fun PriceChangeRow(label: String, value: String, isPositive: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            color = MutedText,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = if (isPositive) Success500 else Error500
        )
    }
}

@Composable
fun TimeframeTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Gold400 else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = PlusJakartaSans,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp,
            color = if (isSelected) Color.Black else MutedText
        )
    }
}

@Composable
fun GoldLineChart(
    points: List<PriceHistoryPoint>,
    modifier: Modifier = Modifier
) {
    val prices = points.map { it.priceIdr }
    val maxVal = prices.maxOrNull() ?: 1.0
    val minVal = prices.minOrNull() ?: 0.0
    val diff = (maxVal - minVal).coerceAtLeast(1.0)

    val graphColor = Gold400

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val sizePadding = 30.dp.toPx()

        val activeWidth = width - sizePadding * 2
        val activeHeight = height - sizePadding * 2

        if (points.size < 2) return@Canvas

        val stepX = activeWidth / (points.size - 1)
        val coordinatePoints = points.mapIndexed { index, point ->
            val ratioY = (point.priceIdr - minVal) / diff
            val x = sizePadding + index * stepX
            val y = sizePadding + activeHeight - (ratioY * activeHeight).toFloat()
            Offset(x, y)
        }

        // Fill gradient
        val fillPath = Path().apply {
            moveTo(coordinatePoints.first().x, sizePadding + activeHeight)
            coordinatePoints.forEach { point -> lineTo(point.x, point.y) }
            lineTo(coordinatePoints.last().x, sizePadding + activeHeight)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(graphColor.copy(alpha = 0.35f), graphColor.copy(alpha = 0.0f)),
                startY = coordinatePoints.map { it.y }.minOrNull() ?: 0f,
                endY = sizePadding + activeHeight
            )
        )

        // Stroke
        val strokePath = Path().apply {
            moveTo(coordinatePoints.first().x, coordinatePoints.first().y)
            coordinatePoints.forEach { point -> lineTo(point.x, point.y) }
        }
        drawPath(path = strokePath, color = graphColor, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))

        // Dots
        coordinatePoints.forEach { offset ->
            drawCircle(color = Color.White, radius = 4.dp.toPx(), center = offset)
            drawCircle(color = graphColor, radius = 2.dp.toPx(), center = offset)
        }
    }

    // Horizontal labels
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        points.forEach { point ->
            Text(
                text = point.dateLabel,
                fontFamily = PlusJakartaSans,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MutedText,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CurrencyRow(label: String, value: String, change: String) {
    val isPositive = change.startsWith("+")
    val col = if (isPositive) Success500 else Error500
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isDark) Color.Black.copy(alpha = 0.1f) else Neutral100.copy(alpha = 0.4f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontFamily = PlusJakartaSans, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
        Text(text = value, fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = change, fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = col, modifier = Modifier.width(60.dp), textAlign = TextAlign.End)
    }
}
