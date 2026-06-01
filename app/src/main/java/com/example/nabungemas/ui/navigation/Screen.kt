package com.example.nabungemas.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    
    object SavingDetail : Screen("saving_detail/{savingId}") {
        fun createRoute(savingId: String) = "saving_detail/$savingId"
    }
    
    object AddEditSaving : Screen("add_edit_saving?savingId={savingId}") {
        fun createRoute(savingId: String? = null): String {
            return if (savingId != null) "add_edit_saving?savingId=$savingId" else "add_edit_saving"
        }
    }
    
    object AddEditTransaction : Screen("add_edit_transaction?transactionId={transactionId}&savingId={savingId}") {
        fun createRoute(transactionId: String? = null, savingId: String? = null): String {
            val builder = StringBuilder("add_edit_transaction")
            val params = mutableListOf<String>()
            if (transactionId != null) params.add("transactionId=$transactionId")
            if (savingId != null) params.add("savingId=$savingId")
            if (params.isNotEmpty()) {
                builder.append("?").append(params.joinToString("&"))
            }
            return builder.toString()
        }
    }
    
    object TransactionDetail : Screen("transaction_detail/{transactionId}") {
        fun createRoute(transactionId: String) = "transaction_detail/$transactionId"
    }

    object TransactionHistory : Screen("transaction_history")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object ChangePassword : Screen("change_password")
}

sealed class MainTab(val route: String, val title: String, val icon: String, val filledIcon: String) {
    object Home : MainTab("home", "Home", "home", "home")
    object Saving : MainTab("saving", "Tabungan", "savings", "savings")
    object Price : MainTab("price", "Harga Emas", "show_chart", "show_chart")
    object About : MainTab("about", "Info", "info", "info")
}
