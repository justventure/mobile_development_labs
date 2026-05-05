package com.study.development.domain.ports.inbound

import com.study.development.domain.entities.CartItem
import com.study.development.domain.entities.Order

interface OrderPort {
    suspend fun getOrders(): List<Order>
    suspend fun saveOrder(items: List<CartItem>, total: Double): Order
}
