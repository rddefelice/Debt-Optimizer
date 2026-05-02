package com.rddefelice.debtoptimizer.data.local

import androidx.room.TypeConverter
import com.rddefelice.debtoptimizer.domain.model.DebtType

class Converters {
    @TypeConverter
    fun fromDebtType(value: DebtType): String {
        return value.name
    }

    @TypeConverter
    fun toDebtType(value: String): DebtType {
        return DebtType.valueOf(value)
    }
}