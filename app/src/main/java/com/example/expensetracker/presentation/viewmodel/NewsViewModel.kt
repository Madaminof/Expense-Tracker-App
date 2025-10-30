package com.example.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.expensetracker.data.Repository.NewsRepository
import com.example.expensetracker.data.Repository.Resource
import com.example.expensetracker.data.remote.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val state: StateFlow<Resource<List<Article>>> = _state

    fun loadNews() {
        viewModelScope.launch {
            _state.value = Resource.Loading
            _state.value = repository.getFinanceNews()
        }
    }
}