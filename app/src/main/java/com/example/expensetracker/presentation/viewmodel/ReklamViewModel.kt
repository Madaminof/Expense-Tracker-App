package com.example.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.Repository.ReklamRepository
import com.example.expensetracker.data.remote.ReklamApi.Reklama
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReklamViewModel @Inject constructor(
    private val repository: ReklamRepository
) : ViewModel() {

    private val _reklamaList = MutableStateFlow<List<Reklama>>(emptyList())
    val reklamaList: StateFlow<List<Reklama>> = _reklamaList

    private val _reklam = MutableStateFlow<Reklama?>(null)
    val reklam = _reklam.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    // 🔹 API’dan reklamalarni olish
    fun loadReklamalar() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _reklamaList.value = repository.getReklamalar()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }



    fun loadReklam(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getReklamById(id)
                _reklam.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
