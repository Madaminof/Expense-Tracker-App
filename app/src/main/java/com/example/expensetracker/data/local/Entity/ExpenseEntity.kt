package com.example.expensetracker.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val type: String, // "income" yoki "expense"
    val date: Long = System.currentTimeMillis()
)