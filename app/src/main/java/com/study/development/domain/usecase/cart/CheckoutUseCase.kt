package com.study.development.domain.usecase.cart

import com.study.development.domain.ports.inbound.CartUseCase

class CheckoutUseCase(private val port: CartUseCase) {
    operator fun invoke() = port.clear()
}
