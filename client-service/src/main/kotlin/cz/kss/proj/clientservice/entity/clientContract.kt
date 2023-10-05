package cz.kss.proj.clientservice.entity

import org.springframework.data.relational.core.mapping.Table

@Table
data class Client(
    val userName: String,
    val firstName: String,
    val lastName: String,

)

@Table
data class Address(
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)





