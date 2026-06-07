package com.example.nabungemas.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Policy
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.ui.components.ConfirmationDialog
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.navigation.Screen
import com.example.nabungemas.ui.theme.Error500
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun AboutScreen(
    navController: NavController,
<<<<<<< HEAD
=======
    authViewModel: com.example.nabungemas.ui.screens.AuthViewModel, // Tambahkan baris ini gess
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) Neutral800 else Neutral050

    if (showLogoutDialog) {
        ConfirmationDialog(
            onDismissRequest = { showLogoutDialog = false },
            onConfirm = {
<<<<<<< HEAD
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
=======
                // UPDATE: Panggil fungsi logout asli Supabase di sini gess!
                authViewModel.signOut {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                }
            },
            title = "Keluar dari Aplikasi?",
            message = "Apakah Anda yakin ingin keluar dari akun Anda?",
            confirmButtonText = "Keluar",
            isDeleteAction = true,
            icon = Icons.Rounded.Logout
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top App Bar with status bar padding
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tentang Aplikasi",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Gold400
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // App Logo Box
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Gold100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Savings,
                        contentDescription = "App Logo",
                        tint = Gold400,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "NabungEmas App",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Versi 1.0.0",
                    fontFamily = PlusJakartaSans,
                    fontSize = 13.sp,
                    color = MutedText,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Section 1: Developer Info
                AboutSectionCard(title = "Pengembang") {
                    AboutLinkRow(
                        icon = Icons.Rounded.Person,
                        label = "Developer",
                        value = "Google DeepMind Agentic Team"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AboutLinkRow(
                        icon = Icons.Rounded.Link,
                        label = "GitHub Repository",
                        value = "github.com/nabungemas",
                        isClickable = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section 2: Open Source Libraries
                AboutSectionCard(title = "Pustaka Open Source") {
                    AboutLinkRow(icon = Icons.Rounded.Code, label = "Jetpack Compose", value = "v1.6.0")
                    Spacer(modifier = Modifier.height(12.dp))
                    AboutLinkRow(icon = Icons.Rounded.Code, label = "Material Design 3", value = "v1.2.0")
                    Spacer(modifier = Modifier.height(12.dp))
                    AboutLinkRow(icon = Icons.Rounded.Code, label = "Kotlin Coroutines", value = "v1.7.3")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section 3: Legal & Actions
                AboutSectionCard(title = "Hukum & Tindakan") {
                    AboutLinkRow(
                        icon = Icons.Rounded.Policy,
                        label = "Kebijakan Privasi",
                        value = "Baca kebijakan",
                        isClickable = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AboutLinkRow(
                        icon = Icons.Rounded.Info,
                        label = "Syarat & Ketentuan",
                        value = "Baca syarat",
                        isClickable = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Logout button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showLogoutDialog = true }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = null,
                            tint = Error500,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Keluar dari Akun",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Error500
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun AboutSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Neutral800 else Neutral050),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(16.dp), clip = false)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Gold400
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun AboutLinkRow(
    icon: ImageVector,
    label: String,
    value: String,
    isClickable: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontFamily = PlusJakartaSans,
            fontSize = 13.sp,
            fontWeight = if (isClickable) FontWeight.Bold else FontWeight.Normal,
            color = if (isClickable) Gold400 else MutedText,
            modifier = if (isClickable) Modifier.clickable { /* action */ } else Modifier
        )
    }
}
