package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.BaseEntity
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec
import org.springframework.r2dbc.core.awaitRowsUpdated

interface BaseRepository<T : BaseEntity<I>, I> {
    fun findAll(): Flow<T>
    suspend fun findById(id: I): T?
    suspend fun save(entity: T): T
    suspend fun delete(entity: T): Boolean
    suspend fun deleteAll(): Boolean

    @Suppress("UNCHECKED_CAST")
    suspend fun insert(entity: T, f: () -> GenericExecuteSpec): T {
        f().filter { statement: Statement, _ ->
            statement.returnGeneratedValues("id").execute()
        }.fetch().first().doOnNext { row ->
            entity.id = row["id"] as I
        }.awaitSingleOrNull()
        return entity
    }

    suspend fun update(entity: T, f: () -> GenericExecuteSpec): T {
        return entity(f) ?: entity
    }

    suspend fun delete(f: () -> GenericExecuteSpec): Boolean {
        return f().fetch().awaitRowsUpdated() > 0
    }

    suspend fun entity(f: () -> GenericExecuteSpec): T? {
        return f().fetch().first().mapNotNull { toEntity(it) }.awaitSingleOrNull()
    }

    fun entities(f: () -> Flow<MutableMap<String, Any>>): Flow<T> {
        return f().mapNotNull { toEntity(it) }
    }

    fun toEntity(row: MutableMap<String, Any>): T?
}
