package com.study.development.data.repository

import com.study.development.data.local.CartStorage
import com.study.development.domain.model.Product
import com.study.development.domain.ports.inbound.CartUseCase
import com.study.development.domain.ports.outbound.CartPort

class CartRepositoryImpl(private val storage: CartStorage) : CartUseCase, CartPort {

    private val items: MutableList<Product> = storage.loadItems()

    override fun addItem(product: Product) = persistItem(product)

    override fun removeItem(productId: Int) = deleteItem(productId)

    override fun getItems(): List<Product> = fetchItems()

    override fun getTotalPrice(): Double = calculateTotal()

    override fun clear() = clearAll()

    override fun persistItem(product: Product) {
        val index = items.indexOfFirst { it.id == product.id }
        if (index != -1) {
            items[index] = items[index].copy(quantity = items[index].quantity + 1)
        } else {
            items.add(product.copy(quantity = 1))
        }
        storage.saveItems(items)
    }

    override fun deleteItem(productId: Int) {
        items.removeAll { it.id == productId }
        storage.saveItems(items)
    }

    override fun fetchItems(): List<Product> = items.toList()

    override fun calculateTotal(): Double = items.sumOf { it.price * it.quantity }

    override fun clearAll() {
        items.clear()
        storage.clear()
    }
}
