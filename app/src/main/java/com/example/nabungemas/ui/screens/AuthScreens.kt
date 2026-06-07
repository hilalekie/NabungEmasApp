package com.example.nabungemas.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
<<<<<<< HEAD
=======
import androidx.compose.runtime.collectAsState
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nabungemas.ui.components.GoldButton
import com.example.nabungemas.ui.components.GoldTextField
import com.example.nabungemas.ui.components.OutlineButton
import com.example.nabungemas.ui.navigation.Screen
import com.example.nabungemas.ui.theme.DividerColor
import com.example.nabungemas.ui.theme.Gold100
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold300
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral100
import com.example.nabungemas.ui.theme.Neutral200
import com.example.nabungemas.ui.theme.Neutral700
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.Neutral900
import com.example.nabungemas.ui.theme.PlusJakartaSans
import com.example.nabungemas.ui.theme.Success500
import kotlinx.coroutines.delay

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) Neutral800 else Color.White
    val borderCol = if (isDark) Neutral200.copy(alpha = 0.2f) else Neutral200

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(28.dp))
            .border(1.5.dp, borderCol, RoundedCornerShape(28.dp))
            .background(bgColor)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
<<<<<<< HEAD
        // Simple drawn Google logo
=======
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "G",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF4285F4)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Google",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

<<<<<<< HEAD


@Composable
fun LoginScreen(
    navController: NavController,
=======
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }

<<<<<<< HEAD
    val scrollState = rememberScrollState()

=======
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    val scrollState = rememberScrollState()

    LaunchedEffect(authState) {
        if (authState == "LOGIN_SUCCESS") {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
<<<<<<< HEAD
        
        // Brand logo header
=======

>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Gold100)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
<<<<<<< HEAD
                Icon(
                    imageVector = Icons.Rounded.Savings,
                    contentDescription = "Logo",
                    tint = Gold400,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "NabungEmas",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Gold400
            )
=======
                Icon(imageVector = Icons.Rounded.Savings, contentDescription = "Logo", tint = Gold400, modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "NabungEmas", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 28.sp, color = Gold400)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        }

        Spacer(modifier = Modifier.height(40.dp))

