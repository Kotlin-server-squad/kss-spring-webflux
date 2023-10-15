package cz.kss.proj.clientservice.repository

import cz.kss.proj.clientservice.entity.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepositoryT : BaseRepository<Customer, Long>

@Repository
class CustomerRepositoryTest(
    private val dbClient: DatabaseClient,
    private val addressRepositoryTest: AddressRepositoryTest,
    private val additionalInformationRepositoryTest: AdditionalInformationRepositoryTest,
) : CustomerRepositoryT {
    override fun findAll(): Flow<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun entity(f: () -> DatabaseClient.GenericExecuteSpec): Customer? {
        return super.entity(f)
    }

    override fun entities(f: () -> Flow<MutableMap<String, Any>>): Flow<Customer> {
        return super.entities(f)
    }

    override fun toEntity(row: MutableMap<String, Any>): Customer? {
        return if (
            (row["id"] == null || row["id"] !is Long) ||
            (row["user_name"] == null) ||
            (row["email"] == null)
        ) {
            null
        } else {
            Customer(
                id = row["id"] as Long,
                userName = row["user_name"] as String,
                firstName = row["first_name"] as String,
                lastName = row["last_name"] as String,
                email = row["email"] as String,
                additionalInformation = null,
                address = null
            )
        }
    }

    override suspend fun delete(entity: Customer): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Customer): Customer {
        return saveAdditionalInformation(saveAddress(this.saveCustomer(entity)))
    }

    suspend fun saveCustomer(entity: Customer): Customer {
        return entity.id?.let { id ->
            dbClient.sql(
                """
                        UPDATE customer SET user_name = :user_name,first_name = :first_name,last_name = :last_name, email = :email WHERE id = :id
                    """.trimIndent()
            )
                .bind("user_name", entity.userName)
                .bind("first_name", entity.firstName)
                .bind("last_name", entity.lastName)
                .bind("email", entity.email)
                .fetch().first()
                .mapNotNull { toEntity(it) }
                .awaitSingle()
        } ?: run {
            dbClient.sql(
                """
                        INSERT INTO customer (user_name, first_name,last_name,email) VALUES (:user_name,:first_name,:last_name, :email)
                    """.trimIndent()
            )
                .bind("user_name", entity.userName)
                .bind("first_name", entity.firstName)
                .bind("last_name", entity.lastName)
                .bind("email", entity.email)
                .filter { statement -> statement.returnGeneratedValues("id") }
                .fetch().one()
                .doOnNext { entity.id = it["id"] as Long }
                .thenReturn(entity)
                .awaitSingle()
        } ?: throw IllegalStateException("Entity not saved")
    }

    suspend fun saveAddress(entity: Customer): Customer {
        val t = addressRepositoryTest.save(entity.address!!)
        entity.address = t
        return entity
    }

    suspend fun saveAdditionalInformation(entity: Customer): Customer {
        val t = additionalInformationRepositoryTest.save(entity.additionalInformation!!)
        entity.additionalInformation = t
        return entity
    }

    override suspend fun findById(id: Long): Customer? {
        TODO("Not yet implemented")
    }
}