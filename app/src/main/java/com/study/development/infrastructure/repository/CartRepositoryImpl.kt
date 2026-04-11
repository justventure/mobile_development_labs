package com.study.development.infrastructure.repository

import com.study.development.domain.entities.CartItem
import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.CartPort
import com.study.development.infrastructure.data.dao.CartDao
import com.study.development.infrastructure.data.entity.CartItemEntity
import com.study.development.infrastructure.data.entity.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val dao: CartDao
) : CartPort {

    override suspend fun addItem(product: Product) {
        val existing = dao.getCartItems().find { it.productId == product.id }

        if (existing != null) {
            dao.insertCartItem(
                existing.copy(quantity = existing.quantity + 1)
            )
        } else {
            dao.insertProduct(
                ProductEntity(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    imageRes = product.imageRes,
                    description = product.description
                )
            )

            dao.insertCartItem(
                CartItemEntity(
                    productId = product.id,
                    quantity = 1
                )
            )
        }
    }

    override suspend fun removeItem(productId: Int) {
        val items = dao.getCartItems()
            .filter { it.productId != productId }

        dao.clearCart()

        items.forEach {
            dao.insertCartItem(it)
        }
    }

    override suspend fun getItems(): List<CartItem> {
        val cartItems = dao.getCartItems()

        return cartItems.mapNotNull { item ->
            val product = dao.getProductById(item.productId) ?: return@mapNotNull null

            CartItem(
                product = Product(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    imageRes = product.imageRes,
                    description = product.description
                ),
                quantity = item.quantity
            )
        }
    }

    override suspend fun getTotalPrice(): Double {
        return getItems().sumOf { it.totalPrice }
    }

    override suspend fun clear() {
        dao.clearCart()
    }
}
