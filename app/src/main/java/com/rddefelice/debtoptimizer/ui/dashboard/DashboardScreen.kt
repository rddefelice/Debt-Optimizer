package com.rddefelice.debtoptimizer.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rddefelice.debtoptimizer.domain.model.Debt

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Debt Overview",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        state.summary?.let { summary ->
            Text(
                text = "Total Balance: $${"%.2f".format(summary.totalBalance)}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Monthly Minimum: $${"%.2f".format(summary.totalMinimumPayments)}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            text = "Your Debts",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        LazyColumn {
            items(state.debts) { debt ->
                DebtItem(debt = debt)
            }
        }
    }
}

@Composable
fun DebtItem(debt: Debt) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = debt.name, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Balance: $${debt.balance}", style = MaterialTheme.typography.bodySmall)
    }
}