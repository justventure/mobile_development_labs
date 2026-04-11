package com.study.development.application.use_cases.cart

import com.study.development.domain.ports.outbound.CartPort
import javax.inject.Inject

class GetTotalPriceUseCase @Inject constructor(
    private val port: CartPort
) {
    suspend operator fun invoke(): Double {
        return port.getTotalPrice()
    }
}
