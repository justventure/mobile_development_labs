package com.study.development.domain.usecase.auth

import com.study.development.domain.ports.inbound.AuthUseCase

class LoginUseCase(private val port: AuthUseCase) {
    operator fun invoke(email: String, password: String): Boolean =
        port.login(email, password)
}
