package com.abidzar.githubusersearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.domain.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUsers: SearchUsersUseCase
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _results = MutableLiveData<List<UserSummary>>(emptyList())
    val results: LiveData<List<UserSummary>> = _results

    private var debounceJob: Job? = null

    fun onQueryChanged(query: String, page: Int = 1) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(400)
            if (query.isBlank()) {
                _results.postValue(emptyList())
                _error.postValue(null)
                return@launch
            }
            load(query, page)
        }
    }

    fun refresh(query: String, page: Int = 1) {
        viewModelScope.launch { load(query, page) }
    }

    private suspend fun load(query: String, page: Int) {
        try {
            _loading.postValue(true)
            _error.postValue(null)
            val data = searchUsers(query, page)
            _results.postValue(data)
        } catch (e: Exception) {
            _error.postValue(e.message ?: "Unknown error")
        } finally {
            _loading.postValue(false)
        }
    }
}
