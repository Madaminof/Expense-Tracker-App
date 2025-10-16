package com.example.expensetracker.presentation


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.util.*

fun formatSmartDate(timestamp: Long): String {
    val now = Calendar.getInstance()
    val date = Calendar.getInstance().apply { timeInMillis = timestamp }

    val sameDay = now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)

    val pattern = if (sameDay) "HH:mm" else "dd MMM, HH:mm"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())

    return sdf.format(Date(timestamp))
}

@Composable
fun AppStatusBar(
    statusBarColor: Color = MaterialTheme.colorScheme.primary,
    navBarColor: Color = MaterialTheme.colorScheme.background,
    darkIcons: Boolean = false
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = navBarColor,
            darkIcons = !darkIcons // nav bar iconlari status bar ga qarab
        )
    }
}
