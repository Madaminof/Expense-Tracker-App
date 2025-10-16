package com.example.expensetracker.data.local.Entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val username: String,
    val imageUri: String? = null
)

data class ProfileSettings(
    val title:String,
    val icon: ImageVector,
    val hasSwitch: Boolean = false

)



sealed class Setting(val title: String) {
    object ProfileEdit : Setting("Profile edit")
    object MonthPlan : Setting("Oylik byudjet reja")
    object DarkMode : Setting("Dark Mode")
    object IlovaHaqida : Setting("Ilova haqida")


}

val settingList = listOf(
    ProfileSettings(Setting.ProfileEdit.title,Icons.Filled.AccountCircle),
    ProfileSettings(Setting.MonthPlan.title,Icons.Filled.CalendarMonth),
    ProfileSettings(Setting.DarkMode.title,Icons.Filled.DarkMode,hasSwitch = true),
    ProfileSettings(Setting.IlovaHaqida.title,Icons.Filled.Info),
    )
