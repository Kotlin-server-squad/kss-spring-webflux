package cz.kss.proj.orderservice.repository

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun MutableMap<String, Any>.toMaybeInstant(column: String): Instant? =
    (this[column] as LocalDateTime?)?.toInstant(ZoneOffset.UTC)