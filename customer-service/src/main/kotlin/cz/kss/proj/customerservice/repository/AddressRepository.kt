package cz.kss.proj.customerservice.repository

import cz.kss.proj.customerservice.entity.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository: BaseRepository<Address,Long>

@Repository
class AddressRepositoryImpl(
    private val dbClient: DatabaseClient
): AddressRepository{
    override fun findAll(): Flow<Address> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Long): Address? {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Address): Address {
        return entity.id?.let { id ->
              val t =  dbClient.sql(
                    """
                        UPDATE address SET zip_code = :zip_code,street = :street,town = :town, country = :country, region = :region id = :id
                    """.trimIndent()
                )
                    .bind("zip_code", entity.zipCode)
                    .bind("street", entity.street)
                    .bind("town", entity.town)
                    .bind("country", entity.country)
                    .bind("region", entity.region)
                  .fetch().first()
                  .awaitSingle()
            toEntity(t)
            } ?: run {
                dbClient.sql(
                    """
                        INSERT INTO address (zip_code, street,town,country,region) VALUES (:zip_code, :street,:town,:country,:region)
                    """.trimIndent()
                )
                    .bind("zip_code", entity.zipCode)
                    .bind("street", entity.street)
                    .bind("town", entity.town)
                    .bind("country", entity.country)
                    .bind("region", entity.region)
                    .filter { statement -> statement.returnGeneratedValues("id") }
                    .fetch().one()
                    .doOnNext { entity.id = it["id"] as Long }
                    .awaitSingle()
            entity
        } ?: throw IllegalStateException("Entity not saved")
    }

    override suspend fun delete(entity: Address): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun entity(f: () -> DatabaseClient.GenericExecuteSpec): Address? {
        return super.entity(f)
    }

    override fun entities(f: () -> Flow<MutableMap<String, Any>>): Flow<Address> {
        return super.entities(f)
    }

    override fun toEntity(row: MutableMap<String, Any>): Address? {
        TODO("Not yet implemented")
    }
}