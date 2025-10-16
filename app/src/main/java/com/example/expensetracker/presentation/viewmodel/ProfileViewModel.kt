package com.example.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.Repository.ProfileRepository
import com.example.expensetracker.data.datastore.DataStoreManager
import com.example.expensetracker.data.local.Entity.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val datasStore:DataStoreManager
) : ViewModel() {
    val profile: StateFlow<ProfileEntity?> = repository.getProfileFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    val isDarkMode = datasStore.darkModeFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )
    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            datasStore.saveDarkMode(enabled)
        }
    }

    init {
        viewModelScope.launch {
            val existingProfile = repository.getProfile()
            if (existingProfile == null) {
                val defaultProfile = ProfileEntity(
                    fullName = "Foydalanuvchi",
                    username = "@username"
                )
                repository.insertProfile(defaultProfile)
            }
        }
    }

    fun updateProfile(updated: ProfileEntity) {
        viewModelScope.launch {
            repository.updateProfile(updated)
        }
    }
}
