package com.rddefelice.debtoptimizer.data.repository

import com.rddefelice.debtoptimizer.data.local.DebtDao
import com.rddefelice.debtoptimizer.data.local.toDomain
import com.rddefelice.debtoptimizer.data.local.toEntity
import com.rddefelice.debtoptimizer.domain.model.Debt
import com.rddefelice.debtoptimizer.domain.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DebtRepositoryImpl @Inject constructor(
    private val dao: DebtDao
) : DebtRepository {

    override suspend fun getAllDebts(): List<Debt> {
        return dao.getAll().first().map { it.toDomain() }
    }

    override suspend fun insertDebt(debt: Debt) {
        dao.insert(debt.toEntity())
    }

    override suspend fun deleteDebt(debt: Debt) {
        dao.delete(debt.toEntity())
    }

    override fun observeDebts(): Flow<List<Debt>> {
        return dao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }
}