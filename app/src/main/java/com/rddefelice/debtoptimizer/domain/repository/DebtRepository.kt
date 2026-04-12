package com.rddefelice.debtoptimizer.domain.repository

import com.rddefelice.debtoptimizer.domain.model.Debt
import kotlinx.coroutines.flow.Flow

interface DebtRepository {
    suspend fun getAllDebts(): List<Debt>
    suspend fun insertDebt(debt: Debt)
    suspend fun deleteDebt(debt: Debt)
    // Add update, etc.
    fun observeDebts(): Flow<List<Debt>>
}