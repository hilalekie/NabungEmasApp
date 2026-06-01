package com.example.nabungemas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.data.GoldTransaction
import com.example.nabungemas.data.NabungEmasRepository
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.components.formatIdr
import com.example.nabungemas.ui.navigation.Screen
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success500
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val goals by repository.goals.collectAsState()
    val transactions by repository.transactions.collectAsState()
    val goldPrice by repository.currentPrice.collectAsState()

    val scrollState = rememberScrollState()

    // Calculate overall summaries
    val totalAccumulatedGrams = goals.sumOf { it.accumulatedGrams }
    val totalTargetGrams = goals.sumOf { it.targetGrams }
    val overallProgress = if (totalTargetGrams > 0) (totalAccumulatedGrams / totalTargetGrams).toFloat() else 0f
    val totalValueIdr = totalAccumulatedGrams * goldPrice.pricePerGramIdr

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        // Top App Bar with statusBarsPadding to avoid overlap
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NabungEmas",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Gold400,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* notification action */ }) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Settings / Profile",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Hero Card (Total Summary) - Gold gradient card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = false,
                        spotColor = Gold400.copy(alpha = 0.3f)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFB8860B), Color(0xFFD4A017), Gold400)
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "TOTAL TABUNGAN EMAS",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${String.format(Locale.US, "%.1f", totalAccumulatedGrams)} gram",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                    Text(
                        text = "≈ ${formatIdr(totalValueIdr)}",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Target Progress",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${(overallProgress * 100).toInt()}%",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(overallProgress.coerceIn(0f, 1f))
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Section "Tabungan Aktif"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tabungan Aktif",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Lihat Semua",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Gold400,
                    modifier = Modifier.clickable {
                        navController.navigate("main?tab=saving")
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val activeGoals = goals.filter { !it.isCompleted }
            if (activeGoals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSystemInDarkTheme()) Neutral800 else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada tabungan aktif.",
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        color = MutedText
                    )
                }
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(activeGoals) { goal ->
                        // Mini saving card matching design: white card, icon + name + grams
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSystemInDarkTheme()) Neutral800 else Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .width(160.dp)
                                .shadow(2.dp, RoundedCornerShape(16.dp), clip = false)
                                .clickable {
                                    navController.navigate(Screen.SavingDetail.createRoute(goal.id))
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Gold100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = goal.title.take(1).uppercase(),
                                        fontFamily = PlusJakartaSans,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Gold400
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
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
                                    text = "${String.format(Locale.US, "%.1f", goal.accumulatedGrams)} gr",
                                    fontFamily = PlusJakartaSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Gold400
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Section "Transaksi Terbaru"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transaksi Terbaru",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Riwayat",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Gold400,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.TransactionHistory.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val recentTxs = transactions.take(3)
            // Transaction list in a white card container matching design
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSystemInDarkTheme()) Neutral800 else Color.White)
            ) {
                if (recentTxs.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada transaksi.",
                            fontFamily = PlusJakartaSans,
                            fontSize = 14.sp,
                            color = MutedText
                        )
                    }
                } else {
                    Column {
                        recentTxs.forEachIndexed { index, tx ->
                            RecentTransactionItem(
                                tx = tx,
                                onClick = {
                                    navController.navigate(Screen.TransactionDetail.createRoute(tx.id))
                                }
                            )
                            if (index < recentTxs.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .height(1.dp)
                                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Tambah Transaksi" button at the bottom per design
            GoldButton(
                text = "+ Tambah Transaksi",
                onClick = {
                    navController.navigate(Screen.AddEditTransaction.createRoute())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun RecentTransactionItem(
    tx: GoldTransaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale("in", "ID")) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Gold100),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Gold400
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = tx.goalTitle,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = dateFormat.format(Date(tx.date)),
                fontFamily = PlusJakartaSans,
                fontSize = 12.sp,
                color = MutedText
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "+${String.format(Locale.US, "%.1f", tx.grams)} g",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Success500
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = formatIdr(tx.totalNominal),
                fontFamily = PlusJakartaSans,
                fontSize = 12.sp,
                color = MutedText
            )
        }
    }
}
