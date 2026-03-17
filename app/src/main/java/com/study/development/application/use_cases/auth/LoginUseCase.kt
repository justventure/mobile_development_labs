package com.study.development.application.use_cases.auth

import com.study.development.domain.ports.outbound.AuthPort
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val port: AuthPort) {
    operator fun invoke(email: String, password: String): Boolean =
        port.authenticate(email, password)
}
