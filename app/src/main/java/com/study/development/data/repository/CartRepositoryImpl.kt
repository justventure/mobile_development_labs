package com.study.development.data.repository

import com.study.development.data.local.CartStorage
import com.study.development.domain.model.Product
import com.study.development.domain.repository.CartRepository

class CartRepositoryImpl(private val storage: CartStorage) : CartRepository {

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