package com.example.expensetracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetracker.data.local.Entity.ExpenseEntity
import com.example.expensetracker.data.local.Entity.ProfileEntity
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.ProfileDao


@Database(entities = [ExpenseEntity::class, ProfileEntity::class], version = 3)
abstract class ExpenseDatabase:RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao
    abstract fun profileDao(): ProfileDao

}