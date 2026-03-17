package com.study.development.presentation.product

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.study.development.R
import com.study.development.presentation.cart.CartActivity

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val button = findViewById<Button>(R.id.addToCartButton)
        button.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}
