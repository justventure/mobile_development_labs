package com.study.development.application.use_cases.cart

import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.ProductPort
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val port: ProductPort) {
    operator fun invoke(): List<Product> = port.fetchProducts()
}
