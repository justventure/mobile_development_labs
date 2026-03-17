package com.study.development.domain.entities

data class CartItem(
    val product: Product,
    val quantity: Int = 1
) {
    val totalPrice: Double get() = product.price * quantity
}
