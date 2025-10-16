package com.example.expensetracker.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.datastore by preferencesDataStore(name = "user_setting")
object PreferenceKeys{
    val Dark_Mode = booleanPreferencesKey("dark_mode")
}

class DataStoreManager @Inject constructor(private val context: Context){
    val darkModeFlow: Flow<Boolean> = context.datastore.data.map { pref ->
        pref[PreferenceKeys.Dark_Mode]?:false
    }

    suspend fun saveDarkMode(isDark:Boolean){
        context.datastore.edit { pref ->
            pref[PreferenceKeys.Dark_Mode] = isDark
        }
    }
}