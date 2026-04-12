package com.rddefelice.debtoptimizer.domain.usecase

import com.rddefelice.debtoptimizer.domain.model.Debt
import javax.inject.Inject

class CalculateAvalanche @Inject constructor() {
    fun execute(debts: List<Debt>, extraPayment: Double = 0.0): List<DebtPayoffPlan> {
        // Sort by apr descending (highest interest first)
        val sorted = debts.sortedByDescending { it.apr }
        return calculatePayoff(sorted, extraPayment)
    }

    private fun calculatePayoff(sortedDebts: List<Debt>, extra: Double): List<DebtPayoffPlan> {
        // Implement logic: simulate monthly payments until all paid off
        // Return a list of steps with months, total interest, etc.
        return emptyList() // TODO: full impl
    }
}