package com.rddefelice.debtoptimizer.domain.usecase

import com.rddefelice.debtoptimizer.domain.repository.DebtRepository
import javax.inject.Inject

class GetDebtSummary @Inject constructor(private val repo: DebtRepository) {
    suspend operator fun invoke(): DebtSummary {
        val debts = repo.getAllDebts()
        val totalBalance = debts.sumOf { it.balance }
        return DebtSummary(
            totalBalance = totalBalance,
            totalMinimumPayments = debts.sumOf { it.minimumPayment },
            weightedAvgApr = if (debts.isEmpty() || totalBalance == 0.0) 0.0 else debts.sumOf { it.balance * it.apr } / totalBalance
        )
    }
}

data class DebtSummary(
    val totalBalance: Double,
    val totalMinimumPayments: Double,
    val weightedAvgApr: Double
)