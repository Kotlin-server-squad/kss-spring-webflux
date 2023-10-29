package cz.kss.proj.customerservice.repository

import cz.kss.proj.customerservice.config.context.CorrelationIdContext
import cz.kss.proj.customerservice.config.context.TraceIdContext
import cz.kss.proj.customerservice.entity.Customer
import cz.kss.proj.customerservice.entity.customerFromRow
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.MDC
import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.kotlin.core.publisher.toMono
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


@Repository
interface CustomerRepository : BaseRepository<Customer, Long>

@Repository
class CustomerRepositoryImpl(
    private val dbClient: DatabaseClient,
    private val addressRepository: AddressRepository,
    private val additionalInformationRepository: AdditionalInformationRepository,
    private val logger: Logger,
    private val defaultCoroutineContext: CoroutineContext
) : CustomerRepository {
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
            (row["email"] == null) ||
            (row["first_name"] == null) ||
            (row["last_name"] == null)
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
        val t = addressRepository.save(entity.address!!)
        entity.address = t
        return entity
    }

    suspend fun saveAdditionalInformation(entity: Customer): Customer {
        val t = additionalInformationRepository.save(entity.additionalInformation!!)
        entity.additionalInformation = t
        return entity
    }

    override suspend fun findById(id: Long): Customer? {
        val traceIdContext = coroutineContext[TraceIdContext]
        val correlationIdContext = coroutineContext[CorrelationIdContext]
        logger.info("$traceIdContext $correlationIdContext find by id $id")
        val currentMdc = MDC.getCopyOfContextMap()

         return withContext(defaultCoroutineContext + MDCContext(currentMdc)) {
            dbClient.sql(GET_CUSTOMER_BY_ID)
                .bind("id", id)
                .fetch()
                .first()
                .flatMap { customerFromRow("c", it).toMono() }
                .awaitSingleOrNull()
        }
    }

    companion object{
        private const val GET_CUSTOMER_BY_ID="""
       select c.id c_id,c.user_name,c.last_name,c.first_name,c.email,
       ai.id ai_id,ai.birth_day,ai.birth_month,ai.birth_year,ai.gender,
       a.id a_id,a.country,a.region,a.street,a.town,a.zip_code
from customer c
         left join additional_information ai on ai.id = c.additional_information_id
         left outer join address a on a.id = c.address_id
where c.id = :id;
        """
    }
}