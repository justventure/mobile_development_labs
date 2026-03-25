package com.study.development.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.development.domain.entities.Product
import com.study.development.application.use_cases.auth.LogoutUseCase
import com.study.development.application.use_cases.cart.AddToCartUseCase
import com.study.development.application.use_cases.cart.GetCartItemsUseCase
import com.study.development.application.use_cases.cart.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _cartCount = MutableLiveData<Int>()
    val cartCount: LiveData<Int> = _cartCount

    fun loadProducts() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getProductsUseCase() }
            _products.value = result
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { addToCartUseCase(product) }
            refreshCartCount()
        }
    }

    fun refreshCartCount() {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) { getCartItemsUseCase().sumOf { it.quantity } }
            _cartCount.value = count
        }
    }

    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { logoutUseCase() }
        }
    }
}
