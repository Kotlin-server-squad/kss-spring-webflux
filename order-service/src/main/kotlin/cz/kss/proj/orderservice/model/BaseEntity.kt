package cz.kss.proj.orderservice.model

import java.time.Instant

interface BaseEntity<I> {
    var id: I?
    var createdAt: Instant?
    var updatedAt: Instant?
}