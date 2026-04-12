package com.rddefelice.debtoptimizer.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.rddefelice.debtoptimizer.data.local.AppDatabase
import com.rddefelice.debtoptimizer.data.local.DebtDao
import com.rddefelice.debtoptimizer.data.repository.DebtRepositoryImpl
import com.rddefelice.debtoptimizer.domain.repository.DebtRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "debt_db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    fun provideDebtDao(db: AppDatabase): DebtDao = db.debtDao()

    @Provides
    @Singleton
    fun provideDebtRepository(dao: DebtDao): DebtRepository = DebtRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}