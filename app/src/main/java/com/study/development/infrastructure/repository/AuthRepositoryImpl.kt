package com.study.development.infrastructure.repository

import com.study.development.infrastructure.adapters.local.SessionPreferences
import com.study.development.domain.entities.User
import com.study.development.domain.ports.outbound.AuthPort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val prefs: SessionPreferences
) : AuthPort {

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