<<<<<<< HEAD
        Text(
            text = "Selamat Datang Kembali",
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = "Masuk untuk melanjutkan menabung emas.",
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            color = MutedText,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Form Fields
        GoldTextField(
            value = email,
            onValueChange = { 
                email = it
                emailError = ""
            },
=======
        Text(text = "Selamat Datang Kembali", fontFamily = PlusJakartaSans, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface)
        Text(text = "Masuk untuk melanjutkan menabung emas.", fontFamily = PlusJakartaSans, fontSize = 14.sp, color = MutedText, modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(32.dp))

        if (errorMessage != null) {
            Text(text = errorMessage!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))
        }

        GoldTextField(
            value = email,
            onValueChange = { email = it; emailError = ""; authViewModel.clearError() },
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            label = "Email",
            placeholder = "nama@email.com",
            errorText = emailError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        GoldTextField(
            value = password,
<<<<<<< HEAD
            onValueChange = { password = it },
=======
            onValueChange = { password = it; authViewModel.clearError() },
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            label = "Password",
            placeholder = "••••••••",
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
<<<<<<< HEAD
                    Icon(imageVector = icon, contentDescription = "Toggle Password Visibility", tint = MutedText)
=======
                    Icon(imageVector = icon, contentDescription = null, tint = MutedText)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

<<<<<<< HEAD
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Lupa kata sandi?",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Gold400,
                modifier = Modifier.clickable { /* action */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        GoldButton(
            text = "Masuk",
=======
        Spacer(modifier = Modifier.height(32.dp))

        GoldButton(
            text = if (isLoading) "Memuat..." else "Masuk",
            enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty(),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            onClick = {
                if (email.isEmpty()) {
                    emailError = "Email tidak boleh kosong"
                } else if (!email.contains("@")) {
                    emailError = "Format email tidak valid"
                } else {
<<<<<<< HEAD
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
=======
                    authViewModel.signIn(email, password)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
<<<<<<< HEAD

        // Separator
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = if (isSystemInDarkTheme()) Neutral700 else DividerColor
            )
            Text(
                text = "Atau masuk dengan",
                fontFamily = PlusJakartaSans,
                fontSize = 12.sp,
                color = MutedText,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = if (isSystemInDarkTheme()) Neutral700 else DividerColor
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        GoogleSignInButton(
            onClick = {
                // Fast login
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        // Footer register link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Belum punya akun? ",
                fontFamily = PlusJakartaSans,
                fontSize = 14.sp,
                color = MutedText
            )
            Text(
                text = "Daftar sekarang",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Gold400,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
=======
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Belum punya akun? ", fontFamily = PlusJakartaSans, fontSize = 14.sp, color = MutedText)
            Text(text = "Daftar sekarang", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Gold400,
                modifier = Modifier.clickable { navController.navigate(Screen.Register.route) }
            )
        }
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    }
}

@Composable
fun RegisterScreen(
    navController: NavController,
<<<<<<< HEAD
=======
    authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsChecked by remember { mutableStateOf(false) }

<<<<<<< HEAD
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

=======
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    var passwordError by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(authState) {
        if (authState == "REGISTER_SUCCESS") {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
<<<<<<< HEAD
        // Custom TopAppBar with statusBarsPadding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 12.dp, vertical = 8.dp),
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
                text = "Daftar Akun",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Gold400,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Settings",
                    tint = MutedText
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar Profile Slot
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Gold200)
                    .border(4.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null,
                    tint = Gold400,
                    modifier = Modifier.size(48.dp)
                )
                // Add Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Gold400, Gold300)))
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add photo",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Buat Akun Baru",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Registration Form
            GoldTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nama Lengkap",
                placeholder = "Masukkan nama lengkap",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GoldTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                placeholder = "contoh@email.com",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GoldTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Nomor HP",
                placeholder = "812xxxxxx",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Text(
                        text = "+62",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

=======
        Row(
            modifier = Modifier.fillMaxWidth().statusBarsPadding().background(MaterialTheme.colorScheme.surface).padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Gold400)
            }
            Text(text = "Daftar Akun", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Gold400, modifier = Modifier.padding(start = 8.dp))
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Buat Akun Baru", fontFamily = PlusJakartaSans, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))
            }

            GoldTextField(value = name, onValueChange = { name = it }, label = "Nama Lengkap", placeholder = "Masukkan nama lengkap", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            GoldTextField(value = email, onValueChange = { email = it }, label = "Email", placeholder = "contoh@email.com", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            GoldTextField(value = phone, onValueChange = { phone = it }, label = "Nomor HP", placeholder = "812xxxxxx", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            Spacer(modifier = Modifier.height(16.dp))

            GoldTextField(
                value = password,
<<<<<<< HEAD
                onValueChange = { password = it },
                label = "Kata Sandi",
                placeholder = "••••••••",
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = MutedText)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GoldTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Konfirmasi Sandi",
                placeholder = "••••••••",
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (isConfirmPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = MutedText)
                    }
                },
=======
                onValueChange = { password = it; passwordError = "" },
                label = "Kata Sandi",
                placeholder = "••••••••",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            GoldTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; passwordError = "" },
                label = "Konfirmasi Sandi",
                placeholder = "••••••••",
                errorText = passwordError,
                visualTransformation = PasswordVisualTransformation(),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

<<<<<<< HEAD
            Spacer(modifier = Modifier.height(16.dp))

            // T&C Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
=======
            Spacer(modifier = Modifier.height(24.dp))

            // PERBAIKAN UTAMA: Menyisipkan komponen Row Checkbox S&K milik kelompokmu yang hilang
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            ) {
                Checkbox(
                    checked = termsChecked,
                    onCheckedChange = { termsChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Gold400,
                        checkmarkColor = Color.Black
                    )
                )
                Text(
                    text = "Saya setuju dengan Syarat & Ketentuan serta Kebijakan Privasi yang berlaku.",
                    fontFamily = PlusJakartaSans,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = MutedText,
<<<<<<< HEAD
                    modifier = Modifier.padding(start = 4.dp, top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            GoldButton(
                text = "Daftar Sekarang",
                onClick = {
                    if (termsChecked && name.isNotEmpty() && email.isNotEmpty()) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                enabled = termsChecked && name.isNotEmpty() && email.isNotEmpty(),
=======
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            GoldButton(
                text = if (isLoading) "Mendaftarkan..." else "Daftar Sekarang",
                onClick = {
                    if (password != confirmPassword) {
                        passwordError = "Konfirmasi kata sandi tidak cocok"
                    } else if (password.length < 6) {
                        passwordError = "Kata sandi Supabase minimal harus 6 karakter"
                    } else {
                        authViewModel.signUp(email, password, name)
                    }
                },
                // Ditambahkan validasi 'confirmPassword' dan 'termsChecked' wajib bernilai true gess
                enabled = !isLoading && termsChecked && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
<<<<<<< HEAD

            // Footer login link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sudah punya akun? ",
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    color = MutedText
                )
                Text(
                    text = "Masuk di sini",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Gold400,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
=======
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Sudah punya akun? ", fontFamily = PlusJakartaSans, fontSize = 14.sp, color = MutedText)
                Text(text = "Masuk di sini", fontFamily = PlusJakartaSans, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Gold400,
                    modifier = Modifier.clickable { navController.popBackStack() }
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
