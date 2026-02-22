package com.example.miniapp_mobile.data.remote

import com.example.miniapp_mobile.data.model.LoginRequest
import com.example.miniapp_mobile.data.model.LoginResponse
import com.example.miniapp_mobile.data.model.MessageResponse
import com.example.miniapp_mobile.data.model.RegisterRequest
import com.example.miniapp_mobile.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>

    @GET("api/auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserResponse>

    @POST("api/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<MessageResponse>
}