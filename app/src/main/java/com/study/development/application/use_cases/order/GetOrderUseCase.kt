package com.study.development.application.use_cases.orders

import com.study.development.domain.ports.inbound.OrderPort
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val orderPort: OrderPort
) {
    suspend operator fun invoke() = orderPort.getOrders()
}
