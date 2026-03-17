package com.study.development.domain.repository

import com.study.development.domain.model.User

interface AuthRepository {
    fun login(email: String, password: String): Boolean
    fun register(name: String, email: String, password: String): Boolean
    fun logout()
    fun isLoggedIn(): Boolean
    fun getUser(): User?
}
