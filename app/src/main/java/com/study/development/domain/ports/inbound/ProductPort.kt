package com.study.development.domain.ports.inbound

import com.study.development.domain.entities.Product

interface ProductPort {
    fun getProducts(): List<Product>
}
