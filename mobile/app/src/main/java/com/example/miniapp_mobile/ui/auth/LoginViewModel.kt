package com.example.miniapp_mobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniapp_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> get() = _loginState

    private val _email = MutableLiveData<String>("")
    private val _password = MutableLiveData<String>("")

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun login() {
        val emailValue = _email.value?.trim() ?: ""
        val passwordValue = _password.value ?: ""

        // Validation
        if (emailValue.isEmpty()) {
            _loginState.value = LoginState.Error("Email is required")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            _loginState.value = LoginState.Error("Please enter a valid email address")
            return
        }

        if (passwordValue.isEmpty()) {
            _loginState.value = LoginState.Error("Password is required")
            return
        }

        if (passwordValue.length < 6) {
            _loginState.value = LoginState.Error("Password must be at least 6 characters")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val result = repository.login(emailValue, passwordValue)
                result.fold(
                    onSuccess = { response ->
                        val token = response.token ?: ""
                        if (token.isNotEmpty()) {
                            _loginState.value = LoginState.Success(token, emailValue)
                        } else {
                            _loginState.value = LoginState.Error("Invalid token received")
                        }
                    },
                    onFailure = { exception ->
                        _loginState.value = LoginState.Error(
                            exception.message ?: "Login failed. Please try again."
                        )
                    }
                )
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(
                    "Network error. Please check your connection."
                )
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String, val email: String) : LoginState()
    data class Error(val message: String) : LoginState()
}