package cz.kss.proj.orderservice.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec

interface BaseRepository<T, I> {
    fun findAll(): Flow<T>
    suspend fun findById(id: I): T?
    suspend fun save(entity: T): T
    suspend fun delete(entity: T): Boolean
    suspend fun deleteAll(): Boolean

    suspend fun entity(f: () -> GenericExecuteSpec): T? {
        return f().fetch().one().mapNotNull { toEntity(it) }.awaitSingle()
    }

    fun entities(f: () -> Flow<MutableMap<String, Any>>): Flow<T> {
        return f().mapNotNull { toEntity(it) }
    }

    fun toEntity(row: MutableMap<String, Any>): T?
}
