package com.study.development.domain.ports.outbound

import com.study.development.domain.entities.User

interface AuthPort {
    fun authenticate(email: String, password: String): Boolean
    fun createAccount(name: String, email: String, password: String): Boolean
    fun invalidateSession()
    fun checkSession(): Boolean
    fun fetchCurrentUser(): User?
}
