package com.study.development.domain.usecase.auth

import com.study.development.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    operator fun invoke(name: String, email: String, password: String): Boolean =
        repository.register(name, email, password)
}
