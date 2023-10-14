package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.Customer
import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository


@Repository
interface CustomerRepository : BaseRepository<Customer, Long> {
    // any custom methods here
}

@Repository
class CustomerRepositoryImpl(private val dbClient: DatabaseClient) : CustomerRepository {
    override fun findAll(): Flow<Customer> {
        return entities {
            dbClient.sql(
                """
                    SELECT c.id, c.name, c.email FROM customer c
                """.trimIndent()
            ).fetch().flow()
        }
    }

    override suspend fun findById(id: Long): Customer? {
        return entity {
            dbClient.sql(
                """
                    SELECT c.id, c.name, c.email FROM customer c WHERE c.id = :id
                """.trimIndent()
            ).bind("id", id)
        }
    }

    override suspend fun save(entity: Customer): Customer {
        return entity {
            entity.id?.let { id ->
                dbClient.sql(
                    """
                        UPDATE customer SET name = :name, email = :email WHERE id = :id
                    """.trimIndent()
                )
                    .bind("id", id)
                    .bind("name", entity.name)
                    .bind("email", entity.email)
            } ?: run {
                dbClient.sql(
                    """
                        INSERT INTO customer (name, email) VALUES (:name, :email)
                    """.trimIndent()
                )
                    .bind("name", entity.name)
                    .bind("email", entity.email)
            }
        } ?: throw IllegalStateException("Entity not saved")
    }

    override suspend fun delete(entity: Customer): Boolean {
        return entity.id?.let { id ->
            dbClient.sql(
                """
                    DELETE FROM customer WHERE id = :id
                """.trimIndent()
            )
                .bind("id", id)
            true
        } ?: false
    }

    override suspend fun deleteAll(): Boolean {
        dbClient.sql("""
            DELETE FROM customer
        """.trimIndent())
        return true
    }

    override fun toEntity(row: MutableMap<String, Any>): Customer? {
        return if (
            (row["id"] == null || row["id"] !is Long) ||
            (row["name"] == null) ||
            (row["email"] == null)
        ) {
            null
        } else {
            Customer(
                id = row["id"] as Long,
                name = row["name"] as String,
                email = row["email"] as String
            )
        }
    }
}


