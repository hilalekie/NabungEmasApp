package com.example.nabungemas.ui.screens

<<<<<<< HEAD
=======
import androidx.compose.foundation.Image
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.data.NabungEmasRepository
import com.example.nabungemas.ui.components.ConfirmationDialog
import com.example.nabungemas.ui.navigation.Screen
import com.example.nabungemas.ui.theme.Error500
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success500

@Composable
fun ProfileScreen(
    navController: NavController,
<<<<<<< HEAD
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
=======
    authViewModel: AuthViewModel, // SUNTIKKAN VIEWMODEL DI SINI GESS
    modifier: Modifier = Modifier
) {
    val repository = remember { NabungEmasRepository.INSTANCE }
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    val goals by repository.goals.collectAsState()
    val transactions by repository.transactions.collectAsState()
    val isDark = isSystemInDarkTheme()

<<<<<<< HEAD
=======
    // 1. Ambil data asli pendaftaran Supabase kamu (Nama & Email Real)!
    val userFullName = authViewModel.getCurrentUserFullName()
    val userEmail = authViewModel.getCurrentUserEmail()

    // Hitung ringkasan statistik matematis untuk ditampilkan di kartu gess
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    val totalGrams = goals.sumOf { it.accumulatedGrams }
    val totalTransactions = transactions.size
    val avgProgress = if (goals.isNotEmpty()) goals.map { it.progress }.average() * 100 else 0.0

    var darkModeEnabled by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

<<<<<<< HEAD
=======
    // Dialog konfirmasi logout asli
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    if (showLogoutDialog) {
        ConfirmationDialog(
            onDismissRequest = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
<<<<<<< HEAD
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            title = "Keluar dari Akun?",
            message = "Apakah Anda yakin ingin keluar? Anda perlu masuk kembali untuk mengakses tabungan Anda.",
=======
                authViewModel.signOut {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            title = "Keluar dari Akun?",
            message = "Apakah Anda yakin ingin keluar?",
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            confirmButtonText = "Logout",
            isDeleteAction = true
        )
    }

    Column(
<<<<<<< HEAD
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        // Top App Bar
=======
        modifier = modifier.fillMaxSize().background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        // Bagian Atas/Header Bar (Slot "Top Header Bar Slot")
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
<<<<<<< HEAD
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Gold400
                )
=======
                .background(if (isDark) MaterialTheme.colorScheme.surface else Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Gold400)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            }
            Text(
                text = "Profil Saya",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
<<<<<<< HEAD
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* Edit profile action */ }) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit Profil",
                    tint = Gold400
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Avatar Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(96.dp)) {
                    // Gold gradient border ring
=======
                fontSize = 18.sp,
                color = Gold400,
                modifier = Modifier.padding(start = 8.dp).weight(1f)
            )
            IconButton(onClick = { navController.navigate(Screen.EditProfile.route) }) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit", tint = Gold400)
            }
        }

        // Konten Profil Vertikal Scroll
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(12.dp))

            // Bagian Avatar & Identitas User
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(96.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(
<<<<<<< HEAD
                                Brush.linearGradient(listOf(Gold400, Gold300))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(Gold200),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Person,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    // Online indicator dot
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Success500)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Muhammad Fatahila",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "fatahila.m@email.com",
                    fontFamily = PlusJakartaSans,
                    fontSize = 13.sp,
                    color = MutedText
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gold100)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Member sejak Mei 2024",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Gold400
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    label = "Tabungan",
                    value = "${String.format("%.1f", totalGrams)}g",
                    modifier = Modifier.weight(1f)
                )
                ProfileStatCard(
                    label = "Transaksi",
                    value = "$totalTransactions",
                    modifier = Modifier.weight(1f)
                )
                ProfileStatCard(
                    label = "Terkumpul",
                    value = "${avgProgress.toInt()}%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section: Akun
            ProfileSectionHeader(title = "Akun")
            Spacer(modifier = Modifier.height(8.dp))
            ProfileMenuCard(
                items = listOf(
                    ProfileMenuItem(Icons.Rounded.Person, "Edit Profile", onClick = { navController.navigate(Screen.EditProfile.route) }),
                    ProfileMenuItem(Icons.Rounded.Lock, "Password", onClick = { navController.navigate(Screen.ChangePassword.route) }),
                    ProfileMenuItem(Icons.Rounded.Security, "Verifikasi Identitas", onClick = {})
=======
                                brush = Brush.linearGradient(
                                    colors = listOf(Gold300, Gold100, Gold200)
                                )
                            )
                            .border(3.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Placeholder Avatar Ringan
                        Icon(imageVector = Icons.Rounded.Person, contentDescription = "Avatar", tint = Gold400, modifier = Modifier.size(52.dp))
                    }
                    // Titik Hijau Status Online
                    Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(Success500).border(2.dp, Color.White, CircleShape))
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Menampilkan nama lengkap dan email asli pendaftaran Supabase kamu!
                Text(text = userFullName, fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = userEmail, fontFamily = PlusJakartaSans, fontSize = 13.sp, color = MutedText)

                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(Gold100).padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = "Member sejak Mei 2024", fontFamily = PlusJakartaSans, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = Gold400)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Row Statistik Matematis Nyata
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatCard(label = "Tabungan", value = String.format("%.2fg", totalGrams), modifier = Modifier.weight(1f))
                ProfileStatCard(label = "Transaksi", value = totalTransactions.toString(), modifier = Modifier.weight(1f))
                ProfileStatCard(label = "Terkumpul", value = String.format("%.0f%%", avgProgress), modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bagian Pengaturan Akun
            ProfileSectionHeader(title = "Pengaturan Akun")
            Spacer(modifier = Modifier.height(8.dp))
            ProfileMenuCard(
                items = listOf(
                    ProfileMenuItem(icon = Icons.Rounded.Person, label = "Ubah Profil", onClick = { navController.navigate(Screen.EditProfile.route) }),
                    ProfileMenuItem(icon = Icons.Rounded.Lock, label = "Ganti Kata Sandi", onClick = { navController.navigate(Screen.ChangePassword.route) }),
                    ProfileMenuItem(icon = Icons.Rounded.Security, label = "Verifikasi Identitas", onClick = { /* Aksi opsional */ })
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                ),
                isDark = isDark
            )

<<<<<<< HEAD
            Spacer(modifier = Modifier.height(20.dp))

            // Section: Preferensi
            ProfileSectionHeader(title = "Preferensi")
            Spacer(modifier = Modifier.height(8.dp))
            val bgColor = if (isDark) Neutral800 else Color.White
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(bgColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
=======
            Spacer(modifier = Modifier.height(16.dp))

            // Bagian Preferensi
            ProfileSectionHeader(title = "Preferensi")
            Spacer(modifier = Modifier.height(8.dp))
            val cardBg = if (isDark) Neutral800 else Color.White
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).clip(RoundedCornerShape(16.dp)).background(cardBg)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Rounded.DarkMode, contentDescription = null, tint = MutedText, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(16.dp))
<<<<<<< HEAD
                    Text(text = "Dark Mode", fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
=======
                    Text(text = "Mode Gelap", fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    Switch(
                        checked = darkModeEnabled,
                        onCheckedChange = { darkModeEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Gold400)
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                Row(
<<<<<<< HEAD
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 16.dp),
=======
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null, tint = MutedText, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(16.dp))
<<<<<<< HEAD
                    Text(text = "Notifikasi", fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
=======
                    Text(text = "Notifikasi Harga Emas", fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = null, tint = MutedText, modifier = Modifier.size(18.dp))
                }
            }

<<<<<<< HEAD
            Spacer(modifier = Modifier.height(20.dp))

            // Section: Lainnya
=======
            Spacer(modifier = Modifier.height(16.dp))

            // Bagian Lainnya
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            ProfileSectionHeader(title = "Lainnya")
            Spacer(modifier = Modifier.height(8.dp))
            ProfileMenuCard(
                items = listOf(
<<<<<<< HEAD
                    ProfileMenuItem(Icons.Rounded.Help, "Pusat Bantuan", onClick = {}),
                    ProfileMenuItem(Icons.Rounded.Info, "Syarat & Ketentuan", onClick = {})
=======
                    ProfileMenuItem(icon = Icons.Rounded.Help, label = "Pusat Bantuan", onClick = { }),
                    ProfileMenuItem(icon = Icons.Rounded.Info, label = "Syarat & Ketentuan", onClick = { /* TODO: Tambahkan aksi S&K nanti */ })
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                ),
                isDark = isDark
            )

<<<<<<< HEAD
            Spacer(modifier = Modifier.height(28.dp))

            // Logout Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .border(2.dp, Error500, RoundedCornerShape(28.dp))
                    .clickable { showLogoutDialog = true }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Rounded.Logout, contentDescription = null, tint = Error500, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Logout", fontFamily = PlusJakartaSans, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Error500)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
=======
            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Logout Utama Cloud Supabase
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(27.dp))
                        .border(1.5.dp, Error500, RoundedCornerShape(27.dp))
                        .background(if (isDark) Color.Transparent else Color(0xFFFFEBEE))
                        .clickable { showLogoutDialog = true },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Rounded.Logout, contentDescription = "Logout", tint = Error500, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Keluar dari Akun", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Error500)
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        }
    }
}

@Composable
fun ProfileSectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        fontFamily = PlusJakartaSans,
<<<<<<< HEAD
        fontWeight = FontWeight.Medium,
=======
        fontWeight = FontWeight.Bold,
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        fontSize = 11.sp,
        color = MutedText,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

data class ProfileMenuItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun ProfileMenuCard(
    items: List<ProfileMenuItem>,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isDark) Neutral800 else Color.White
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = item.onClick)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = item.icon, contentDescription = null, tint = MutedText, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.label, fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = null, tint = MutedText, modifier = Modifier.size(18.dp))
            }
            if (index < items.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            }
        }
    }
}

@Composable
fun ProfileStatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isDark) Neutral800 else Color.White)
            .padding(vertical = 14.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontFamily = PlusJakartaSans, fontSize = 11.sp, color = MutedText)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Gold400)
    }
}
