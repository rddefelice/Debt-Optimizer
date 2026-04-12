package com.rddefelice.debtoptimizer.domain.model

data class Debt(
    val id: Long = 0,
    val name: String,
    val balance: Double,
    val apr: Double,          // Annual Percentage Rate (e.g., 0.18 for 18%)
    val minimumPayment: Double
)