package com.study.development.domain.ports.outbound

import com.study.development.domain.entities.CartItem
import com.study.development.domain.entities.Product

interface CartPort {
    suspend fun addItem(product: Product)
    suspend fun removeItem(productId: Int)
    suspend fun getItems(): List<CartItem>
    suspend fun getTotalPrice(): Double
    suspend fun clear()
}
