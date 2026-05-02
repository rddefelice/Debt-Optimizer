package com.rddefelice.debtoptimizer.domain.usecase

import com.rddefelice.debtoptimizer.domain.model.Debt

data class DebtPayoffPlan(
    val debtName: String,
    val monthsToPayoff: Int,
    val totalInterest: Double
)

data class PayoffResult(
    val plans: List<DebtPayoffPlan>,
    val totalInterest: Double,
    val monthsToDebtFree: Int
)

/**
 * Simulates a debt payoff strategy.
 * The order of [sortedDebts] determines the priority for extra payments.
 */
fun runPayoffSimulation(sortedDebts: List<Debt>, extraMonthlyPayment: Double): PayoffResult {
    if (sortedDebts.isEmpty()) return PayoffResult(emptyList(), 0.0, 0)

    val activeDebts = sortedDebts.mapIndexed { index, debt ->
        SimulationState(
            balance = debt.balance,
            apr = debt.apr,
            minPayment = debt.minimumPayment,
            uniqueKey = "${debt.name}_${debt.id}_$index",
            name = debt.name
        )
    }

    val totalMonthlyBudget = extraMonthlyPayment + sortedDebts.sumOf { it.minimumPayment }
    
    val interestPaidMap = mutableMapOf<String, Double>()
    val payoffMonthMap = mutableMapOf<String, Int>()
    var currentMonth = 0
    val maxMonths = 1200

    while (activeDebts.any { it.balance > 0.001 } && currentMonth < maxMonths) {
        currentMonth++
        // Start with the full budget every month
        var poolForThisMonth = totalMonthlyBudget

        // 1. Accrue Interest (Keep this as is)
        for (debt in activeDebts) {
            if (debt.balance > 0.001) {
                val monthlyInterest = debt.balance * (debt.apr / 12.0)
                debt.balance += monthlyInterest
                interestPaidMap[debt.uniqueKey] = (interestPaidMap[debt.uniqueKey] ?: 0.0) + monthlyInterest
            }
        }

        // 2. Pay Minimums - ONLY subtract what we actually spend
        for (debt in activeDebts) {
            if (debt.balance > 0.001) {
                val payment = minOf(debt.balance, debt.minPayment)
                debt.balance -= payment
                poolForThisMonth -= payment // Only subtracts the actual payment made

                if (debt.balance <= 0.001) {
                    debt.balance = 0.0
                    payoffMonthMap[debt.uniqueKey] = currentMonth
                }
            }
        }

        // 3. Apply ALL remaining pool to the HIGHEST priority debt (the first one in the sorted list)
        for (debt in activeDebts) {
            if (debt.balance > 0.001) {
                val extraApplied = minOf(debt.balance, poolForThisMonth)
                debt.balance -= extraApplied
                poolForThisMonth -= extraApplied

                if (debt.balance <= 0.001) {
                    debt.balance = 0.0
                    payoffMonthMap[debt.uniqueKey] = currentMonth
                }
                // IMPORTANT: If we still have money, it goes to the NEXT debt in the sorted list
                if (poolForThisMonth <= 0.001) break
            }
        }
    }


    val plans = activeDebts.map { debtState ->
        DebtPayoffPlan(
            debtName = debtState.name,
            monthsToPayoff = payoffMonthMap[debtState.uniqueKey] ?: currentMonth,
            totalInterest = interestPaidMap[debtState.uniqueKey] ?: 0.0
        )
    }

    return PayoffResult(
        plans = plans,
        totalInterest = plans.sumOf { it.totalInterest },
        monthsToDebtFree = plans.maxOfOrNull { it.monthsToPayoff } ?: 0
    )
}

private class SimulationState(
    var balance: Double,
    val apr: Double,
    val minPayment: Double,
    val uniqueKey: String,
    val name: String
)
