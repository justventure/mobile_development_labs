package com.study.development.domain.usecase.auth

import com.study.development.domain.repository.AuthRepository

class IsLoggedInUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean = repository.isLoggedIn()
}
