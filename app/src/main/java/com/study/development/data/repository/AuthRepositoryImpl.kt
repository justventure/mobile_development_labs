package com.study.development.data.repository

import com.study.development.data.local.SessionPreferences
import com.study.development.domain.model.User
import com.study.development.domain.ports.inbound.AuthUseCase
import com.study.development.domain.ports.outbound.AuthPort

class AuthRepositoryImpl(private val prefs: SessionPreferences) : AuthUseCase, AuthPort {

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

    override fun authenticate(email: String, password: String): Boolean {
        prefs.save(name = email, email = email)
        return true
    }

    override fun createAccount(name: String, email: String, password: String): Boolean {
        prefs.save(name = name, email = email)
        return true
    }

    override fun invalidateSession() = prefs.clear()

    override fun checkSession(): Boolean = prefs.isLoggedIn()

    override fun fetchCurrentUser(): User? {
        if (!prefs.isLoggedIn()) return null
        return User(name = prefs.getName(), email = prefs.getEmail())
    }
}
