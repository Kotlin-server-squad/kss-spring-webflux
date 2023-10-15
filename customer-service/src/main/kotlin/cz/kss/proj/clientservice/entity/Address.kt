package cz.kss.proj.clientservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("address")
data class Address(
    @Id
    var id: Long? = null,
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)