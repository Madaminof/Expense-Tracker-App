package com.example.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.Repository.ExpenseRepository
import com.example.expensetracker.data.local.Entity.ExpenseEntity
import com.example.expensetracker.presentation.ui.EnumTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses

    private val _incomeTotal = MutableStateFlow(0.0)
    val incomeTotal: StateFlow<Double> = _incomeTotal

    private val _expenseTotal = MutableStateFlow(0.0)
    val expenseTotal: StateFlow<Double> = _expenseTotal

    init {
        // 🔹 App ishga tushganda ma'lumotlarni olish
        getAllExpenses()
        calculateTotals()
    }

    fun getAllExpenses() {
        viewModelScope.launch {
            repository.getAllExpenses().collectLatest { list ->
                _expenses.value = list
            }
        }
    }

    private fun calculateTotals() {
        // 🔹 Income hisoblash
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTotalByType(EnumTypes.income.toString()).collectLatest { total ->
                _incomeTotal.value = total ?: 0.0
            }
        }

        // 🔹 Expense hisoblash
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTotalByType(EnumTypes.expense.toString()).collectLatest { total ->
                _expenseTotal.value = total ?: 0.0
            }
        }
    }

    fun expenseAdd(expense: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertExpense(expense)
            calculateTotals() // 🔹 Qo‘shilgandan so‘ng yangilash
        }
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExpense(expense)
            calculateTotals() // 🔹 O‘chirgandan so‘ng yangilash
        }
    }

    fun updateExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.updateExpense(expense)
    }
}
