package com.study.development.domain.usecase.cart

import com.study.development.domain.repository.CartRepository

class GetTotalPriceUseCase(private val repository: CartRepository) {
    operator fun invoke(): Double = repository.getTotalPrice()
}
