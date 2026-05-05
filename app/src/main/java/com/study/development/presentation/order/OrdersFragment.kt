package com.study.development.presentation.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var adapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.ordersRecyclerView)

        adapter = OrdersAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            adapter.submitList(orders)
        }

        viewModel.loadOrders()
    }
}
