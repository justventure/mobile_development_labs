package com.study.development.domain.ports.outbound

import com.study.development.domain.model.Product

interface CartPort {
    fun persistItem(product: Product)
    fun deleteItem(productId: Int)
    fun fetchItems(): List<Product>
    fun calculateTotal(): Double
    fun clearAll()
}
