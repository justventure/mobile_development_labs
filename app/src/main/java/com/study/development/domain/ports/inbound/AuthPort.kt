package com.study.development.domain.ports.inbound

interface AuthUseCase {
    fun login(email: String, password: String): Boolean
    fun register(name: String, email: String, password: String): Boolean
    fun logout()
    fun isLoggedIn(): Boolean
}
