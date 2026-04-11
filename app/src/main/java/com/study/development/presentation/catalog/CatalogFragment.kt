package com.study.development.presentation.catalog

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.study.development.R
import com.study.development.presentation.login.LoginActivity
import com.study.development.presentation.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val viewModel: CatalogViewModel by viewModels()
    private lateinit var adapter: CatalogAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartCountText = view.findViewById<TextView>(R.id.cartCountText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.catalogRecyclerView)
        val logoutButton = view.findViewById<MaterialButton>(R.id.logoutButton)
        val searchInput = view.findViewById<TextInputEditText>(R.id.searchInput)
        val filterButton = view.findViewById<MaterialButton>(R.id.filterButton)
        val chipGroup = view.findViewById<LinearLayout>(R.id.chipGroup)

        adapter = CatalogAdapter(
            onProductClick = { product ->
                val intent = Intent(requireContext(), ProductActivity::class.java).apply {
                    putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.id)
                    putExtra(ProductActivity.EXTRA_PRODUCT_NAME, product.name)
                    putExtra(ProductActivity.EXTRA_PRODUCT_PRICE, product.price)
                    putExtra(ProductActivity.EXTRA_PRODUCT_IMAGE, product.imageRes)
                    putExtra(ProductActivity.EXTRA_PRODUCT_DESC, product.description)
                }
                startActivity(intent)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    requireActivity().overrideActivityTransition(android.app.Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
                } else {
                    @Suppress("DEPRECATION")
                    requireActivity().overridePendingTransition(0, 0)
                }
            },
            onAddToCartClick = { product ->
                viewModel.addToCart(product)
                Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        searchInput.addTextChangedListener { text ->
            viewModel.setSearchQuery(text?.toString() ?: "")
        }

        filterButton.setOnClickListener { showSortMenu(it) }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            buildCategoryChips(chipGroup, categories)
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        viewModel.cartCount.observe(viewLifecycleOwner) { count ->
            cartCountText.text = "Cart: $count"
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedCategory.collectLatest { selected ->
                updateChipSelection(chipGroup, selected)
            }
        }

        viewModel.loadProducts()
        viewModel.refreshCartCount()

        logoutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun buildCategoryChips(container: LinearLayout, categories: List<String>) {
        container.removeAllViews()

        container.addView(createChip("All", null))
        categories.forEach { container.addView(createChip(it, it)) }
    }

    private fun createChip(label: String, category: String?): Chip {
        return Chip(requireContext()).apply {
            text = label
            isCheckable = true
            isCheckedIconVisible = false
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setAllCornerSizes(999f)
                .build()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { marginEnd = 8 }
            setOnClickListener { viewModel.setCategory(category) }
        }
    }

    private fun updateChipSelection(container: LinearLayout, selected: String?) {
        for (i in 0 until container.childCount) {
            val chip = container.getChildAt(i) as? Chip ?: continue
            val isAll = selected == null && chip.text == "All"
            val isMatch = selected != null && chip.text == selected
            chip.isChecked = isAll || isMatch
        }
    }

    private fun showSortMenu(anchor: View) {
        val popup = android.widget.PopupMenu(requireContext(), anchor)
        popup.menu.add(0, 0, 0, "Default")
        popup.menu.add(0, 1, 1, "Price: low to high")
        popup.menu.add(0, 2, 2, "Price: high to low")
        popup.menu.add(0, 3, 3, "Name: A → Z")

        popup.setOnMenuItemClickListener { item ->
            viewModel.setSortOrder(
                when (item.itemId) {
                    1 -> CatalogViewModel.SortOrder.PRICE_ASC
                    2 -> CatalogViewModel.SortOrder.PRICE_DESC
                    3 -> CatalogViewModel.SortOrder.NAME_ASC
                    else -> CatalogViewModel.SortOrder.NONE
                }
            )
            true
        }
        popup.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCartCount()
    }
}
