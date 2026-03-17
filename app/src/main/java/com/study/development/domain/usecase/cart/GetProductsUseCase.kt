package com.study.development.domain.usecase.product

import com.study.development.domain.model.Product
import com.study.development.domain.repository.ProductRepository

class GetProductsUseCase(private val repository: ProductRepository) {
    operator fun invoke(): List<Product> = repository.getProducts()
}
