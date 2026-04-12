package com.rddefelice.debtoptimizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rddefelice.debtoptimizer.domain.model.Debt

@Entity(tableName = "debts")
data class DebtEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val balance: Double,
    val apr: Double,
    val minimumPayment: Double
)

fun DebtEntity.toDomain(): Debt {
    return Debt(
        id = id,
        name = name,
        balance = balance,
        apr = apr,
        minimumPayment = minimumPayment
    )
}

fun Debt.toEntity(): DebtEntity {
    return DebtEntity(
        id = id,
        name = name,
        balance = balance,
        apr = apr,
        minimumPayment = minimumPayment
    )
}