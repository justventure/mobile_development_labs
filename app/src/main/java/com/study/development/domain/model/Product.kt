package com.study.development.domain.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    var quantity: Int = 1
)


