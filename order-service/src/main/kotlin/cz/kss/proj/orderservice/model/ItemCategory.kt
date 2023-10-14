package cz.kss.proj.orderservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

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