package com.example.expensetracker.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expensetracker.presentation.formatSmartDate
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel


@Composable
fun ExpenseListScreen(
    viewModel: ExpenseViewModel,
    typeFilter: String? = null
) {
    val expenses by viewModel.expenses.collectAsState(initial = emptyList())

    val filteredExpenses = if (typeFilter != null)
        expenses.filter { it.type.equals(typeFilter, ignoreCase = true) }
    else expenses

    if (filteredExpenses.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Hozircha ma'lumot yo‘q")
        }
    } else {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // ⚠️ fillMaxWidth emas
                .padding(vertical = 2.dp)
        )
        {
            items(filteredExpenses) { expense ->
                var expanded by remember { mutableStateOf(false) }
                var showEditDialog by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .clickable { expanded = false }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Chap tomonda: nom va tur
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = expense.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = expense.type.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        // O‘ng tomonda: miqdor va sana
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${"%,.1f".format(expense.amount)} so'm",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (expense.type.equals(EnumTypes.income.name, true))
                                    Color(0xFF2E7D32) // yashil
                                else
                                    Color(0xFFC62828) // qizil
                            )
                            Text(
                                text = formatSmartDate(expense.date),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        // Menyu (uch nuqta)
                        Box {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More options",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Tahrirlash") },
                                    onClick = {
                                        expanded = false
                                        showEditDialog = true
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("O‘chirish") },
                                    onClick = {
                                        expanded = false
                                        viewModel.deleteExpense(expense)
                                    }
                                )
                            }
                        }
                    }
                }

                // 🔹 Har bir item orasida Divider (chiziq)
                Divider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )

                if (showEditDialog) {
                    EditExpenseDialog(
                        expense = expense,
                        onDismiss = { showEditDialog = false },
                        onSave = { updatedExpense ->
                            viewModel.updateExpense(updatedExpense)
                            showEditDialog = false
                        }
                    )
                }
            }

        }
    }
}


