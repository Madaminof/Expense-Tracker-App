package com.example.expensetracker.data.local.Entity

import androidx.room.TypeConverter
import com.example.expancetracker.R

class Converters {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return "${category.name}|${category.iconRes}"
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        val parts = value.split("|")
        return if (parts.size == 2) {
            Category(parts[0], parts[1].toIntOrNull() ?: R.drawable.other_icon)
        } else {
            Category("Other", R.drawable.other_icon)
        }
    }
}
