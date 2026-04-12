package com.rddefelice.debtoptimizer.ui.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rddefelice.debtoptimizer.domain.usecase.DebtPayoffPlan

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Payoff Calculator",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Method Toggle
        Text(text = "Strategy", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.method == CalculatorMethod.SNOWBALL,
                onClick = { viewModel.setMethod(CalculatorMethod.SNOWBALL) }
            )
            Text("Snowball (Lowest Balance First)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.method == CalculatorMethod.AVALANCHE,
                onClick = { viewModel.setMethod(CalculatorMethod.AVALANCHE) }
            )
            Text("Avalanche (Highest APR First)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Extra Payment Input
        OutlinedTextField(
            value = if (state.extraPayment == 0.0) "" else state.extraPayment.toString(),
            onValueChange = { 
                val amount = it.toDoubleOrNull() ?: 0.0
                viewModel.setExtraPayment(amount)
            },
            label = { Text("Extra Monthly Payment ($)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.calculate() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Payoff Plan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Results
        if (state.payoffPlan.isNotEmpty()) {
            Text(text = "Payoff Order", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(state.payoffPlan) { plan ->
                    PayoffPlanItem(plan = plan)
                }
            }
        } else if (!state.isLoading) {
            Text(
                text = "Enter extra payment and click calculate to see your plan.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PayoffPlanItem(plan: DebtPayoffPlan) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = plan.debtName, style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Months: ${plan.monthsToPayoff}", modifier = Modifier.weight(1f))
                Text(text = "Interest: $${"%.2f".format(plan.totalInterest)}")
            }
        }
    }
}