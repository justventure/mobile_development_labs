package com.study.development.infrastructure.adapters.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionPreferences @Inject constructor(@ApplicationContext context: Context) {
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
