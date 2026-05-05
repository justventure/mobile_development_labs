package com.study.development.infrastructure.repository

import com.study.development.domain.entities.CartItem
import com.study.development.domain.entities.Order
import com.study.development.domain.ports.inbound.OrderPort
import com.study.development.infrastructure.data.dao.OrderDao
import com.study.development.infrastructure.data.entity.OrderEntity
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val dao: OrderDao
) : OrderPort {

    private val gson = Gson()

    override suspend fun saveOrder(items: List<CartItem>, total: Double): Order {
        val entity = OrderEntity(
            itemsJson = gson.toJson(items),
            total = total,
            date = System.currentTimeMillis()
        )
        dao.insertOrder(entity)
        return Order(
            id = 0,
            items = items,
            total = total,
            date = entity.date
        )
    }

    override suspend fun getOrders(): List<Order> {
        return dao.getAllOrders().map { entity ->
            val items = gson.fromJson(entity.itemsJson, Array<CartItem>::class.java).toList()
            Order(
                id = entity.id,
                items = items,
                total = entity.total,
                date = entity.date
            )
        }
    }
}
