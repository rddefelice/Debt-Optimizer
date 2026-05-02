package com.rddefelice.debtoptimizer.domain.usecase

import com.rddefelice.debtoptimizer.domain.model.Debt
import javax.inject.Inject

class CalculateAvalanche @Inject constructor() {
    fun execute(debts: List<Debt>, extraPayment: Double = 0.0): PayoffResult {
        // Sort by APR descending (highest interest first)
        val sorted = debts.sortedByDescending { it.apr }
        return runPayoffSimulation(sorted, extraPayment)
    }
}