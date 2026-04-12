package com.rddefelice.debtoptimizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rddefelice.debtoptimizer.ui.auth.LoginScreen
import com.rddefelice.debtoptimizer.ui.calculator.CalculatorScreen
import com.rddefelice.debtoptimizer.ui.dashboard.DashboardScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
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
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.Calculator.route) {
            CalculatorScreen(navController = navController)
        }
    }
}