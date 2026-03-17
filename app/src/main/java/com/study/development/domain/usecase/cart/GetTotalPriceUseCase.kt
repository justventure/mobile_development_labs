package com.study.development.domain.usecase.cart

import com.study.development.domain.ports.inbound.CartUseCase

class GetTotalPriceUseCase(private val port: CartUseCase) {
    operator fun invoke(): Double = port.getTotalPrice()
}
