package com.study.development.domain.usecase.auth

import com.study.development.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}
