package com.example.nabungemas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.components.GoldTextField
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun EditProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(authViewModel.getCurrentUserFullName()) }
    var email by remember { mutableStateOf(authViewModel.getCurrentUserEmail()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
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
                text = "Edit Profil",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Avatar Edit Section
            Box(
                modifier = Modifier.size(104.dp),
                contentAlignment = Alignment.Center
            ) {
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
                
                // Camera Icon overlay
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(2.dp, MaterialTheme.colorScheme.background, CircleShape)
                        .clickable { /* action change avatar */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = "Change Avatar",
                        tint = Gold400,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Form
            GoldTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nama Lengkap",
                placeholder = "Masukkan nama lengkap",
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Person, contentDescription = null, tint = MutedText)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            GoldTextField(
                value = email,
                onValueChange = { email = it },
                label = "Alamat Email",
                placeholder = "Masukkan alamat email",
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Email, contentDescription = null, tint = MutedText)
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            val isFormValid = name.isNotBlank() && email.isNotBlank()
            var saveError by remember { mutableStateOf("") }
            val isLoading by authViewModel.isLoading.collectAsState()

            if (saveError.isNotEmpty()) {
                Text(
                    text = saveError,
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontFamily = PlusJakartaSans,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            GoldButton(
                text = if (isLoading) "Menyimpan..." else "Simpan Perubahan",
                onClick = {
                    saveError = ""
                    authViewModel.updateProfile(name, email) { success, errorMsg ->
                        if (success) {
                            navController.popBackStack()
                        } else {
                            saveError = "Gagal menyimpan: ${errorMsg ?: "Terjadi kesalahan"}"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid && !isLoading
            )
        }
    }
}
