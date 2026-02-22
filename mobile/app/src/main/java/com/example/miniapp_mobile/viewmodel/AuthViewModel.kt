package com.example.miniapp_mobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniapp_mobile.data.model.LoginResponse
import com.example.miniapp_mobile.data.model.User
import com.example.miniapp_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

sealed class AuthUiState<out T> {
    object Idle : AuthUiState<Nothing>()
    object Loading : AuthUiState<Nothing>()
    data class Success<T>(val data: T) : AuthUiState<T>()
    data class Error(val message: String) : AuthUiState<Nothing>()
}

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _loginState = MutableLiveData<AuthUiState<LoginResponse>>(AuthUiState.Idle)
    val loginState: LiveData<AuthUiState<LoginResponse>> = _loginState

    private val _registerState = MutableLiveData<AuthUiState<String>>(AuthUiState.Idle)
    val registerState: LiveData<AuthUiState<String>> = _registerState

    private val _profileState = MutableLiveData<AuthUiState<User>>(AuthUiState.Idle)
    val profileState: LiveData<AuthUiState<User>> = _profileState

    fun login(email: String, password: String) {
        _loginState.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = repository.login(email, password)
            _loginState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Login failed") }
            )
        }
    }

    fun register(firstName: String, lastName: String, email: String, password: String) {
        _registerState.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = repository.register(firstName, lastName, email, password)
            _registerState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Registration failed") }
            )
        }
    }

    fun loadProfile(token: String) {
        _profileState.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = repository.getProfile(token)
            _profileState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Failed to load profile") }
            )
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            repository.logout(token)
        }
    }

    fun resetLoginState() {
        _loginState.value = AuthUiState.Idle
    }

    fun resetRegisterState() {
        _registerState.value = AuthUiState.Idle
    }

    fun resetProfileState() {
        _profileState.value = AuthUiState.Idle
    }
}