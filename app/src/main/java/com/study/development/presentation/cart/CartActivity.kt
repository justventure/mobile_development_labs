package com.study.development.presentation.cart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.study.development.R
import com.study.development.presentation.catalog.CatalogActivity
import com.study.development.presentation.common.NavDirection
import com.study.development.presentation.common.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(
                        OVERRIDE_TRANSITION_CLOSE,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                } else {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }
        })

        val totalText = findViewById<TextView>(R.id.totalText)
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val checkoutButton = findViewById<Button>(R.id.checkoutButton)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.selectedItemId = R.id.nav_cart

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_catalog -> {
                    navigateTo(
                        Intent(this, CatalogActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        },
                        NavDirection.LEFT,
                        finishCurrent = true
                    )
                    true
                }
                R.id.nav_cart -> true
                else -> false
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        viewModel.items.observe(this) { items ->
            adapter = CartAdapter(items) { cartItem ->
                viewModel.removeItem(cartItem.product.id)
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
