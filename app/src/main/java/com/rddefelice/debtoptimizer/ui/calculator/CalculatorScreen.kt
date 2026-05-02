package com.rddefelice.debtoptimizer.ui.calculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rddefelice.debtoptimizer.domain.usecase.DebtPayoffPlan
import com.rddefelice.debtoptimizer.domain.usecase.PayoffResult

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()
    var selectedMethodForDetails by remember { mutableStateOf(CalculatorMethod.AVALANCHE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Strategy Comparison",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.calculate() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))
        ) {
            Text("Compare Strategies")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.snowballResult != null && state.avalancheResult != null) {
            val snowball = state.snowballResult!!
            val avalanche = state.avalancheResult!!
            
            val isAvalancheBetter = avalanche.totalInterest < snowball.totalInterest
            
            Text(text = "Choose a strategy to see details:", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ComparisonCard(
                    modifier = Modifier.weight(1f),
                    title = "Snowball",
                    result = snowball,
                    isWinner = !isAvalancheBetter,
                    isSelected = selectedMethodForDetails == CalculatorMethod.SNOWBALL,
                    onClick = { selectedMethodForDetails = CalculatorMethod.SNOWBALL }
                )
                ComparisonCard(
                    modifier = Modifier.weight(1f),
                    title = "Avalanche",
                    result = avalanche,
                    isWinner = isAvalancheBetter,
                    isSelected = selectedMethodForDetails == CalculatorMethod.AVALANCHE,
                    onClick = { selectedMethodForDetails = CalculatorMethod.AVALANCHE }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Payoff Order (${if (selectedMethodForDetails == CalculatorMethod.SNOWBALL) "Snowball" else "Avalanche"})",
                style = MaterialTheme.typography.titleMedium
            )
            
            val details = if (selectedMethodForDetails == CalculatorMethod.SNOWBALL) snowball.plans else avalanche.plans
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(details) { plan ->
                    PayoffPlanItem(plan = plan)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Enter extra payment and click compare\nto see which strategy saves you the most.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ComparisonCard(
    modifier: Modifier,
    title: String,
    result: PayoffResult,
    isWinner: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isWinner) Color(0xFFF0FFF4) else Color.White
    
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (isSelected) BorderStroke(2.dp, Color(0xFF6750A4)) else BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isWinner) {
                Surface(
                    color = Color(0xFF388E3C),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "SAVINGS WINNER",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(18.dp))
            }

            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(text = "${result.monthsToDebtFree} months", fontSize = 12.sp)
            Text(
                text = "$${"%.0f".format(result.totalInterest)} interest", 
                fontSize = 13.sp,
                color = if (isWinner) Color(0xFF388E3C) else Color.Black,
                fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
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
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = plan.debtName, fontWeight = FontWeight.Medium)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Paid off: Month ${plan.monthsToPayoff}", modifier = Modifier.weight(1f), fontSize = 13.sp)
                Text(text = "Interest: $${"%.2f".format(plan.totalInterest)}", color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}
