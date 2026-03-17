package com.study.development.infrastructure.repository

import com.study.development.R
import com.study.development.domain.entities.Product
import com.study.development.domain.ports.outbound.ProductPort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor() : ProductPort {

    override fun fetchProducts(): List<Product> = listOf(
        Product(1, "Apple", 2.0, R.drawable.apple, "Fresh red apple from local farms."),
        Product(2, "Banana", 1.5, R.drawable.apple, "Sweet yellow banana, rich in potassium."),
        Product(3, "Orange", 3.0, R.drawable.apple, "Juicy orange packed with vitamin C."),
        Product(4, "Mango", 4.0, R.drawable.apple, "Tropical mango with sweet aroma."),
        Product(5, "Grapes", 2.5, R.drawable.apple, "Seedless green grapes, perfect for snacking."),
        Product(6, "Strawberry", 3.5, R.drawable.apple, "Fresh strawberries, great for desserts."),
        Product(7, "Watermelon", 5.0, R.drawable.apple, "Large juicy watermelon, perfect for summer."),
        Product(8, "Pineapple", 4.5, R.drawable.apple, "Tropical pineapple with tangy sweet taste.")
    )
}
