package com.study.development.domain.usecase.cart

import com.study.development.domain.model.Product
import com.study.development.domain.ports.inbound.CartUseCase

class GetCartItemsUseCase(private val port: CartUseCase) {
    operator fun invoke(): List<Product> = port.getItems()
}
