package com.study.development.presentation.cart

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.study.development.R
import com.study.development.domain.entities.CartItem

class ConfirmOrderDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_ITEMS = "arg_items"
        private const val ARG_TOTAL = "arg_total"

        fun newInstance(items: List<CartItem>, total: Double): ConfirmOrderDialogFragment {
            return ConfirmOrderDialogFragment().apply {
                arguments = bundleOf(
                    ARG_ITEMS to ArrayList(items),
                    ARG_TOTAL to total
                )
            }
        }
    }

    var onConfirm: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = arguments?.getSerializable(ARG_ITEMS) as? List<CartItem> ?: emptyList()
        val total = arguments?.getDouble(ARG_TOTAL) ?: 0.0

        val summary = buildString {
            items.forEach { item ->
                appendLine("${item.product.name} x${item.quantity} — $${item.totalPrice}")
            }
            appendLine()
            append("Итого: $$total")
        }

        return AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение заказа")
            .setMessage(summary)
            .setPositiveButton("Подтвердить") { _, _ -> onConfirm?.invoke() }
            .setNegativeButton("Отмена", null)
            .create()
    }
}
