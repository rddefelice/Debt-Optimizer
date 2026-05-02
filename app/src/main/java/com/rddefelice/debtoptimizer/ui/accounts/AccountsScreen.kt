package com.rddefelice.debtoptimizer.ui.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rddefelice.debtoptimizer.domain.model.Debt
import com.rddefelice.debtoptimizer.ui.dashboard.DashboardViewModel
import com.rddefelice.debtoptimizer.ui.navigation.Screen

@Composable
fun AccountsScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var selectedDebt by remember { mutableStateOf<Debt?>(null) }

    if (selectedDebt != null) {
        DebtDetailDialog(
            debt = selectedDebt!!,
            onDismiss = { selectedDebt = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {

        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

        // Account List
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.debts) { debt ->
                AccountItem(
                    debt = debt,
                    onClick = { selectedDebt = debt }
                )
                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Add Account Button
            Button(
                onClick = { navController.navigate(Screen.AddAccount.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
            ) {
                Text(
                    text = "Add\nAccount",
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            // Calculation Button
            val hasEnoughDebts = state.debts.size >= 2
            Button(
                onClick = { if (hasEnoughDebts) navController.navigate(Screen.Calculator.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasEnoughDebts) Color(0xFF6750A4) else Color.LightGray
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp),
                enabled = true // Keeping enabled to show the message on click if needed, or just visual feedback
            ) {
                Text(
                    text = if (hasEnoughDebts) "Calculate\nDebt Repayment" 
                           else "Add at least 2 accounts to see calculation options",
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    fontSize = if (hasEnoughDebts) 14.sp else 10.sp
                )
            }
        }
    }
}

@Composable
fun AccountItem(debt: Debt, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFE6E1FF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = debt.name.take(1).uppercase(),
                color = Color(0xFF6750A4),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = debt.name, fontSize = 16.sp)
            Text(text = "Balance: $${"%.2f".format(debt.balance)}", color = Color.Gray, fontSize = 14.sp)
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}

@Composable
fun DebtDetailDialog(debt: Debt, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = debt.name) },
        text = {
            Column {
                Text("Type: ${debt.type}")
                Text("Balance: $${"%.2f".format(debt.balance)}")
                Text("Interest Rate: ${"%.2f".format(debt.apr * 100)}%")
                Text("Min Payment: $${"%.2f".format(debt.minimumPayment)}")
                debt.remainingTermMonths?.let {
                    Text("Remaining Term: $it months")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}