package com.study.development.application.use_cases.cart

import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.CartPort
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val port: CartPort
) {
    suspend operator fun invoke(product: Product) {
        port.addItem(product)
    }
}
