package com.study.development.domain.repository

import com.study.development.domain.model.Product

interface CartRepository {
    fun addItem(product: Product)
    fun removeItem(productId: Int)
    fun getItems(): List<Product>
    fun getTotalPrice(): Double
    fun clear()
}
