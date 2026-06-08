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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.data.GoldTransaction
import com.example.nabungemas.data.NabungEmasRepository
import com.example.nabungemas.data.SavingGoal
import com.example.nabungemas.ui.components.ConfirmationDialog
import com.example.nabungemas.ui.components.EmptyStateView
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.components.GoldProgressBar
import com.example.nabungemas.ui.components.GoldTextField
import com.example.nabungemas.ui.components.SavingCard
import com.example.nabungemas.ui.components.formatIdr
import com.example.nabungemas.ui.navigation.Screen
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success500
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SavingListScreen(
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val goals by repository.goals.collectAsState()
    val goldPrice by repository.currentPrice.collectAsState()
    val isDark = isSystemInDarkTheme()

    val activeCount = goals.count { !it.isCompleted }

    LaunchedEffect(Unit) {
        repository.fetchGoals()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tabungan Saya",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Gold400,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Active count chip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gold100)
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Gold400)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$activeCount tabungan aktif",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Gold400
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (goals.isEmpty()) {
                EmptyStateView(
                    icon = Icons.Rounded.Savings,
                    title = "Belum ada tabungan",
                    message = "Mulai impian finansialmu sekarang dengan membuat rencana tabungan emas pertamamu.",
                    buttonText = "+ Buat Tabungan",
                    onButtonClick = { navController.navigate(Screen.AddEditSaving.createRoute()) },
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(goals) { goal ->
                        SavingListCard(
                            goal = goal,
                            currentGoldPriceIdr = goldPrice.pricePerGramIdr,
                            onClick = {
                                navController.navigate(Screen.SavingDetail.createRoute(goal.id))
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        // FAB: Add Target
        if (goals.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 16.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp))
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.linearGradient(listOf(Gold400, Gold300)))
                    .clickable {
                        navController.navigate(Screen.AddEditSaving.createRoute())
                    }
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Buat Tabungan",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buat Tabungan",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun SavingListCard(
    goal: SavingGoal,
    currentGoldPriceIdr: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val progressPercent = (goal.progress * 100).toInt()
    val estimateValue = goal.accumulatedGrams * currentGoldPriceIdr
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale("in", "ID")) }

    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp), clip = false)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header row: title + Aktif badge
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = goal.title,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Update: ${dateFormat.format(Date())}",
                        fontFamily = PlusJakartaSans,
                        fontSize = 12.sp,
                        color = MutedText,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (goal.isCompleted) Success500.copy(alpha = 0.15f)
                            else Color(0xFFE8F5E9)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (goal.isCompleted) "Selesai" else "Aktif",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = if (goal.isCompleted) Success500 else Color(0xFF388E3C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Accumulated gram - large display
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = String.format(Locale.US, "%.1f", goal.accumulatedGrams),
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Gold400
                )
                Text(
                    text = " / ${String.format(Locale.US, "%.0f", goal.targetGrams)} gram",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (progressPercent >= 100) "100%" else "$progressPercent%",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (goal.isCompleted) Success500 else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(if (isDark) Color(0xFF3A3A3A) else Color(0xFFF0EDE6))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(goal.progress.toFloat().coerceIn(0f, 1f))
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(Color(0xFFB8860B), Gold400))
                        )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom: value + Detail link
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "≈ ${formatIdr(estimateValue)}",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onClick)
                ) {
                    Text(
                        text = "Detail",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Gold400
                    )
                    Icon(
                        imageVector = Icons.Rounded.ArrowForwardIos,
                        contentDescription = null,
                        tint = Gold400,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SavingDetailScreen(
    savingId: String,
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    val goals by repository.goals.collectAsState()
    val transactions by repository.transactions.collectAsState()
    val goldPrice by repository.currentPrice.collectAsState()

    val goal = goals.find { it.id == savingId }
    val goalTransactions = transactions.filter { it.goalId == savingId }
        .sortedByDescending { it.date }

    if (goal == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Target tabungan tidak ditemukan.", color = MutedText)
        }
        return
    }

    val estimateValue = goal.accumulatedGrams * goldPrice.pricePerGramIdr
    val totalTransactionsCount = goalTransactions.size
    val averageBuy = if (totalTransactionsCount > 0) goalTransactions.map { it.grams }.average() else 0.0
    val progressPercent = (goal.progress * 100).toInt()
    val remaining = goal.targetGrams - goal.accumulatedGrams
    val isDark = isSystemInDarkTheme()

    if (showDeleteDialog) {
        ConfirmationDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirm = {
                coroutineScope.launch {
                    repository.deleteGoal(savingId)
                    navController.popBackStack()
                }
            },
            title = "Hapus Tabungan?",
            message = "Aksi ini tidak dapat dibatalkan. Seluruh data transaksi terkait tabungan ini juga akan dihapus permanen.",
            confirmButtonText = "Hapus",
            isDeleteAction = true
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Gold400)
                }
                Text(
                    text = goal.title,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { navController.navigate(Screen.AddEditSaving.createRoute(savingId)) }) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.onSurface)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete", tint = Color(0xFFE53935))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Hero Card - golden gradient like design
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
                            Text(
                                text = "TABUNGAN TARGET",
                                fontFamily = PlusJakartaSans,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                letterSpacing = 1.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White.copy(alpha = 0.25f))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "$progressPercent%",
                                    fontFamily = PlusJakartaSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = goal.title,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Terkumpul",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = String.format(Locale.US, "%.2f", goal.accumulatedGrams),
                                        fontFamily = PlusJakartaSans,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 28.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = " gram",
                                        fontFamily = PlusJakartaSans,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Target",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "${String.format(Locale.US, "%.2f", goal.targetGrams)} gram",
                                    fontFamily = PlusJakartaSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
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
                                    .fillMaxWidth(goal.progress.toFloat().coerceIn(0f, 1f))
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = "Sisa ${String.format(Locale.US, "%.2f", remaining.coerceAtLeast(0.0))} gram lagi",
                                fontFamily = PlusJakartaSans,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Est. Selesai: Des 2025",
                                fontFamily = PlusJakartaSans,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row (3 chips like design)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatChipDetail(
                        icon = Icons.Rounded.SwapHoriz,
                        label = "Transaksi",
                        value = "$totalTransactionsCount",
                        modifier = Modifier.weight(1f)
                    )
                    StatChipDetail(
                        icon = Icons.Rounded.Timer,
                        label = "Rata-rata",
                        value = "${String.format(Locale.US, "%.2f", averageBuy)}g",
                        modifier = Modifier.weight(1f)
                    )
                    StatChipDetail(
                        icon = Icons.Rounded.TrendingUp,
                        label = "Nilai Est.",
                        value = formatIdr(estimateValue).replace("Rp ", ""),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Transactions header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Riwayat Transaksi",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Lihat Semua",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = Gold400,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.TransactionHistory.route)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (goalTransactions.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isDark) Neutral800 else Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada transaksi tabungan.",
                            fontFamily = PlusJakartaSans,
                            fontSize = 13.sp,
                            color = MutedText
                        )
                    }
                } else {
                    // All transactions in a white card container
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isDark) Neutral800 else Color.White)
                    ) {
                        goalTransactions.forEachIndexed { index, tx ->
                            SavingDetailTransactionRow(
                                tx = tx,
                                onClick = {
                                    navController.navigate(Screen.TransactionDetail.createRoute(tx.id))
                                }
                            )
                            if (index < goalTransactions.lastIndex) {
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

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // FAB: Add transaction for this goal - matching design button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp)
                .shadow(8.dp, RoundedCornerShape(28.dp))
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.linearGradient(listOf(Gold400, Gold300)))
                .clickable {
                    navController.navigate(Screen.AddEditTransaction.createRoute(savingId = savingId))
                }
                .padding(horizontal = 24.dp, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tambah Transaksi",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun SavingDetailTransactionRow(
    tx: GoldTransaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy • HH:mm", Locale("in", "ID")) }

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
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = Gold400,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (tx.notes.isNotEmpty()) tx.notes else "Tabungan",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Text(
                text = dateFormat.format(Date(tx.date)),
                fontFamily = PlusJakartaSans,
                fontSize = 12.sp,
                color = MutedText
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "+${String.format(Locale.US, "%.2f", tx.grams)}g",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Success500
            )
            Text(
                text = formatIdr(tx.totalNominal),
                fontFamily = PlusJakartaSans,
                fontSize = 12.sp,
                color = MutedText
            )
        }
    }
}

