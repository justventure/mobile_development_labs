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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _cartCount = MutableLiveData<Int>()
    val cartCount: LiveData<Int> = _cartCount

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _sortOrder = MutableStateFlow(SortOrder.NONE)

    enum class SortOrder {
        NONE, PRICE_ASC, PRICE_DESC, NAME_ASC
    }

    init {
        combine(_allProducts, _searchQuery, _sortOrder, _selectedCategory) { all, query, sort, cat ->
            applyFiltersAndSort(all, query, sort, cat)
        }.onEach { result ->
            _products.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProductsUseCase()
            _allProducts.value = result
            _categories.postValue(result.map { it.category }.filter { it.isNotBlank() }.distinct().sorted())
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
    }

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    private fun applyFiltersAndSort(
        list: List<Product>,
        query: String,
        sortOrder: SortOrder,
        category: String?
    ): List<Product> {
        var result = list

        if (!category.isNullOrBlank()) result = result.filter { it.category == category }
        if (query.isNotEmpty()) result = result.filter { it.name.contains(query, ignoreCase = true) }

        return when (sortOrder) {
            SortOrder.PRICE_ASC -> result.sortedBy { it.price }
            SortOrder.PRICE_DESC -> result.sortedByDescending { it.price }
            SortOrder.NAME_ASC -> result.sortedBy { it.name }
            SortOrder.NONE -> result
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase(product)
            _cartCount.postValue(getCartItemsUseCase().sumOf { it.quantity })
        }
    }

    fun refreshCartCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _cartCount.postValue(getCartItemsUseCase().sumOf { it.quantity })
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
        }
    }
}
