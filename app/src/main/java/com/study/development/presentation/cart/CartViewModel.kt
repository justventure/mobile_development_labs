package com.study.development.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.development.domain.entities.CartItem
import com.study.development.application.use_cases.cart.CheckoutUseCase
import com.study.development.application.use_cases.cart.GetCartItemsUseCase
import com.study.development.application.use_cases.cart.GetTotalPriceUseCase
import com.study.development.application.use_cases.cart.RemoveFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val checkoutUseCase: CheckoutUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<CartItem>>()
    val items: LiveData<List<CartItem>> = _items

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
