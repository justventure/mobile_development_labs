package com.study.development.infrastructure.adapters.local

import android.content.Context
import androidx.core.content.edit
import com.study.development.domain.entities.CartItem
import com.study.development.domain.entities.Product
import org.json.JSONArray
import org.json.JSONObject

class CartStorage(context: Context) {
    private val prefs = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)

    fun saveItems(items: List<CartItem>) {
        val array = JSONArray()
        items.forEach { cartItem ->
            val obj = JSONObject()
            obj.put("id", cartItem.product.id)
            obj.put("name", cartItem.product.name)
            obj.put("price", cartItem.product.price)
            obj.put("imageRes", cartItem.product.imageRes)
            obj.put("quantity", cartItem.quantity)
            array.put(obj)
        }
        prefs.edit { putString("cart_items", array.toString()) }
    }

    fun loadItems(): MutableList<CartItem> {
        val json = prefs.getString("cart_items", null) ?: return mutableListOf()
        val array = JSONArray(json)
        val items = mutableListOf<CartItem>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            items.add(
                CartItem(
                    product = Product(
                        id = obj.getInt("id"),
                        name = obj.getString("name"),
                        price = obj.getDouble("price"),
                        imageRes = obj.getInt("imageRes")
                    ),
                    quantity = obj.getInt("quantity")
                )
            )
        }
        return items
    }

    fun clear() {
        prefs.edit { remove("cart_items") }
    }
}
