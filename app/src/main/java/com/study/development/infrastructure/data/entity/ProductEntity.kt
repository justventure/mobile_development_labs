package com.study.development.infrastructure.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val description: String,
    val category: String = ""
)
