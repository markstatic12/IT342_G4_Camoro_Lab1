package com.example.miniapp_mobile.data.repository

import com.example.miniapp_mobile.data.model.LoginRequest
import com.example.miniapp_mobile.data.model.LoginResponse
import com.example.miniapp_mobile.data.model.RegisterRequest
import com.example.miniapp_mobile.data.model.User
import com.example.miniapp_mobile.data.remote.RetrofitClient
import org.json.JSONObject

class AuthRepository {

    private val api = RetrofitClient.apiService

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = parseErrorBody(response.errorBody()?.string())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<String> {
        return try {
            val response = api.register(RegisterRequest(firstName, lastName, email, password))
            if (response.isSuccessful) {
                Result.success(response.body()?.message ?: "Registration successful")
            } else {
                val errorMsg = parseErrorBody(response.errorBody()?.string())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    suspend fun getProfile(token: String): Result<User> {
        return try {
            val response = api.getProfile("Bearer $token")
            if (response.isSuccessful) {
                val user = response.body()?.user
                    ?: return Result.failure(Exception("Empty profile response"))
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to load profile"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    suspend fun logout(token: String): Result<String> {
        return try {
            val response = api.logout("Bearer $token")
            if (response.isSuccessful) {
                Result.success("Logged out successfully")
            } else {
                Result.failure(Exception("Logout failed"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    private fun parseErrorBody(errorJson: String?): String {
        if (errorJson.isNullOrBlank()) return "An unexpected error occurred"
        return try {
            JSONObject(errorJson).optString("message", errorJson)
        } catch (e: Exception) {
            errorJson
        }
    }
}