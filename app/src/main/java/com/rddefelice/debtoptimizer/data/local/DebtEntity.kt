package com.rddefelice.debtoptimizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rddefelice.debtoptimizer.domain.model.Debt
import com.rddefelice.debtoptimizer.domain.model.DebtType

@Entity(tableName = "debts")
data class DebtEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val balance: Double,
    val apr: Double,
    val minimumPayment: Double,
    val remainingTermMonths: Int? = null,
    val type: DebtType = DebtType.OTHER
)

fun DebtEntity.toDomain(): Debt {
    return Debt(
        id = id,
        name = name,
        balance = balance,
        apr = apr,
        minimumPayment = minimumPayment,
        remainingTermMonths = remainingTermMonths,
        type = type
    )
}

fun Debt.toEntity(): DebtEntity {
    return DebtEntity(
        id = id,
        name = name,
        balance = balance,
        apr = apr,
        minimumPayment = minimumPayment,
        remainingTermMonths = remainingTermMonths,
        type = type
    )
}