package com.example.miniapp_mobile.data

import android.content.Context
import android.content.SharedPreferences
import com.example.miniapp_mobile.data.model.User
import com.google.gson.Gson

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val PREF_NAME = "mini_app_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER = "user_data"
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUser(user: User) {
        prefs.edit().putString(KEY_USER, gson.toJson(user)).apply()
    }

    fun getUser(): User? {
        val json = prefs.getString(KEY_USER, null) ?: return null
        return try {
            gson.fromJson(json, User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = getToken() != null
}
