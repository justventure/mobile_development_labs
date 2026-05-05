package com.study.development.domain.entities

data class Order(
    val id: Int,
    val items: List<CartItem>,
    val total: Double,
    val date: Long
)
