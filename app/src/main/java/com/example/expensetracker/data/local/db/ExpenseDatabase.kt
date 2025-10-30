package com.example.expensetracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.local.Entity.Converters
import com.example.expensetracker.data.local.Entity.ExpenseEntity
import com.example.expensetracker.data.local.Entity.ProfileEntity
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.ProfileDao

@Database(entities = [ExpenseEntity::class, ProfileEntity::class], version = 7)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase:RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao
    abstract fun profileDao(): ProfileDao

}