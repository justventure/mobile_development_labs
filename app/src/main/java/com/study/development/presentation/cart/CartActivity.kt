package com.study.development.presentation.cart

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val totalText = findViewById<TextView>(R.id.totalText)
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val checkoutButton = findViewById<Button>(R.id.checkoutButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.items.observe(this) { items ->
            adapter = CartAdapter(items) { product ->
                viewModel.removeItem(product.id)
            }
            recyclerView.adapter = adapter
        }

        viewModel.total.observe(this) { total ->
            totalText.text = "Total: $$total"
        }

        viewModel.checkoutDone.observe(this) {
            Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show()
        }

        checkoutButton.setOnClickListener {
            if (viewModel.items.value.isNullOrEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.checkout()
            }
        }

        viewModel.loadCart()
    }
}
