package com.study.development.domain.ports.inbound

import com.study.development.domain.entities.Product

interface ProductPort {
    suspend fun fetchProducts(): List<Product>
}
