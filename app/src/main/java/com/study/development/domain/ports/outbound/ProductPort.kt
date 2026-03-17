package com.study.development.domain.ports.outbound

import com.study.development.domain.entities.Product

interface ProductPort {
    fun fetchProducts(): List<Product>
}
