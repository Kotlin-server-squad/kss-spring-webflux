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
    companion object {
        private const val SELECT_ALL = "SELECT c.id, c.first_name, c.last_name, c.email, c.created_at, c.updated_at FROM customer c"
    }
    override fun findAll(): Flow<Customer> {
        return entities {
            dbClient.sql(
                """
                    $SELECT_ALL
                """.trimIndent()
            ).fetch().flow()
        }
    }

    override suspend fun findById(id: Long): Customer? {
        return entity {
            dbClient.sql(
                """
                    $SELECT_ALL WHERE c.id = :id
                """.trimIndent()
            ).bind("id", id)
        }
    }

    override suspend fun save(entity: Customer): Customer {
        return entity.id?.let { id ->
            update(entity) {
                dbClient.sql(
                    """
                        UPDATE customer c SET c.first_name = :first_name, c.last_name = :last_name,  c.email = :email WHERE id = :id
                    """.trimIndent()
                )
                    .bind("id", id)
                    .bind("first_name", entity.firstName)
                    .bind("last_name", entity.lastName)
                    .bind("email", entity.email)
            }
        } ?: run {
            insert(entity) {
                dbClient.sql(
                    """
                        INSERT INTO customer (first_name, last_name, email) VALUES (:first_name, :last_name, :email)
                    """.trimIndent()
                )
                    .bind("first_name", entity.firstName)
                    .bind("last_name", entity.lastName)
                    .bind("email", entity.email)
            }
        }
    }

    override suspend fun delete(entity: Customer): Boolean {
        return entity.id?.let { id ->
            delete {
                dbClient.sql(
                    """
                        DELETE FROM customer WHERE id = :id
                    """.trimIndent()
                )
                    .bind("id", id)
            }
        } ?: false
    }

    override suspend fun deleteAll(): Boolean {
        return delete {
            dbClient.sql(
                """
                    DELETE FROM customer
                """.trimIndent()
            )
        }
    }

    override fun    toEntity(row: MutableMap<String, Any>): Customer? {
        return if (
            (row["id"] == null || row["id"] !is Long) ||
            (row["first_name"] == null) ||
            (row["last_name"] == null) ||
            (row["email"] == null)
        ) {
            null
        } else {
            Customer(
                id = row["id"] as Long,
                firstName = row["first_name"] as String,
                lastName = row["last_name"] as String,
                email = row["email"] as String,
                createdAt = row.toMaybeInstant ("created_at"),
                updatedAt = row.toMaybeInstant("updated_at")
            )
        }
    }
}


