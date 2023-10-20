package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.BaseEntity
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec
import org.springframework.r2dbc.core.awaitRowsUpdated

interface BaseRepository<ENTITY : BaseEntity<ENTITY_ID>, ENTITY_ID> {
    fun findAll(): Flow<ENTITY>
    suspend fun findById(id: ENTITY_ID): ENTITY?
    suspend fun save(entity: ENTITY): ENTITY
    suspend fun delete(entity: ENTITY): Boolean
    suspend fun deleteAll(): Boolean

    @Suppress("UNCHECKED_CAST")
    suspend fun insert(entity: ENTITY, f: () -> GenericExecuteSpec): ENTITY {
        f().filter { statement: Statement, _ ->
            statement.returnGeneratedValues("id").execute()
        }.fetch().first().doOnNext { row ->
            entity.id = row["id"] as ENTITY_ID
        }.awaitSingleOrNull()
        return entity
    }

    suspend fun update(entity: ENTITY, f: () -> GenericExecuteSpec): ENTITY {
        return entity(f) ?: entity
    }

    suspend fun delete(f: () -> GenericExecuteSpec): Boolean {
        return f().fetch().awaitRowsUpdated() > 0
    }

    suspend fun entity(f: () -> GenericExecuteSpec): ENTITY? {
        return f().fetch().first().mapNotNull { toEntity(it) }.awaitSingleOrNull()
    }

    fun entities(f: () -> Flow<MutableMap<String, Any>>): Flow<ENTITY> {
        return f().mapNotNull { toEntity(it) }
    }

    fun toEntity(row: MutableMap<String, Any>): ENTITY?

    @Suppress("UNCHECKED_CAST")
    suspend fun <RELATED_ENTITY : BaseEntity<RELATED_ENTITY_ID>, RELATED_ENTITY_ID> toEntityWithRelationship(
        rows: List<MutableMap<String, Any>>,
        relatedIdColumn: String,
        relatedEntitiesF: suspend (RELATED_ENTITY_ID) -> RELATED_ENTITY?,
        withRelatedEntitiesF: (ENTITY, List<RELATED_ENTITY>) -> ENTITY
    ): ENTITY? {
        val entity = toEntity(rows.first()) ?: return null
        val relatedEntities = rows.mapNotNull { row ->
            if (row[relatedIdColumn] == null) {
                null
            } else {
                relatedEntitiesF(row[relatedIdColumn] as RELATED_ENTITY_ID)
            }
        }
        return if (relatedEntities.isEmpty()) entity else withRelatedEntitiesF(entity, relatedEntities)
    }
}
