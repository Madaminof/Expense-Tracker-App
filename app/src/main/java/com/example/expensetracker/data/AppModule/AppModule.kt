package com.example.expensetracker.data.AppModule

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.Repository.ExpenseRepository
import com.example.expensetracker.data.datastore.DataStoreManager
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.ProfileDao
import com.example.expensetracker.data.local.db.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleDb {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ExpenseDatabase =
        Room.databaseBuilder(
            appContext,
            ExpenseDatabase::class.java,
            "expense_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideExpenseDao(db: ExpenseDatabase): ExpenseDao = db.expenseDao()

    @Provides
    @Singleton
    fun provideProfileDao(db: ExpenseDatabase): ProfileDao = db.profileDao()

    @Provides
    @Singleton
    fun provideExpenseRepository(dao: ExpenseDao): ExpenseRepository =
        ExpenseRepository(dao)

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)
}
