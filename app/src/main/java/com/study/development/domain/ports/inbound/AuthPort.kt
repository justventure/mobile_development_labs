package com.study.development.domain.ports.inbound

interface LoginPort {
    fun login(email: String, password: String): Boolean
}

interface RegisterPort {
    fun register(name: String, email: String, password: String): Boolean
}

interface LogoutPort {
    fun logout()
}

interface IsLoggedInPort {
    fun isLoggedIn(): Boolean
}
