package com.example.expensetracker.data.local.Entity

import com.example.expancetracker.R
import com.example.expensetracker.presentation.Screen

data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)

val items = listOf(
    BottomNavItem("Home", R.drawable.icon0, Screen.Home.route),
    BottomNavItem("Graphics", R.drawable.icon3, Screen.DiogramScreen.route),
    BottomNavItem("News", R.drawable.icon1, Screen.FinanceNews.route),
    BottomNavItem("Profile", R.drawable.icon4, Screen.Profile.route)
)

