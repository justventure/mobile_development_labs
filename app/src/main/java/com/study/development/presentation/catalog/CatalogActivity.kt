package com.study.development.presentation.catalog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import com.study.development.presentation.cart.CartActivity
import com.study.development.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogActivity : AppCompatActivity() {

    private val viewModel: CatalogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

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
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCartCount()
    }
}
