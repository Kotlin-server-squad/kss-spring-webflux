package cz.kss.proj.orderservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("k_order")
data class Order(
    @Id
    val id: Long,
    val priceSum: BigDecimal,
    val status: OrderStatus,
    val boolean: Boolean,
    val orderLineItems: List<OrderLineItem>,
    val customerId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
)

enum class OrderStatus {
    NEW, COMPLETED, CANCELED
}