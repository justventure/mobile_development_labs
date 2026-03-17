package com.study.development.domain.usecase.auth

import com.study.development.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Boolean =
        repository.login(email, password)
}
