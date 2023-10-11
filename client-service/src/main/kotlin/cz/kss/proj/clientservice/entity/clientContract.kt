package cz.kss.proj.clientservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("customer")
data class Customer(
    @Id
    val id: Long,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val address: Address,
    val email: String,
    val additionalInformation: AdditionalInformation,
)

@Table("address")
data class Address(
    @Id
    val id: Long,
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)

@Table("additional_information")
data class AdditionalInformation(
    @Id
    val id: Long,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val gender: Gender,
)

enum class Gender{
    MALE,FEMALE,NON
}



