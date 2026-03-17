package com.study.development.domain.ports.inbound

import com.study.development.domain.model.Product

interface ProductUseCase {
    fun getProducts(): List<Product>
}
