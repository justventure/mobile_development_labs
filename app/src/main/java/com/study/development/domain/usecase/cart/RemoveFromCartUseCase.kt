package com.study.development.domain.usecase.cart

import com.study.development.domain.repository.CartRepository

class RemoveFromCartUseCase(private val repository: CartRepository) {
    operator fun invoke(productId: Int) = repository.removeItem(productId)
}
