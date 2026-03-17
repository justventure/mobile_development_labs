package com.study.development.data.repository

import com.study.development.R
import com.study.development.domain.model.Product
import com.study.development.domain.repository.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    override fun getProducts(): List<Product> = listOf(
        Product(1, "Apple", 2.0, R.drawable.apple),
        Product(2, "Banana", 1.5, R.drawable.apple),
        Product(3, "Orange", 3.0, R.drawable.apple)
    )
}
