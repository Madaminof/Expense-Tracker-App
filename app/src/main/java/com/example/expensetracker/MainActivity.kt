package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.presentation.AppStatusBar
import com.example.expensetracker.presentation.NavHost
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel
import com.example.expensetracker.presentation.viewmodel.ProfileViewModel
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val isDarkMode by profileViewModel.isDarkMode.collectAsState()
            val navController = rememberNavController()
            val viewModel: ExpenseViewModel = viewModel()
            ExpenseTrackerTheme (
                darkTheme = isDarkMode
            ){
                AppStatusBar()
                NavHost(navController,viewModel)
            }
        }
    }
}
