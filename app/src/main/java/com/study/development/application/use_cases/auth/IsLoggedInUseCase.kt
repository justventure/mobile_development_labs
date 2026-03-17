package com.study.development.application.use_cases.auth

import com.study.development.domain.ports.outbound.AuthPort
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(private val port: AuthPort) {
    operator fun invoke(): Boolean = port.checkSession()
}
