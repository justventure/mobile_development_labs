package com.study.development.presentation.catalog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.study.development.R
import com.study.development.presentation.cart.CartActivity
import com.study.development.presentation.common.NavDirection
import com.study.development.presentation.common.navigateTo
import com.study.development.presentation.login.LoginActivity
import com.study.development.presentation.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogActivity : AppCompatActivity() {

    private val viewModel: CatalogViewModel by viewModels()
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        val cartCountText = findViewById<TextView>(R.id.cartCountText)
        val recyclerView = findViewById<RecyclerView>(R.id.catalogRecyclerView)
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        bottomNavigation.selectedItemId = R.id.nav_catalog

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_catalog -> true
                R.id.nav_cart -> {
                    navigateTo(
                        Intent(this, CartActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        },
                        NavDirection.RIGHT
                    )
                    false
                }
                else -> false
            }
        }

        viewModel.products.observe(this) { products ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = CatalogAdapter(
                products,
                onProductClick = { product ->
                    navigateTo(
                        Intent(this, ProductActivity::class.java).apply {
                            putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.id)
                            putExtra(ProductActivity.EXTRA_PRODUCT_NAME, product.name)
                            putExtra(ProductActivity.EXTRA_PRODUCT_PRICE, product.price)
                            putExtra(ProductActivity.EXTRA_PRODUCT_IMAGE, product.imageRes)
                            putExtra(ProductActivity.EXTRA_PRODUCT_DESC, product.description)
                        },
                        NavDirection.RIGHT
                    )
                },
                onAddToCartClick = { product ->
                    viewModel.addToCart(product)
                    Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                }
            )
        }

        viewModel.cartCount.observe(this) { count ->
            cartCountText.text = "Cart: $count"
        }

        viewModel.loadProducts()
        viewModel.refreshCartCount()

        logoutButton.setOnClickListener {
            viewModel.logout()
            navigateTo(
                Intent(this, LoginActivity::class.java),
                NavDirection.LEFT,
                finishCurrent = true
            )
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.selectedItemId = R.id.nav_catalog
        viewModel.refreshCartCount()
    }
}
