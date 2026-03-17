package com.study.development.domain.usecase.cart

import com.study.development.domain.ports.inbound.CartUseCase

class RemoveFromCartUseCase(private val port: CartUseCase) {
    operator fun invoke(productId: Int) = port.removeItem(productId)
}
