package com.study.development.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.development.domain.model.Product
import com.study.development.domain.usecase.cart.AddToCartUseCase
import com.study.development.domain.usecase.cart.GetCartItemsUseCase
import com.study.development.domain.usecase.product.GetProductsUseCase

class CatalogViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _cartCount = MutableLiveData<Int>()
    val cartCount: LiveData<Int> = _cartCount

    fun loadProducts() {
        _products.value = getProductsUseCase()
    }

    fun addToCart(product: Product) {
        addToCartUseCase(product)
        refreshCartCount()
    }

    fun refreshCartCount() {
        _cartCount.value = getCartItemsUseCase().sumOf { it.quantity }
    }
}
