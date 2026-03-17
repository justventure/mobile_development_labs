package com.study.development.domain.repository

import com.study.development.domain.model.Product

interface ProductRepository {
    fun getProducts(): List<Product>
}
