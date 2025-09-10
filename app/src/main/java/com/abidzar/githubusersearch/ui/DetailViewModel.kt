package com.abidzar.githubusersearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abidzar.githubusersearch.domain.model.UserDetail
import com.abidzar.githubusersearch.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserDetail: GetUserDetailUseCase
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _user = MutableLiveData<UserDetail?>(null)
    val user: LiveData<UserDetail?> = _user

    fun load(username: String) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                _error.postValue(null)
                _user.postValue(getUserDetail(username))
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
