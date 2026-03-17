package com.study.development.domain.ports.outbound

import com.study.development.domain.entities.CartItem
import com.study.development.domain.entities.Product

interface CartPort {
    fun addItem(product: Product)
    fun removeItem(productId: Int)
    fun getItems(): List<CartItem>
    fun getTotalPrice(): Double
    fun clear()
}
