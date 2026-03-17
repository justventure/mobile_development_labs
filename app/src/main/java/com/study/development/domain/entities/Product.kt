package com.study.development.domain.entities

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val description: String = ""
)
