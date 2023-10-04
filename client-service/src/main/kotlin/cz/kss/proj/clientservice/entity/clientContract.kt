package cz.kss.proj.clientservice.entity

data class Client(
    val userName: String,
    val firstName: String,
    val lastName: String,

)

data class Address(
    val zipCode: Int,
)
