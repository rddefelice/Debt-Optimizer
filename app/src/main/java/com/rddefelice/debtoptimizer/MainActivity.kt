package com.rddefelice.debtoptimizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rddefelice.debtoptimizer.ui.navigation.NavGraph
import com.rddefelice.debtoptimizer.ui.navigation.Screen
import com.rddefelice.debtoptimizer.ui.theme.DebtOptimizerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DebtOptimizerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Show bottom nav on main screens AND the calculator (as it's a tool for accounts)
                val showBottomNav = when (currentDestination?.route) {
                    Screen.Accounts.route, Screen.Offers.route, Screen.Settings.route, Screen.Calculator.route -> true
                    else -> false
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomNav) {
                            BottomNavigationBar(
                                currentDestination = currentDestination,
                                onNavigate = { screen ->
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentDestination: androidx.navigation.NavDestination?,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar {
        val items = listOf(
            BottomNavItem("Accounts", Screen.Accounts, Icons.Default.Home),
            BottomNavItem("Offers", Screen.Offers, Icons.Default.Star),
            BottomNavItem("Settings", Screen.Settings, Icons.Default.Settings)
        )

        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true ||
                             (item.screen == Screen.Accounts && currentDestination?.route == Screen.Calculator.route)
            
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = { onNavigate(item.screen) }
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val screen: Screen,
    val icon: ImageVector
)