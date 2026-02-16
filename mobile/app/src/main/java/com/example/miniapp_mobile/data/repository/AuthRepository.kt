package com.example.miniapp_mobile.data.repository

import com.example.miniapp_mobile.data.api.RetrofitClient
import com.example.miniapp_mobile.data.model.AuthResponse
import com.example.miniapp_mobile.data.model.LoginRequest
import com.example.miniapp_mobile.data.model.MessageResponse
import com.example.miniapp_mobile.data.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    
    private val authApiService = RetrofitClient.authApiService
    
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<MessageResponse> = withContext(Dispatchers.IO) {
        try {
            val request = RegisterRequest(firstName, lastName, email, password)
            val response = authApiService.register(request)
            
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Registration failed"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // File: app/src/main/java/com/example/miniapp_mobile/data/repository/AuthRepository.kt
    suspend fun login(email: String, password: String): Result<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(email, password)
                val response = authApiService.login(request)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Empty response body"))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Login failed"
                    Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
