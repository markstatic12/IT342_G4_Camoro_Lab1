package com.example.miniapp_mobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniapp_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    
    private val repository = AuthRepository()
    
    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState
    
    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName
    
    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName
    
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    
    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword
    
    fun updateFirstName(value: String) {
        _firstName.value = value
    }
    
    fun updateLastName(value: String) {
        _lastName.value = value
    }
    
    fun updateEmail(value: String) {
        _email.value = value
    }
    
    fun updatePassword(value: String) {
        _password.value = value
    }
    
    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
    }
    
    fun register() {
        val firstNameValue = _firstName.value?.trim() ?: ""
        val lastNameValue = _lastName.value?.trim() ?: ""
        val emailValue = _email.value?.trim() ?: ""
        val passwordValue = _password.value ?: ""
        val confirmPasswordValue = _confirmPassword.value ?: ""
        
        // Validation
        if (firstNameValue.isEmpty()) {
            _registerState.value = RegisterState.Error("First name is required")
            return
        }
        
        if (lastNameValue.isEmpty()) {
            _registerState.value = RegisterState.Error("Last name is required")
            return
        }
        
        if (emailValue.isEmpty()) {
            _registerState.value = RegisterState.Error("Email is required")
            return
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            _registerState.value = RegisterState.Error("Invalid email format")
            return
        }
        
        if (passwordValue.isEmpty()) {
            _registerState.value = RegisterState.Error("Password is required")
            return
        }
        
        if (passwordValue.length < 6) {
            _registerState.value = RegisterState.Error("Password must be at least 6 characters")
            return
        }
        
        if (passwordValue != confirmPasswordValue) {
            _registerState.value = RegisterState.Error("Passwords do not match")
            return
        }
        
        // Call API
        _registerState.value = RegisterState.Loading
        
        viewModelScope.launch {
            val result = repository.register(
                firstNameValue,
                lastNameValue,
                emailValue,
                passwordValue
            )
            
            result.fold(
                onSuccess = { response ->
                    _registerState.value = RegisterState.Success(response.message)
                },
                onFailure = { exception ->
                    _registerState.value = RegisterState.Error(
                        exception.message ?: "Registration failed"
                    )
                }
            )
        }
    }
    
    fun resetState() {
        _registerState.value = RegisterState.Idle
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}
