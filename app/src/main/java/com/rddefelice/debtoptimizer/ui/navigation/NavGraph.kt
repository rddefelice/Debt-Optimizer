package com.rddefelice.debtoptimizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rddefelice.debtoptimizer.ui.auth.LoginScreen
import com.rddefelice.debtoptimizer.ui.calculator.CalculatorScreen
import com.rddefelice.debtoptimizer.ui.dashboard.DashboardScreen
import com.rddefelice.debtoptimizer.ui.accounts.AccountsScreen
import com.rddefelice.debtoptimizer.ui.accounts.AddAccountScreen
import com.rddefelice.debtoptimizer.ui.offers.OffersScreen
import com.rddefelice.debtoptimizer.ui.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Accounts : Screen("accounts")
    object AddAccount : Screen("add_account")
    object Offers : Screen("offers")
    object Settings : Screen("settings")
    object Calculator : Screen("calculator")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Accounts.route) {
            AccountsScreen(navController = navController)
        }
        composable(Screen.AddAccount.route) {
            AddAccountScreen(navController = navController)
        }
        composable(Screen.Offers.route) {
            OffersScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(Screen.Calculator.route) {
            CalculatorScreen(navController = navController)
        }
    }
}