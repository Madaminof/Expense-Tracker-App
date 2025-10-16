package com.example.expensetracker.data.Repository

import com.example.expensetracker.data.local.Entity.ExpenseEntity
import com.example.expensetracker.data.local.dao.ExpenseDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val dao: ExpenseDao
){
    fun getAllExpenses() = dao.getAllExpenses()

    fun getTotalByType(type:String) = dao.getTotalByType(type)

    suspend fun insertExpense(expense: ExpenseEntity) = dao.insert(expense)

    suspend fun deleteExpense(expense: ExpenseEntity) = dao.delete(expense)

    suspend fun clearAll() = dao.clearAll()

    suspend fun updateExpense(expense: ExpenseEntity) = dao.update(expense)

}