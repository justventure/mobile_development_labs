package com.study.development.data.repository

import com.study.development.data.local.SessionPreferences
import com.study.development.domain.model.User
import com.study.development.domain.repository.AuthRepository

class AuthRepositoryImpl(private val prefs: SessionPreferences) : AuthRepository {

    override fun login(email: String, password: String): Boolean {
        prefs.save(name = email, email = email)
        return true
    }

    override fun register(name: String, email: String, password: String): Boolean {
        prefs.save(name = name, email = email)
        return true
    }

    override fun logout() = prefs.clear()

    override fun isLoggedIn(): Boolean = prefs.isLoggedIn()

    override fun getUser(): User? {
        if (!prefs.isLoggedIn()) return null
        return User(name = prefs.getName(), email = prefs.getEmail())
    }
}
