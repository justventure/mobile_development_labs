package com.study.development.presentation.product

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.study.development.R
import com.study.development.domain.entities.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private val viewModel: ProductViewModel by viewModels()

    companion object {
        const val EXTRA_PRODUCT_ID = "product_id"
        const val EXTRA_PRODUCT_NAME = "product_name"
        const val EXTRA_PRODUCT_PRICE = "product_price"
        const val EXTRA_PRODUCT_IMAGE = "product_image"
        const val EXTRA_PRODUCT_DESC = "product_desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = Product(
            id = intent.getIntExtra(EXTRA_PRODUCT_ID, 0),
            name = intent.getStringExtra(EXTRA_PRODUCT_NAME) ?: "",
            price = intent.getDoubleExtra(EXTRA_PRODUCT_PRICE, 0.0),
            imageRes = intent.getIntExtra(EXTRA_PRODUCT_IMAGE, 0),
            description = intent.getStringExtra(EXTRA_PRODUCT_DESC) ?: ""
        )

        findViewById<ImageView>(R.id.productImage).setImageResource(product.imageRes)
        findViewById<TextView>(R.id.productName).text = product.name
        findViewById<TextView>(R.id.productPrice).text = "$${product.price}"
        findViewById<TextView>(R.id.productDescription).text = product.description

        findViewById<MaterialButton>(R.id.addToCartButton).setOnClickListener {
            viewModel.addToCart(product)
            Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        findViewById<MaterialButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }
}
