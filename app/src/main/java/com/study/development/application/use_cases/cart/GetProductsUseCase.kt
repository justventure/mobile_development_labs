package com.study.development.application.use_cases.cart

import com.study.development.domain.entities.Product
import com.study.development.domain.ports.inbound.ProductPort
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val port: ProductPort
) {
    suspend operator fun invoke(): List<Product> {
        return port.fetchProducts()
    }
}
