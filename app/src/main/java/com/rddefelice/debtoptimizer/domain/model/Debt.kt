package com.rddefelice.debtoptimizer.domain.model

enum class DebtType {
    CREDIT_CARD, LOAN, OTHER
}

data class Debt(
    val id: Long = 0,
    val name: String,
    val balance: Double,
    val apr: Double,          // Annual Percentage Rate (e.g., 0.18 for 18%)
    val minimumPayment: Double,
    val remainingTermMonths: Int? = null,
    val type: DebtType = DebtType.OTHER
)