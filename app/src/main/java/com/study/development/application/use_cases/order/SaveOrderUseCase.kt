package com.study.development.application.use_cases.orders

import com.study.development.domain.entities.CartItem
import com.study.development.domain.ports.inbound.OrderPort
import javax.inject.Inject

class SaveOrderUseCase @Inject constructor(
    private val orderPort: OrderPort
) {
    suspend operator fun invoke(items: List<CartItem>, total: Double) =
        orderPort.saveOrder(items, total)
}
