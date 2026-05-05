package com.study.development.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.development.application.use_cases.orders.GetOrdersUseCase
import com.study.development.domain.entities.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    fun loadOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            _orders.postValue(getOrdersUseCase())
        }
    }
}
