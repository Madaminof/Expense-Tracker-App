package com.example.expensetracker.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expancetracker.R
import com.example.expensetracker.presentation.Screen
import com.example.expensetracker.presentation.ui.ScaffoldComponents.AppBottomBar
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.PieChartData.Slice

data class ExpenseItem(val name: String, val value: Float, val color: Color)

@Composable
fun DiogramScreen(
    navController: NavController,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val expenseEntities by viewModel.expenses.collectAsState(initial = emptyList())
    val expenseItems = expenseEntities
        .filter { it.type.lowercase() == "expense" }
        .groupBy { it.category.name }
        .map { (name, items) ->
            val total = items.sumOf { it.amount }.toFloat()
            val color = items.firstOrNull()?.category?.iconRes?.let { mapCategoryToColor(it) } ?: Color.Gray
            ExpenseItem(name, total, color)
        }
        .take(8) // Max 8 categories

    Scaffold(
        topBar = {
            LargeHeader(
                title = "Statistics",
                onClick = { navController.popBackStack() },
                navigationIcon = Icons.Filled.ArrowBackIosNew,
                actionIcon = Icons.Filled.Download,
                onActionClick = {}
            )
        },
        bottomBar = { AppBottomBar(navController = navController, currentRoute = currentRoute) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEdit.route) },
                shape = CircleShape,
                containerColor = colorScheme.primary,
                modifier = Modifier
                    .offset(y = 50.dp)
                    .size(64.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense", tint = colorScheme.onPrimary)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(colorScheme.background)
        ) {
            // Pie Chart Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_pattern_statistic2),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.25f
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.05f)
                                    )
                                )
                            )
                    )
                }

                // 🔹 Asosiy kontent (diagramma)
                ExpensePieChart(expenseItems)
            }


            // Bar Chart + Legend
            ExpenseStatistics(expenseItems)


        }
    }
}

@Composable
fun ExpensePieChart(expenses: List<ExpenseItem>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        contentAlignment = Alignment.Center
    ) {
        val slices = if (expenses.isNotEmpty()) {
            expenses.map { Slice(it.value, it.color) }
        } else {
            // Bo‘sh holatda 1 slice bilan 100%
            listOf(Slice(100f, colorScheme.background.copy(alpha = 0.2f)))
        }

        PieChart(
            pieChartData = PieChartData(slices = slices),
            modifier = Modifier
                .fillMaxSize()
                .size(180.dp) // doirani kattaroq qilish
                .padding(16.dp) // chiziqni biroz ichkariga tushirish
        )

        Text(
            text = if (expenses.isNotEmpty()) "Expense" else "No expenses",
            color = if (expenses.isNotEmpty()) Color.White else colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable
fun ExpenseStatistics(expenses: List<ExpenseItem>) {
    if (expenses.isEmpty()) return

    val maxBarHeight = 180.dp
    val sortedExpenses = expenses.sortedByDescending { it.value } // eng katta chap tomonda
    val maxValue = sortedExpenses.maxOf { it.value }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Legend: horizontal, 2 qator, har qator maksimal 4 item
        val chunkedExpenses = sortedExpenses.chunked(4)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            chunkedExpenses.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .background(item.color, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                item.name,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Bar Chart
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxBarHeight),
            horizontalArrangement = Arrangement.Start, // eng katta chap tomonda
            verticalAlignment = Alignment.Bottom
        ) {
            sortedExpenses.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    val normalizedHeight = (item.value / maxValue) * maxBarHeight.value
                    Text(
                        text = "${item.value.toInt()}",
                        fontSize = 12.sp,
                        color = item.color,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(normalizedHeight.dp)
                            .width(20.dp)
                            .background(item.color, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    )
                }
            }
        }
    }
}


fun mapCategoryToColor(iconRes: Int): Color = when (iconRes) {
    R.drawable.ic_food -> Color(0xFFFFA000)
    R.drawable.transport_icon -> Color(0xFFE64A19)
    R.drawable.medical_icon -> Color(0xFF1976D2)
    R.drawable.shopping_icon -> Color(0xFF9C27B0)
    R.drawable.education_icon -> Color(0xFFE91E63)
    R.drawable.entertainment_icon -> Color(0xFF571DD7)
    R.drawable.travel_icon -> Color(0xFF00BCD4)
    else -> Color.Gray
}
