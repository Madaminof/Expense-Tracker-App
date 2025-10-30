package com.example.expensetracker.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.expensetracker.presentation.ui.AboutScreen
import com.example.expensetracker.presentation.ui.AddExpenseScreen
import com.example.expensetracker.presentation.ui.DiogramScreen
import com.example.expensetracker.presentation.ui.FinanceNewsScreen
import com.example.expensetracker.presentation.ui.HomeScreen
import com.example.expensetracker.presentation.ui.NotificationScreen
import com.example.expensetracker.presentation.ui.ProfileScreen
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel


sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddEdit : Screen("add_edit_screen")
    object Profile : Screen("profile_screen")
    object Notification : Screen("notification_screen")
    object FinanceNews : Screen("finance_news")
    object DiogramScreen : Screen("diogram_screen")
    object About : Screen("about_screen")

}

@Composable
fun NavHost(navController: NavHostController,viewModel: ExpenseViewModel){

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(navController, viewModel)
        }
        composable(Screen.AddEdit.route){
            AddExpenseScreen(navController)
        }
        composable(Screen.Profile.route){
            ProfileScreen(navController)
        }
        composable(Screen.Notification.route){
            NotificationScreen(navController)
        }

        composable(Screen.FinanceNews.route){
            FinanceNewsScreen(navController = navController)
        }
        composable(Screen.DiogramScreen.route){
            DiogramScreen(navController)
        }
        composable(Screen.About.route){
            AboutScreen(navController)
        }

    }
}