@Composable
fun StatChipDetail(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.shadow(2.dp, RoundedCornerShape(14.dp), clip = false)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MutedText,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontFamily = PlusJakartaSans,
                fontSize = 10.sp,
                color = MutedText,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun StatChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.shadow(2.dp, RoundedCornerShape(12.dp), clip = false)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = label, fontFamily = PlusJakartaSans, fontSize = 10.sp, color = MutedText, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, maxLines = 1)
        }
    }
}

@Composable
fun AddEditSavingScreen(
    savingId: String?,
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val goldPrice by repository.currentPrice.collectAsState()

    var title by remember { mutableStateOf("") }
    var targetGrams by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf("") }
    var targetError by remember { mutableStateOf("") }
    var saveError by remember { mutableStateOf("") }

    val isEditMode = savingId != null

    LaunchedEffect(savingId) {
        if (isEditMode && savingId != null) {
            val goal = repository.getGoalById(savingId)
            if (goal != null) {
                title = goal.title
                targetGrams = goal.targetGrams.toString()
                description = goal.description ?: ""
            }
        }
    }

    val targetGramsDouble = targetGrams.toDoubleOrNull() ?: 0.0
    val estimatedValue = targetGramsDouble * goldPrice.pricePerGramIdr
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Gold400)
            }
            Text(
                text = if (isEditMode) "Edit Tabungan" else "Buat Tabungan Baru",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Settings", tint = MutedText)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero banner image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF6D4C0A), Color(0xFFB8860B), Gold400))),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "Mulai Rencana Masa Depan",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Form Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isDark) Neutral800 else Color.White)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Nama Target",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.height(6.dp))
                GoldTextField(
                    value = title,
                    onValueChange = { title = it; titleError = "" },
                    label = "",
                    placeholder = "Contoh: Tabungan Menikah",
                    errorText = titleError,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Target Berat (gram)",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.height(6.dp))
                GoldTextField(
                    value = targetGrams,
                    onValueChange = { targetGrams = it; targetError = "" },
                    label = "",
                    placeholder = "10",
                    errorText = targetError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Deskripsi",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.height(6.dp))
                GoldTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "",
                    placeholder = "Tulis catatan atau tujuan tabungan ini...",
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                if (targetGramsDouble > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                    // Estimate info box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Gold100)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = null,
                                tint = Gold400,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Target: ",
                                fontFamily = PlusJakartaSans,
                                fontSize = 13.sp,
                                color = MutedText
                            )
                            Text(
                                text = "${String.format(Locale.US, "%.0f", targetGramsDouble)} gram",
                                fontFamily = PlusJakartaSans,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Gold400
                            )
                            Text(
                                text = " ≈ ${formatIdr(estimatedValue)}",
                                fontFamily = PlusJakartaSans,
                                fontSize = 13.sp,
                                color = MutedText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (saveError.isNotEmpty()) {
                Text(
                    text = saveError,
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontFamily = PlusJakartaSans,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            GoldButton(
                text = "Simpan Tabungan",
                onClick = {
                    var isValid = true
                    if (title.isEmpty()) {
                        titleError = "Nama target tidak boleh kosong"
                        isValid = false
                    }
                    val targetVal = targetGrams.toDoubleOrNull()
                    if (targetVal == null || targetVal <= 0.0) {
                        targetError = "Masukkan target gram yang valid (> 0)"
                        isValid = false
                    }
                    if (isValid && targetVal != null) {
                        coroutineScope.launch {
                            try {
                                if (isEditMode && savingId != null) {
                                    repository.updateGoal(savingId, title, targetVal, description)
                                } else {
                                    repository.addGoal(title, targetVal, description)
                                }
                                navController.navigate("main?tab=saving") {
                                    popUpTo("main?tab={tab}") { inclusive = true }
                                }
                            } catch (e: Exception) {
                                saveError = "Gagal menyimpan: ${e.localizedMessage}"
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Estimasi nilai berdasarkan harga emas hari ini. Nilai dapat berubah sewaktu-waktu mengikuti pasar global.",
                fontFamily = PlusJakartaSans,
                fontSize = 12.sp,
                color = MutedText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
