package com.example.nabungemas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.components.GoldTextField
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isDark = isSystemInDarkTheme()

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
                text = "Ubah Kata Sandi",
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
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Kata sandi harus terdiri dari minimal 8 karakter dengan kombinasi huruf dan angka.",
                fontFamily = PlusJakartaSans,
                fontSize = 14.sp,
                color = MutedText
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            GoldTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = "Kata Sandi Lama",
                placeholder = "Masukkan kata sandi lama",
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Lock, contentDescription = null, tint = MutedText)
                }
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            GoldTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "Kata Sandi Baru",
                placeholder = "Masukkan kata sandi baru",
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Lock, contentDescription = null, tint = MutedText)
                }
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            GoldTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Konfirmasi Kata Sandi Baru",
                placeholder = "Ulangi kata sandi baru",
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Lock, contentDescription = null, tint = MutedText)
                }
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            val isFormValid = oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && (newPassword == confirmPassword)
            
            GoldButton(
                text = "Simpan Perubahan",
                onClick = { 
                    // Add logic to save password
                    navController.popBackStack() 
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid
            )
        }
    }
}
