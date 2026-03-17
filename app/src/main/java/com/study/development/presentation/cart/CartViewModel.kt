package com.study.development.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.development.domain.model.Product
import com.study.development.domain.usecase.cart.CheckoutUseCase
import com.study.development.domain.usecase.cart.GetCartItemsUseCase
import com.study.development.domain.usecase.cart.GetTotalPriceUseCase
import com.study.development.domain.usecase.cart.RemoveFromCartUseCase

class CartViewModel(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val checkoutUseCase: CheckoutUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<Product>>()
    val items: LiveData<List<Product>> = _items

    private val _total = MutableLiveData<Double>()
    val total: LiveData<Double> = _total

    private val _checkoutDone = MutableLiveData<Boolean>()
    val checkoutDone: LiveData<Boolean> = _checkoutDone

    fun loadCart() {
        _items.value = getCartItemsUseCase()
        _total.value = getTotalPriceUseCase()
    }

    fun removeItem(productId: Int) {
        removeFromCartUseCase(productId)
        loadCart()
    }

    fun checkout() {
        checkoutUseCase()
        loadCart()
        _checkoutDone.value = true
    }
}
