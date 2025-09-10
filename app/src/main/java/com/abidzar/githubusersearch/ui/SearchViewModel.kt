package com.abidzar.githubusersearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.domain.usecase.SearchUsersUseCase
import com.abidzar.githubusersearch.data.remote.GitHubApi
import com.abidzar.githubusersearch.data.local.AppDatabase
import com.abidzar.githubusersearch.data.paging.UserSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUsers: SearchUsersUseCase,
    private val api: GitHubApi,
    private val db: AppDatabase
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _results = MutableLiveData<PagingData<UserSummary>>()
    val results: LiveData<PagingData<UserSummary>> = _results

    private var debounceJob: Job? = null

    fun onQueryChanged(query: String, page: Int = 1) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(400)
            if (query.isBlank()) {
                _results.postValue(PagingData.empty())
                _error.postValue(null)
                return@launch
            }
            load(query)
        }
    }

    fun refresh(query: String) {
        viewModelScope.launch { load(query) }
    }

    private suspend fun load(query: String) {
        try {
            _loading.postValue(true)
            _error.postValue(null)
            val pager = Pager(
                config = PagingConfig(pageSize = 30, prefetchDistance = 10, enablePlaceholders = false),
                pagingSourceFactory = { UserSearchPagingSource(api, db, query) }
            )
            pager.flow.cachedIn(viewModelScope).collect {
                _results.postValue(it)
                _loading.postValue(false)
            }
        } catch (e: Exception) {
            _error.postValue(e.message ?: "Unknown error")
        } finally {
            _loading.postValue(false)
        }
    }
}
