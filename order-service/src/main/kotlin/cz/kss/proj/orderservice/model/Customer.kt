package cz.kss.proj.orderservice.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("customer")
data class Customer(
    override var id: Long?,
    @Column("first_name") val firstName: String,
    @Column("last_name") val lastName: String,
    val email: String,
    @Column("created_at") override var createdAt: Instant? = null,
    @Column("updated_at") override var updatedAt: Instant? = null,
    val orders: List<Order> = emptyList()
) : BaseEntity<Long>