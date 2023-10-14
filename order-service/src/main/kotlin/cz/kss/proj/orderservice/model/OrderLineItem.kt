package cz.kss.proj.orderservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("order_line_item")
data class OrderLineItem(
    @Id
    val id: Long,
    val count: Int,
    val itemPrice: BigDecimal,
    val itemPriceSum: BigDecimal,
//    val item: Item,
    val order: Order,
    val createdAt: Instant,
    val updatedAt: Instant,
)