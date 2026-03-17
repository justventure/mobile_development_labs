package com.study.development.domain.usecase.auth

import com.study.development.domain.ports.inbound.AuthUseCase

class RegisterUseCase(private val port: AuthUseCase) {
    operator fun invoke(name: String, email: String, password: String): Boolean =
        port.register(name, email, password)
}
