package com.study.development.presentation.catalog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.development.App
import com.study.development.R
import com.study.development.data.local.SessionPreferences
import com.study.development.data.repository.AuthRepositoryImpl
import com.study.development.domain.usecase.auth.LogoutUseCase
import com.study.development.domain.usecase.cart.AddToCartUseCase
import com.study.development.domain.usecase.cart.GetCartItemsUseCase
import com.study.development.domain.usecase.product.GetProductsUseCase
import com.study.development.data.repository.ProductRepositoryImpl
import com.study.development.presentation.cart.CartActivity
import com.study.development.presentation.login.LoginActivity

class CatalogActivity : AppCompatActivity() {

    private lateinit var viewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        val cartRepository = (application as App).cartRepository
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CatalogViewModel(
                    GetProductsUseCase(ProductRepositoryImpl()),
                    AddToCartUseCase(cartRepository),
                    GetCartItemsUseCase(cartRepository)
                ) as T
            }
        })[CatalogViewModel::class.java]

        val cartCountText = findViewById<TextView>(R.id.cartCountText)
        val recyclerView = findViewById<RecyclerView>(R.id.catalogRecyclerView)
        val cartButton = findViewById<Button>(R.id.cartButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        viewModel.products.observe(this) { products ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CatalogAdapter(products) { product ->
                viewModel.addToCart(product)
            }
        }

        viewModel.cartCount.observe(this) { count ->
            cartCountText.text = "Cart: $count"
        }

        viewModel.loadProducts()
        viewModel.refreshCartCount()

        cartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        logoutButton.setOnClickListener {
            LogoutUseCase(AuthRepositoryImpl(SessionPreferences(this)))()
            (application as App).cartRepository.clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCartCount()
    }
}
