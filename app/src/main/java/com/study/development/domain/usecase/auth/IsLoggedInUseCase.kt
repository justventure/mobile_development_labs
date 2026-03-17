package com.study.development.domain.usecase.auth

import com.study.development.domain.ports.inbound.AuthUseCase

class IsLoggedInUseCase(private val port: AuthUseCase) {
    operator fun invoke(): Boolean = port.isLoggedIn()
}
