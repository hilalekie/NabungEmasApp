package com.example.nabungemas.ui.screens

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
    repository: NabungEmasRepository = NabungEmasRepository.INSTANCE,
    modifier: Modifier = Modifier
) {
    val goals by repository.goals.collectAsState()
    val transactions by repository.transactions.collectAsState()
    val isDark = isSystemInDarkTheme()

    val totalGrams = goals.sumOf { it.accumulatedGrams }
    val totalTransactions = transactions.size
    val avgProgress = if (goals.isNotEmpty()) goals.map { it.progress }.average() * 100 else 0.0

    var darkModeEnabled by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        ConfirmationDialog(
            onDismissRequest = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            title = "Keluar dari Akun?",
            message = "Apakah Anda yakin ingin keluar? Anda perlu masuk kembali untuk mengakses tabungan Anda.",
            confirmButtonText = "Logout",
            isDeleteAction = true
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) MaterialTheme.colorScheme.background else Color(0xFFF7F5F0))
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Gold400
                )
            }
            Text(
                text = "Profil Saya",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { }) {
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
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Gold400, Gold300))),
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(label = "Tabungan", value = "${String.format("%.1f", totalGrams)}g", modifier = Modifier.weight(1f))
                ProfileStatCard(label = "Transaksi", value = "$totalTransactions", modifier = Modifier.weight(1f))
                ProfileStatCard(label = "Terkumpul", value = "${avgProgress.toInt()}%", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileSectionHeader(title = "Akun")
            Spacer(modifier = Modifier.height(8.dp))
            ProfileMenuCard(
                items = listOf(
                    ProfileMenuItem(Icons.Rounded.Person, "Edit Profile", onClick = { navController.navigate(Screen.EditProfile.route) }),
                    ProfileMenuItem(Icons.Rounded.Lock, "Password", onClick = { navController.navigate(Screen.ChangePassword.route) }),
                    ProfileMenuItem(Icons.Rounded.Security, "Verifikasi Identitas", onClick = {})
                ),
                isDark = isDark
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Rounded.DarkMode, contentDescription = null, tint = MutedText, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Dark Mode", fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                    Switch(
                        checked = darkModeEnabled,
                        onCheckedChange = { darkModeEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Gold400)
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null, tint = MutedText, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Notifikasi", fontFamily = PlusJakartaSans, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = null, tint = MutedText, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ProfileSectionHeader(title = "Lainnya")
            Spacer(modifier = Modifier.height(8.dp))
            ProfileMenuCard(
                items = listOf(
                    ProfileMenuItem(Icons.Rounded.Help, "Pusat Bantuan", onClick = {}),
                    ProfileMenuItem(Icons.Rounded.Info, "Syarat & Ketentuan", onClick = {})
                ),
                isDark = isDark
            )

            Spacer(modifier = Modifier.height(28.dp))

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
        }
    }
}

@Composable
fun ProfileSectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Medium,
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