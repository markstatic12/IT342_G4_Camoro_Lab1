package com.example.miniapp_mobile.data.api

import com.example.miniapp_mobile.data.model.AuthResponse
import com.example.miniapp_mobile.data.model.LoginRequest
import com.example.miniapp_mobile.data.model.MessageResponse
import com.example.miniapp_mobile.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>
    
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>
}
