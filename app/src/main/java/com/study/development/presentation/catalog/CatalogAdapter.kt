package com.study.development.presentation.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.study.development.R
import com.study.development.domain.entities.Product

class CatalogAdapter(
    private val products: List<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.productImage)
        val name: TextView = view.findViewById(R.id.productName)
        val price: TextView = view.findViewById(R.id.productPrice)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.image.setImageResource(product.imageRes)
        holder.name.text = product.name
        holder.price.text = "$${product.price}"
        holder.itemView.setOnClickListener { onProductClick(product) }
        holder.addToCartButton.setOnClickListener { onAddToCartClick(product) }
    }

    override fun getItemCount() = products.size
}
