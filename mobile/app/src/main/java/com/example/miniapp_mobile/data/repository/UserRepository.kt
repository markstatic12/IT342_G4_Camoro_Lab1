package com.example.miniapp_mobile.data.repository

import com.example.miniapp_mobile.data.model.User
import com.example.miniapp_mobile.data.remote.RetrofitClient

class UserRepository {

    private val api = RetrofitClient.apiService

    suspend fun getProfile(token: String): Result<User> {
        return try {
            val response = api.getProfile("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch profile"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }
}