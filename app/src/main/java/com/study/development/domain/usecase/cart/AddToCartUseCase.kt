package com.study.development.domain.usecase.cart

import com.study.development.domain.model.Product
import com.study.development.domain.repository.CartRepository

class AddToCartUseCase(private val repository: CartRepository) {
    operator fun invoke(product: Product) = repository.addItem(product)
}
