package com.example.expensetracker.data.local.Entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expancetracker.R


@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val category: Category,
    val title: String,
    val amount: Double,
    val type: String, // "income" yoki "expense"
    val date: Long = System.currentTimeMillis()
)

data class Category(
    val name: String,
    @DrawableRes val iconRes: Int
)

val incomeCategories = listOf(
    Category("Salary", R.drawable.salary_icon),
    Category("Business", R.drawable.ic_business),
    Category("Bonus", R.drawable.ic_gift),
    Category("Invest", R.drawable.investment_icon),
    Category("Freelance", R.drawable.freelance_icon),
    Category("Other", R.drawable.other_icon),

    )

val expenseCategories = listOf(
    Category("Food", R.drawable.ic_food),
    Category("Transport", R.drawable.transport_icon),
    Category("Health", R.drawable.medical_icon),
    Category("Shopping", R.drawable.shopping_icon),
    Category("Education", R.drawable.education_icon),
    Category("Entertain", R.drawable.entertainment_icon),
    Category("Travel", R.drawable.travel_icon),
    Category("Other", R.drawable.other_icon),


    )
