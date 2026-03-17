package com.study.development.data.local

import android.content.Context

class SessionPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun save(name: String, email: String) {
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_name", name)
            .putString("user_email", email)
            .apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
    fun getName(): String = prefs.getString("user_name", "") ?: ""
    fun getEmail(): String = prefs.getString("user_email", "") ?: ""
    fun clear() = prefs.edit().clear().apply()
}
