package com.study.development.domain.usecase.cart

import com.study.development.domain.model.Product
import com.study.development.domain.repository.CartRepository

class GetCartItemsUseCase(private val repository: CartRepository) {
    operator fun invoke(): List<Product> = repository.getItems()
}
