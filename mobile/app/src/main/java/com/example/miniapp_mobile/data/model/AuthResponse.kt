package com.example.miniapp_mobile.data.model

data class AuthResponse(
    val message: String,
    val token: String? = null,
    val user: User? = null
)

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String
)
