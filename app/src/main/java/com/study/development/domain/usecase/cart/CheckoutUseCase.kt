package com.study.development.domain.usecase.cart

import com.study.development.domain.repository.CartRepository

class CheckoutUseCase(private val repository: CartRepository) {
    operator fun invoke() = repository.clear()
}
