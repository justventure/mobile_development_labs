package com.study.development.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.development.domain.entities.CartItem
import com.study.development.application.use_cases.cart.CheckoutUseCase
import com.study.development.application.use_cases.cart.GetCartItemsUseCase
import com.study.development.application.use_cases.cart.GetTotalPriceUseCase
import com.study.development.application.use_cases.cart.RemoveFromCartUseCase
import com.study.development.application.use_cases.orders.SaveOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val saveOrderUseCase: SaveOrderUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<CartItem>>()
    val items: LiveData<List<CartItem>> = _items

    private val _total = MutableLiveData<Double>()
    val total: LiveData<Double> = _total

    private val _checkoutDone = MutableLiveData<Boolean>()
    val checkoutDone: LiveData<Boolean> = _checkoutDone

    fun loadCart() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(getCartItemsUseCase())
            _total.postValue(getTotalPriceUseCase())
        }
    }

    fun removeItem(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromCartUseCase(productId)
            loadCart()
        }
    }

    fun checkout() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = getCartItemsUseCase()
            val total = getTotalPriceUseCase()
            saveOrderUseCase(items, total)
            checkoutUseCase()
            loadCart()
            _checkoutDone.postValue(true)
        }
    }
}
