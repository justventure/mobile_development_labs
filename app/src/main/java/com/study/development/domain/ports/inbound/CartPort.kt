package com.study.development.domain.ports.inbound

import com.study.development.domain.entities.Product

interface AddToCartPort {
    fun addItem(product: Product)
}

interface RemoveFromCartPort {
    fun removeItem(productId: Int)
}

interface GetCartItemsPort {
    fun getItems(): List<Product>
}

interface GetTotalPricePort {
    fun getTotalPrice(): Double
}

interface ClearCartPort {
    fun clear()
}
