package com.study.development.presentation.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import com.study.development.domain.entities.CartItem

class CartAdapter(
    private val onRemove: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.cartItemName)
        val price: TextView = view.findViewById(R.id.cartItemPrice)
        val quantity: TextView = view.findViewById(R.id.cartItemQuantity)
        val removeButton: Button = view.findViewById(R.id.removeButton)
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.product.id == newItem.product.id &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.totalPrice == newItem.totalPrice
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.name.text = item.product.name
        holder.price.text = "$${item.totalPrice}"
        holder.quantity.text = "x${item.quantity}"

        holder.removeButton.setOnClickListener {
            onRemove(item)
        }
    }
}
