package com.study.development.presentation.catalog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import com.study.development.presentation.login.LoginActivity
import com.study.development.presentation.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val viewModel: CatalogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartCountText = view.findViewById<TextView>(R.id.cartCountText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.catalogRecyclerView)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.products.observe(viewLifecycleOwner) { products ->
            recyclerView.adapter = CatalogAdapter(
                products,
                onProductClick = { product ->
                    val intent = Intent(requireContext(), ProductActivity::class.java).apply {
                        putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.id)
                        putExtra(ProductActivity.EXTRA_PRODUCT_NAME, product.name)
                        putExtra(ProductActivity.EXTRA_PRODUCT_PRICE, product.price)
                        putExtra(ProductActivity.EXTRA_PRODUCT_IMAGE, product.imageRes)
                        putExtra(ProductActivity.EXTRA_PRODUCT_DESC, product.description)
                    }
                    startActivity(intent)
                },
                onAddToCartClick = { product ->
                    viewModel.addToCart(product)
                    Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                }
            )
        }

        viewModel.cartCount.observe(viewLifecycleOwner) { count ->
            cartCountText.text = "Cart: $count"
        }

        viewModel.loadProducts()
        viewModel.refreshCartCount()

        logoutButton.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCartCount()
    }
}
