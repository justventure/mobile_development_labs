package com.study.development.domain.usecase.product

import com.study.development.domain.model.Product
import com.study.development.domain.ports.inbound.ProductUseCase

class GetProductsUseCase(private val port: ProductUseCase) {
    operator fun invoke(): List<Product> = port.getProducts()
}
