package com.study.development.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.development.infrastructure.data.dao.CartDao
import com.study.development.infrastructure.data.dao.ProductDao
import com.study.development.infrastructure.data.dao.OrderDao
import com.study.development.infrastructure.data.entity.CartItemEntity
import com.study.development.infrastructure.data.entity.ProductEntity
import com.study.development.infrastructure.data.entity.UserEntity
import com.study.development.infrastructure.data.entity.OrderEntity

@Database(
    entities = [ProductEntity::class, CartItemEntity::class, UserEntity::class, OrderEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
}
