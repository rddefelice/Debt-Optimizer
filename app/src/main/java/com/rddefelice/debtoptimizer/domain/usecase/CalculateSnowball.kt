package com.rddefelice.debtoptimizer.domain.usecase

import com.rddefelice.debtoptimizer.domain.model.Debt
import javax.inject.Inject

class CalculateSnowball @Inject constructor() {
    fun execute(debts: List<Debt>, extraPayment: Double = 0.0): List<DebtPayoffPlan> {
        // Sort by balance ascending
        val sorted = debts.sortedBy { it.balance }
        return calculatePayoff(sorted, extraPayment)
    }

    private fun calculatePayoff(sortedDebts: List<Debt>, extra: Double): List<DebtPayoffPlan> {
        // Implement logic: simulate monthly payments until all paid off
        // Return a list of steps with months, total interest, etc.
        // For simplicity, you can return a data class with results
        return emptyList() // TODO: full impl
    }
}

// Simple result model
data class DebtPayoffPlan(
    val debtName: String,
    val monthsToPayoff: Int,
    val totalInterest: Double
)