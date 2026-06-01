package com.example.nabungemas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.nabungemas.data.GoldTransaction
import com.example.nabungemas.data.NabungEmasRepository
import com.example.nabungemas.data.SavingGoal
import com.example.nabungemas.ui.components.ConfirmationDialog
import com.example.nabungemas.ui.components.EmptyStateView
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.components.GoldTextField
import com.example.nabungemas.ui.components.formatIdr
import com.example.nabungemas.ui.navigation.Screen
import com.example.nabungemas.ui.theme.Error500
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral100
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success500
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddEditTransactionScreen(
    transactionId: String?,
    autoGoalId: String?,
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val goals by repository.goals.collectAsState()
    val goldPrice by repository.currentPrice.collectAsState()

    var selectedGoal by remember { mutableStateOf<SavingGoal?>(null) }
    var grams by remember { mutableStateOf("") }
    var pricePerGram by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var dateMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    var gramsError by remember { mutableStateOf("") }
    var goalError by remember { mutableStateOf("") }

    var showGoalPicker by remember { mutableStateOf(false) }

    val isEditMode = transactionId != null
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale("in", "ID")) }
    val isDark = isSystemInDarkTheme()

    LaunchedEffect(transactionId, autoGoalId, goals) {
        if (pricePerGram.isEmpty()) {
            pricePerGram = goldPrice.pricePerGramIdr.toInt().toString()
        }
        if (isEditMode && transactionId != null) {
            val tx = repository.getTransactionById(transactionId)
            if (tx != null) {
                selectedGoal = goals.find { it.id == tx.goalId }
                grams = tx.grams.toString()
                pricePerGram = tx.pricePerGram.toInt().toString()
                notes = tx.notes
                dateMillis = tx.date
            }
        } else if (autoGoalId != null) {
            selectedGoal = goals.find { it.id == autoGoalId }
        }
    }

    val gramsVal = grams.toDoubleOrNull() ?: 0.0
    val priceVal = pricePerGram.toDoubleOrNull() ?: 0.0
    val totalNominal = gramsVal * priceVal

    // Goal picker dialog
    if (showGoalPicker) {
        Dialog(onDismissRequest = { showGoalPicker = false }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Pilih Target Tabungan",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (goals.isEmpty()) {
                        Text(text = "Belum ada tabungan. Buat tabungan terlebih dahulu.", color = MutedText)
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            goals.forEach { goal ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedGoal?.id == goal.id) Gold100 else Color.Transparent
                                        )
                                        .clickable {
                                            selectedGoal = goal
                                            goalError = ""
                                            showGoalPicker = false
                                        }
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(Gold100),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = goal.title.take(1).uppercase(),
                                            fontFamily = PlusJakartaSans,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Gold400
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = goal.title,
                                        fontFamily = PlusJakartaSans,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

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
                text = if (isEditMode) "Edit Transaksi" else "Tambah Transaksi",
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
            // Form Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isDark) Neutral800 else Color.White)
                    .padding(20.dp)
            ) {
                // Pilih Tabungan
                Text(
                    text = "Pilih Tabungan",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            if (goalError.isNotEmpty()) Color.Red else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { showGoalPicker = true }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedGoal?.title ?: "Tabungan Masa Depan",
                        fontFamily = PlusJakartaSans,
                        fontSize = 15.sp,
                        color = if (selectedGoal == null) MutedText else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MutedText
                    )
                }
                if (goalError.isNotEmpty()) {
                    Text(text = goalError, fontSize = 11.sp, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Jumlah (gram)", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = MutedText)
                Spacer(modifier = Modifier.height(6.dp))
                GoldTextField(
                    value = grams,
                    onValueChange = { grams = it; gramsError = "" },
                    label = "",
                    placeholder = "0.00",
                    errorText = gramsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Harga per Gram (Rp)", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = MutedText)
                Spacer(modifier = Modifier.height(6.dp))
                GoldTextField(
                    value = pricePerGram,
                    onValueChange = { pricePerGram = it },
                    label = "",
                    placeholder = "937.500",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Tanggal Transaksi", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = MutedText)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dateFormat.format(Date(dateMillis)),
                        fontFamily = PlusJakartaSans,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Rounded.CalendarToday,
                        contentDescription = null,
                        tint = MutedText,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Catatan", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = MutedText)
                Spacer(modifier = Modifier.height(6.dp))
                GoldTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = "",
                    placeholder = "Contoh: Bonus bulanan...",
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estimasi Pengeluaran box - matching design
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Gold400.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .background(Gold100.copy(alpha = 0.5f))
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Estimasi Pengeluaran",
                            fontFamily = PlusJakartaSans,
                            fontSize = 12.sp,
                            color = MutedText
                        )
                        Text(
                            text = formatIdr(totalNominal),
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Gold400),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Savings,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            GoldButton(
                text = "Simpan Transaksi",
                onClick = {
                    var isValid = true
                    if (selectedGoal == null) {
                        goalError = "Pilih rencana tabungan target terlebih dahulu"
                        isValid = false
                    }
                    val gramsNum = grams.toDoubleOrNull()
                    if (gramsNum == null || gramsNum <= 0.0) {
                        gramsError = "Masukkan jumlah gram yang valid (> 0)"
                        isValid = false
                    }
                    if (isValid && gramsNum != null && selectedGoal != null) {
                        coroutineScope.launch {
                            if (isEditMode && transactionId != null) {
                                repository.updateTransaction(transactionId, selectedGoal!!.id, gramsNum, priceVal, dateMillis, notes)
                            } else {
                                repository.addTransaction(selectedGoal!!.id, gramsNum, priceVal, dateMillis, notes)
                            }
                            navController.popBackStack()
                        }
                    }
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.History, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tips cards (horizontal scroll like design)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    TipCard(
                        title = "Tips Menabung",
                        body = "Harga emas cenderung naik di akhir pekan. Lakukan transaksi di hari kerja untuk mendapatkan rate terbaik.",
                        bgColor = Gold100
                    )
                }
                item {
                    TipCard(
                        title = "Keamanan",
                        body = "Setiap transaksi diamankan dan diasuransikan. Emas Anda disimpan di tempat yang aman.",
                        bgColor = Color(0xFFE8F5E9)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun TipCard(title: String, body: String, bgColor: Color) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Gold400),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "i", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = body, fontFamily = PlusJakartaSans, fontSize = 12.sp, color = MutedText, lineHeight = 16.sp)
        }
    }
}

@Composable
fun TransactionDetailScreen(
    transactionId: String,
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    val transactions by repository.transactions.collectAsState()
    val tx = transactions.find { it.id == transactionId }
    val isDark = isSystemInDarkTheme()

    if (tx == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Transaksi tidak ditemukan.", color = MutedText)
        }
        return
    }

    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("in", "ID")) }

    if (showDeleteDialog) {
        ConfirmationDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirm = {
                coroutineScope.launch {
                    repository.deleteTransaction(transactionId)
                    navController.popBackStack()
                }
            },
            title = "Hapus Transaksi?",
            message = "Apakah Anda yakin ingin menghapus data transaksi ini? Tabungan gram Anda akan berkurang otomatis.",
            confirmButtonText = "Hapus",
            isDeleteAction = true
        )
    }

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
                text = "Detail Transaksi",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { navController.navigate(Screen.AddEditTransaction.createRoute(transactionId = transactionId)) }) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.onSurface)
            }
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete", tint = Error500)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Main detail card - white card with icon + amount prominent
            Card(
                colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(20.dp), clip = false)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Coin icon circle
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Gold400),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Gram amount - large green
                    Text(
                        text = "+${String.format(Locale.US, "%.2f", tx.grams)} gram",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Success500
                    )
                    Text(
                        text = "≈ ${formatIdr(tx.totalNominal)}",
                        fontFamily = PlusJakartaSans,
                        fontSize = 15.sp,
                        color = MutedText,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    Spacer(modifier = Modifier.height(20.dp))

                    // Detail rows
                    DetailItemRow(label = "Tabungan", value = tx.goalTitle)
                    Spacer(modifier = Modifier.height(14.dp))
                    DetailItemRow(label = "Harga/gram", value = formatIdr(tx.pricePerGram))
                    Spacer(modifier = Modifier.height(14.dp))
                    DetailItemRow(label = "Tanggal", value = dateFormat.format(Date(tx.date)))

                    if (tx.notes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Catatan",
                                fontFamily = PlusJakartaSans,
                                fontSize = 13.sp,
                                color = MutedText
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isDark) Color(0xFF2A2A2A) else Color(0xFFFFF8F0))
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = tx.notes,
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Insight banner card (like design)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF3D2B00), Color(0xFF8B6914)))),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "INSIGHT",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Emas naik 5% bulan ini",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DetailItemRow(
    label: String,
    value: String,
    valueColor: Color = Color.Unspecified,
    valueBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontFamily = PlusJakartaSans, fontSize = 14.sp, color = MutedText)
        Text(
            text = value,
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            fontWeight = if (valueBold) FontWeight.Bold else FontWeight.Medium,
            color = if (valueColor != Color.Unspecified) valueColor else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun TransactionHistoryScreen(
    navController: NavController,
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val transactions by repository.transactions.collectAsState()
    val filters = listOf("Semua", "Bulan Ini", "Beli Emas", "Jual Emas")
    var selectedFilter by remember { mutableStateOf("Semua") }
    val isDark = isSystemInDarkTheme()

    val filteredTransactions = remember(transactions, selectedFilter) {
        when (selectedFilter) {
            "Bulan Ini" -> {
                val oneMonthAgo = System.currentTimeMillis() - 86400000L * 30L
                transactions.filter { it.date >= oneMonthAgo }
            }
            else -> transactions
        }
    }

    val headerFormat = SimpleDateFormat("dd MMM yyyy", Locale("in", "ID")).apply {
        isLenient = false
    }
    val groupedTransactions = filteredTransactions
        .sortedByDescending { it.date }
        .groupBy { tx ->
            val cal = java.util.Calendar.getInstance().apply { timeInMillis = tx.date }
            "${cal.get(java.util.Calendar.DAY_OF_MONTH)} ${
                SimpleDateFormat("MMMM", Locale("in", "ID")).format(cal.time).uppercase()
            } ${cal.get(java.util.Calendar.YEAR)}"
        }

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
                text = "Riwayat Transaksi",
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

        // Filter chip row (horizontal scroll like design)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterTab(
                    label = filter,
                    isSelected = selectedFilter == filter,
                    onClick = { selectedFilter = filter }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (filteredTransactions.isEmpty()) {
            EmptyStateView(
                icon = Icons.Rounded.History,
                title = "Belum ada transaksi",
                message = "Anda belum melakukan transaksi apa pun. Transaksi Anda akan muncul di sini.",
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                groupedTransactions.forEach { (dateHeader, txList) ->
                    item {
                        Text(
                            text = dateHeader,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MutedText,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }
                    items(txList) { tx ->
                        TransactionHistoryCard(
                            tx = tx,
                            onClick = { navController.navigate(Screen.TransactionDetail.createRoute(tx.id)) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionHistoryCard(
    tx: GoldTransaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDark) Neutral800 else Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(16.dp), clip = false)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Gold100),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Gold400
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tx.goalTitle,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (tx.notes.isNotEmpty()) tx.notes else "Setoran rutin",
                    fontFamily = PlusJakartaSans,
                    fontSize = 12.sp,
                    color = MutedText,
                    maxLines = 1
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                // Gram badge - green pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Success500.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "+${String.format(Locale.US, "%.2f", tx.grams)} gram",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Success500
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatIdr(tx.totalNominal),
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun FilterTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) Gold400 else if (isSystemInDarkTheme()) Neutral800 else Color.White
    val textCol = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
    val borderColor = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = PlusJakartaSans,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.sp,
            color = textCol
        )
    }
}

@Composable
fun RecentTransactionItemRow(
    tx: GoldTransaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TransactionHistoryCard(tx = tx, onClick = onClick, modifier = modifier)
}
