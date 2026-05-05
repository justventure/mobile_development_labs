package com.study.development.presentation.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import com.study.development.domain.entities.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrdersAdapter : ListAdapter<Order, OrdersAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.orderId)
        val orderDate: TextView = view.findViewById(R.id.orderDate)
        val orderTotal: TextView = view.findViewById(R.id.orderTotal)
        val orderItems: TextView = view.findViewById(R.id.orderItems)
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        holder.orderId.text = "Заказ #${order.id}"
        holder.orderDate.text = sdf.format(Date(order.date))
        holder.orderTotal.text = "Итого: $${order.total}"
        holder.orderItems.text = order.items.joinToString("\n") {
            "${it.product.name} x${it.quantity}"
        }
    }
}
