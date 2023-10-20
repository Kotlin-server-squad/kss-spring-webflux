package cz.kss.proj.orderservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("k_order")
data class Order(
    @Id
    override var id: Long?,
    @Column("priceSum") val priceSum: BigDecimal,
    @Column("status") val status: OrderStatus,
    val boolean: Boolean,
    val orderLineItems: List<OrderLineItem>,
    @Column("customerId") val customerId: Long,
    @Column("created_at") override var createdAt: Instant? = null,
    @Column("updated_at") override var updatedAt: Instant? = null,
) : BaseEntity<Long>

enum class OrderStatus {
    NEW, COMPLETED, CANCELED
}