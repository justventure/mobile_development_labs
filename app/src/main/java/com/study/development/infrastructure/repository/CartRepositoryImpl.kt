package com.study.development.infrastructure.repository

import com.study.development.infrastructure.adapters.local.CartStorage
import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.CartPort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val storage: CartStorage
) : CartPort {

    private val items: MutableList<Product> = storage.loadItems()

    override fun addItem(product: Product) {
        val index = items.indexOfFirst { it.id == product.id }
        if (index != -1) {
            items[index] = items[index].copy(quantity = items[index].quantity + 1)
        } else {
            items.add(product.copy(quantity = 1))
        }
        storage.saveItems(items)
    }

    override fun removeItem(productId: Int) {
        items.removeAll { it.id == productId }
        storage.saveItems(items)
    }

    override fun getItems(): List<Product> = items.toList()

    override fun getTotalPrice(): Double = items.sumOf { it.price * it.quantity }

    override fun clear() {
        items.clear()
        storage.clear()
    }
}
