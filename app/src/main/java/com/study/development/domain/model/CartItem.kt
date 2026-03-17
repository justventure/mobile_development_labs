package com.study.development.domain.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
) {
    fun totalPrice(): Double = product.price * quantity
}
