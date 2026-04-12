package com.rddefelice.debtoptimizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DebtEntity::class], version = 1)
abstract class DebtDatabase : RoomDatabase() {
    abstract fun debtDao(): DebtDao
}