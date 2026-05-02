package com.rddefelice.debtoptimizer.domain.usecase

import com.rddefelice.debtoptimizer.domain.model.Debt
import javax.inject.Inject

class CalculateSnowball @Inject constructor() {
    fun execute(debts: List<Debt>, extraPayment: Double = 0.0): PayoffResult {
        // Snowball: Sort by balance ascending (lowest balance first)
        val sorted = debts.sortedBy { it.balance }
        return runPayoffSimulation(sorted, extraPayment)
    }
}