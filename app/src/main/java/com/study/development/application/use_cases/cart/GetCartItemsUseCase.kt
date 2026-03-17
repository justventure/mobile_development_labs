package com.study.development.application.use_cases.cart

import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.CartPort
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val port: CartPort) {
    operator fun invoke(): List<Product> = port.getItems()
}
