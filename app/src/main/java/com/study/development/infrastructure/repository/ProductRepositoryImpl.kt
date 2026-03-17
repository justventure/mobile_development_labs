package com.study.development.infrastructure.repository

import com.study.development.R
import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.ProductPort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor() : ProductPort {

    override fun fetchProducts(): List<Product> = listOf(
        Product(1, "Apple", 2.0, R.drawable.apple),
        Product(2, "Banana", 1.5, R.drawable.apple),
        Product(3, "Orange", 3.0, R.drawable.apple)
    )
}
