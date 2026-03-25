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
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProductsUseCase()
            _products.postValue(result)
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase(product)
            val count = getCartItemsUseCase().sumOf { it.quantity }
            _cartCount.postValue(count)
        }
    }

    fun refreshCartCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val count = getCartItemsUseCase().sumOf { it.quantity }
            _cartCount.postValue(count)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
        }
    }
}
