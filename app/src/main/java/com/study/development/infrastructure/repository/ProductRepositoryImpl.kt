package com.study.development.infrastructure.repository

import com.study.development.domain.entities.Product
import com.study.development.domain.ports.inbound.ProductPort
import com.study.development.infrastructure.data.dao.ProductDao
import com.study.development.infrastructure.data.entity.ProductEntity
import com.study.development.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : ProductPort {

    override suspend fun fetchProducts(): List<Product> {
        if (dao.getAllProducts().isEmpty()) {
            dao.insertAll(
                listOf(
                    ProductEntity(1, "Apple", 2.0, R.drawable.apple, "Fresh red apple from local farms.", "Fruits"),
                    ProductEntity(2, "Banana", 1.5, R.drawable.banana, "Sweet yellow banana, rich in potassium.", "Fruits"),
                    ProductEntity(3, "Orange", 3.0, R.drawable.orange, "Juicy orange packed with vitamin C.", "Fruits"),
                    ProductEntity(4, "Mango", 4.0, R.drawable.mango, "Tropical mango with sweet aroma.", "Fruits"),
                    ProductEntity(5, "Grapes", 2.5, R.drawable.grapes, "Seedless green grapes, perfect for snacking.", "Fruits"),
                    ProductEntity(6, "Watermelon", 5.0, R.drawable.watermelon, "Large juicy watermelon, perfect for summer.", "Fruits"),
                    ProductEntity(7, "Pineapple", 4.5, R.drawable.pineapple, "Tropical pineapple with tangy sweet taste.", "Fruits")
                )
            )
        }
        return dao.getAllProducts().map {
            Product(
                id = it.id,
                name = it.name,
                price = it.price,
                imageRes = it.imageRes,
                description = it.description,
                category = it.category
            )
        }
    }
}
