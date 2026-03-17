package com.study.development.infrastructure.adapters.local

import android.content.Context
import com.study.development.domain.entities.Product
import org.json.JSONArray
import org.json.JSONObject

class CartStorage(context: Context) {
    private val prefs = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)

    fun saveItems(items: List<Product>) {
        val array = JSONArray()
        items.forEach { product ->
            val obj = JSONObject()
            obj.put("id", product.id)
            obj.put("name", product.name)
            obj.put("price", product.price)
            obj.put("imageRes", product.imageRes)
            obj.put("quantity", product.quantity)
            array.put(obj)
        }
        prefs.edit().putString("cart_items", array.toString()).apply()
    }

    fun loadItems(): MutableList<Product> {
        val json = prefs.getString("cart_items", null) ?: return mutableListOf()
        val array = JSONArray(json)
        val items = mutableListOf<Product>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            items.add(
                Product(
                    id = obj.getInt("id"),
                    name = obj.getString("name"),
                    price = obj.getDouble("price"),
                    imageRes = obj.getInt("imageRes"),
                    quantity = obj.getInt("quantity")
                )
            )
        }
        return items
    }

    fun clear() {
        prefs.edit().remove("cart_items").apply()
    }
}
