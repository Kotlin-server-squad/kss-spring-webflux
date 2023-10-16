package cz.kss.proj.clientservice.repository

import cz.kss.proj.clientservice.entity.AdditionalInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
interface AdditionalInformationRepository : BaseRepository<AdditionalInformation, Long>

@Repository
class AdditionalInformationRepositoryImpl(
    private val dbClient: DatabaseClient

) : AdditionalInformationRepository {
    override fun findAll(): Flow<AdditionalInformation> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Long): AdditionalInformation? {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: AdditionalInformation): AdditionalInformation {
        return entity.id?.let { id ->
            val t = dbClient.sql(
                """
                        UPDATE additional_information SET birth_day = :birth_day,birth_month = :birth_month,birth_year = :birth_year, gender = :gender WHERE id = :id
                    """.trimIndent()
            )
                .bind("birth_day", entity.birthDay)
                .bind("birth_month", entity.birthMonth)
                .bind("birth_year", entity.birthYear)
                .bind("gender", entity.gender)
                .fetch().first()
                .awaitSingle()
            toEntity(t)
        } ?: run {
            dbClient.sql(
                """
                        INSERT INTO additional_information (birth_day, birth_month,birth_year,gender) VALUES (:birth_day, :birth_month,:birth_year,:gender)
                    """.trimIndent()
            )
                .bind("birth_day", entity.birthDay)
                .bind("birth_month", entity.birthMonth)
                .bind("birth_year", entity.birthYear)
                .bind("gender", entity.gender.toString())
                .filter { statement -> statement.returnGeneratedValues("id") }
                .fetch().one()
                .doOnNext { entity.id = it["id"] as Long }
                .awaitSingle()
            entity
        } ?: throw IllegalStateException("Entity not saved")
    }


    override suspend fun delete(entity: AdditionalInformation): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override fun toEntity(row: MutableMap<String, Any>): AdditionalInformation? {
        TODO("Not yet implemented")
    }
}