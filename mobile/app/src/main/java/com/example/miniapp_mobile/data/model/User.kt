package com.example.miniapp_mobile.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    val email: String,
    val status: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?
)