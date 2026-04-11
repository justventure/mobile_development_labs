package com.study.development.infrastructure.repository

import com.study.development.domain.entities.Product
import com.study.development.domain.ports.inbound.ProductPort
import com.study.development.infrastructure.data.dao.CartDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val dao: CartDao
) : ProductPort {

    override suspend fun fetchProducts(): List<Product> {
        return dao.getAllProducts().map {
            Product(
                id = it.id,
                name = it.name,
                price = it.price,
                imageRes = it.imageRes,
                description = it.description
            )
        }
    }
}
