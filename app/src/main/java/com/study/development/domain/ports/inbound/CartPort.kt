package com.study.development.domain.ports.inbound

import com.study.development.domain.model.Product

interface CartUseCase {
    fun addItem(product: Product)
    fun removeItem(productId: Int)
    fun getItems(): List<Product>
    fun getTotalPrice(): Double
    fun clear()
}
