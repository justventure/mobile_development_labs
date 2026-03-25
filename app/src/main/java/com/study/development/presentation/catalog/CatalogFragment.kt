package com.study.development.presentation.catalog

import android.annotation.SuppressLint
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
    private lateinit var adapter: CatalogAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartCountText = view.findViewById<TextView>(R.id.cartCountText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.catalogRecyclerView)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        adapter = CatalogAdapter(
            onProductClick = { product ->
                val intent = Intent(requireContext(), ProductActivity::class.java).apply {
                    putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.id)
                    putExtra(ProductActivity.EXTRA_PRODUCT_NAME, product.name)
                    putExtra(ProductActivity.EXTRA_PRODUCT_PRICE, product.price)
                    putExtra(ProductActivity.EXTRA_PRODUCT_IMAGE, product.imageRes)
                    putExtra(ProductActivity.EXTRA_PRODUCT_DESC, product.description)
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    startActivity(intent)

                    requireActivity().overrideActivityTransition(
                        android.app.Activity.OVERRIDE_TRANSITION_OPEN,
                        R.anim.slide_in_bottom,
                        R.anim.slide_out_bottom
                    )
                } else {
                    startActivity(intent)

                    @Suppress("DEPRECATION")
                    requireActivity().overridePendingTransition(
                        R.anim.slide_in_bottom,
                        R.anim.slide_out_bottom
                    )
                }
            },
            onAddToCartClick = { product ->
                viewModel.addToCart(product)
                Toast.makeText(
                    requireContext(),
                    "${product.name} added to cart",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
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
