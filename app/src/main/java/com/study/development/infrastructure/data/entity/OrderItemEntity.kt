package com.study.development.infrastructure.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemsJson: String,
    val total: Double,
    val date: Long
)
