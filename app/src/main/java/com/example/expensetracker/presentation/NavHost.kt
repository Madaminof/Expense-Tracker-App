package com.example.expensetracker.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracker.presentation.ui.AddExpenseScreen
import com.example.expensetracker.presentation.ui.HomeScreen
import com.example.expensetracker.presentation.ui.NotificationScreen
import com.example.expensetracker.presentation.ui.ProfileScreen
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel


sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddEdit : Screen("add_edit_screen")
    object Profile : Screen("profile_screen")
    object Notification : Screen("notification_screen")


}

@Composable
fun NavHost(navController: NavHostController,viewModel: ExpenseViewModel){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(navController, viewModel)
        }
        composable(Screen.AddEdit.route){
            AddExpenseScreen(navController) // ✅ to‘g‘ri
        }
        composable(Screen.Profile.route){
            ProfileScreen(navController)
        }
        composable(Screen.Notification.route){
            NotificationScreen(navController)
        }

    }
}