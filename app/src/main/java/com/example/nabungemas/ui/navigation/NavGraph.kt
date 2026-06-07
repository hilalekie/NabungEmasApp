package com.example.nabungemas.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nabungemas.ui.screens.AboutScreen
import com.example.nabungemas.ui.screens.AddEditSavingScreen
import com.example.nabungemas.ui.screens.AddEditTransactionScreen
import com.example.nabungemas.ui.screens.ChangePasswordScreen
import com.example.nabungemas.ui.screens.EditProfileScreen
import com.example.nabungemas.ui.screens.HomeScreen
import com.example.nabungemas.ui.screens.LoginScreen
import com.example.nabungemas.ui.screens.PriceScreen
import com.example.nabungemas.ui.screens.ProfileScreen
import com.example.nabungemas.ui.screens.RegisterScreen
import com.example.nabungemas.ui.screens.SavingDetailScreen
import com.example.nabungemas.ui.screens.SavingListScreen
import com.example.nabungemas.ui.screens.SplashScreen
import com.example.nabungemas.ui.screens.TransactionDetailScreen
import com.example.nabungemas.ui.screens.TransactionHistoryScreen
import com.example.nabungemas.ui.theme.Gold200
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
<<<<<<< HEAD
=======
    // Menyediakan satu ViewModel autentikasi untuk dipakai bersama
    val authViewModel: com.example.nabungemas.ui.screens.AuthViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()

>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
<<<<<<< HEAD
        
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
=======

        composable(Screen.Login.route) {
            // Memanggil fungsi LoginScreen asli kelompokmu dengan tambahan parameter viewModel
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(Screen.Register.route) {
            // Memanggil fungsi RegisterScreen asli kelompokmu dengan tambahan parameter viewModel
            RegisterScreen(navController = navController, authViewModel = authViewModel)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        }

        composable(
            route = "main?tab={tab}",
            arguments = listOf(
<<<<<<< HEAD
                navArgument("tab") { 
=======
                navArgument("tab") {
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val requestedTab = backStackEntry.arguments?.getString("tab")
            MainTabsHost(
                navController = navController,
<<<<<<< HEAD
                initialTabRoute = requestedTab
=======
                initialTabRoute = requestedTab,
                authViewModel = authViewModel
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            )
        }

        composable(
            route = Screen.SavingDetail.route,
            arguments = listOf(navArgument("savingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val savingId = backStackEntry.arguments?.getString("savingId") ?: ""
            SavingDetailScreen(savingId = savingId, navController = navController)
        }

        composable(
            route = Screen.AddEditSaving.route,
            arguments = listOf(
                navArgument("savingId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val savingId = backStackEntry.arguments?.getString("savingId")
            AddEditSavingScreen(savingId = savingId, navController = navController)
        }

        composable(
            route = Screen.AddEditTransaction.route,
            arguments = listOf(
                navArgument("transactionId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("savingId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            val savingId = backStackEntry.arguments?.getString("savingId")
            AddEditTransactionScreen(
                transactionId = transactionId,
                autoGoalId = savingId,
                navController = navController
            )
        }

        composable(
            route = Screen.TransactionDetail.route,
            arguments = listOf(navArgument("transactionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: ""
            TransactionDetailScreen(transactionId = transactionId, navController = navController)
        }

        composable(Screen.TransactionHistory.route) {
            TransactionHistoryScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
<<<<<<< HEAD
            ProfileScreen(navController = navController)
=======
            // Memanggil fungsi ProfileScreen asli kelompokmu dengan tambahan parameter viewModel
            ProfileScreen(navController = navController, authViewModel = authViewModel)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
        }

        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(navController = navController)
        }
    }
}

@Composable
fun MainTabsHost(
    navController: NavHostController,
<<<<<<< HEAD
    initialTabRoute: String?
=======
    initialTabRoute: String?,
    authViewModel: com.example.nabungemas.ui.screens.AuthViewModel
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
) {
    val tabs = listOf(
        MainTab.Home,
        MainTab.Saving,
        MainTab.Price,
        MainTab.About
    )

    var currentTab by remember { mutableStateOf<MainTab>(MainTab.Home) }

<<<<<<< HEAD
    // Sync state if a specific tab was requested via route params
=======
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
    LaunchedEffect(initialTabRoute) {
        if (initialTabRoute != null) {
            val matchingTab = tabs.find { it.route == initialTabRoute }
            if (matchingTab != null) {
                currentTab = matchingTab
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = if (isSystemInDarkTheme()) Neutral800 else Neutral050,
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(64.dp),
                tonalElevation = 4.dp
            ) {
                tabs.forEach { tab ->
                    val isSelected = currentTab == tab
                    val icon = getTabIcon(tab)
<<<<<<< HEAD
                    
=======

>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontFamily = PlusJakartaSans,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 10.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Gold400,
                            indicatorColor = Gold400,
                            unselectedIconColor = MutedText,
                            unselectedTextColor = MutedText
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentTab) {
                MainTab.Home -> HomeScreen(navController = navController)
                MainTab.Saving -> SavingListScreen(navController = navController)
                MainTab.Price -> PriceScreen()
<<<<<<< HEAD
                MainTab.About -> AboutScreen(navController = navController)
=======
                // Menyuntikkan ViewModel ke halaman About asli bawaan kelompokmu
                MainTab.About -> AboutScreen(navController = navController, authViewModel = authViewModel)
>>>>>>> 07b0f42 (Inisialisasi git dengan .gitignore yang bersih)
            }
        }
    }
}

private fun getTabIcon(tab: MainTab): ImageVector {
    return when (tab) {
        MainTab.Home -> Icons.Rounded.Home
        MainTab.Saving -> Icons.Rounded.Savings
        MainTab.Price -> Icons.Rounded.ShowChart
        MainTab.About -> Icons.Rounded.Info
    }
}
