package cz.kss.proj.orderservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("item")
data class Item(
    @Id
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val count: Int,
    val deleted: Boolean,
    val itemCategory: ItemCategory,
    val orderLineItems: List<OrderLineItem>,
    val createdAt: Instant,
    val updatedAt: Instant,
    )

@Table("item_category")
data class ItemCategory(
    @Id
    val id: Long,
    val category: String,
    val description: String,
    val items: List<Item>,
    val createdAt: Instant,
    val updatedAt: Instant,
)