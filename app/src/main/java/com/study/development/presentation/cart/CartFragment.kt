package com.study.development.presentation.cart

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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalText = view.findViewById<TextView>(R.id.totalText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        val checkoutButton = view.findViewById<Button>(R.id.checkoutButton)

        adapter = CartAdapter { cartItem ->
            viewModel.removeItem(cartItem.product.id)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.total.observe(viewLifecycleOwner) { total ->
            totalText.text = "Total: $$total"
        }

        viewModel.checkoutDone.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Order placed!", Toast.LENGTH_SHORT).show()
        }

        checkoutButton.setOnClickListener {
            val items = viewModel.items.value
            val total = viewModel.total.value ?: 0.0
            if (items.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty!", Toast.LENGTH_SHORT).show()
            } else {
                val dialog = ConfirmOrderDialogFragment.newInstance(items, total)
                dialog.onConfirm = { viewModel.checkout() }
                dialog.show(parentFragmentManager, "confirm_order")
            }
        }

        viewModel.loadCart()
    }
}
