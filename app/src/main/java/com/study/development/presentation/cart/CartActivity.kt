package com.study.development.presentation.cart

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import com.study.development.data.local.CartStorage
import com.study.development.data.repository.CartRepositoryImpl
import com.study.development.domain.usecase.cart.CheckoutUseCase
import com.study.development.domain.usecase.cart.GetCartItemsUseCase
import com.study.development.domain.usecase.cart.GetTotalPriceUseCase
import com.study.development.domain.usecase.cart.RemoveFromCartUseCase

class CartActivity : AppCompatActivity() {

    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val repository = CartRepositoryImpl(CartStorage(this))
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(
                    GetCartItemsUseCase(repository),
                    RemoveFromCartUseCase(repository),
                    GetTotalPriceUseCase(repository),
                    CheckoutUseCase(repository)
                ) as T
            }
        })[CartViewModel::class.java]

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