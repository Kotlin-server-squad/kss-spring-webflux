package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.Order
import cz.kss.proj.orderservice.model.OrderStatus
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository
import java.math.BigDecimal

interface OrderRepository : BaseRepository<Order, Long> {
    // any custom methods here
}

@Repository
class OrderRepositoryImpl : OrderRepository {
    override fun findAll(): Flow<Order> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Long): Order? {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Order): Order {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: Order): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override fun toEntity(row: MutableMap<String, Any>): Order? {
        return if (
            row["o_id"] == null
            || row["o_price_sum"] == null
            || row["o_status"] == null
            || row["o_customer_id"] == null
        ) {
            null
        } else {
            Order(
                id = row["o_id"] as Long,
                priceSum = BigDecimal.valueOf(row["o_price_sum"] as Double),
                status = OrderStatus.valueOf(row["o_status"] as String),
                boolean = false,
                customerId = row["o_customer_id"] as Long,
                orderLineItems = emptyList(),   // TODO
                createdAt = row.toMaybeInstant("o_created_at"),
                updatedAt = row.toMaybeInstant("o_updated_at")
            )
        }
    }
}