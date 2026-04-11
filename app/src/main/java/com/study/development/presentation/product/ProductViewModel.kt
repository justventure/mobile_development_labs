package com.study.development.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.development.application.use_cases.cart.AddToCartUseCase
import com.study.development.domain.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase(product)
        }
    }
}
