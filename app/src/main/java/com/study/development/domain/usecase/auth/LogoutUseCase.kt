package com.study.development.domain.usecase.auth

import com.study.development.domain.ports.inbound.AuthUseCase

class LogoutUseCase(private val port: AuthUseCase) {
    operator fun invoke() = port.logout()
}
