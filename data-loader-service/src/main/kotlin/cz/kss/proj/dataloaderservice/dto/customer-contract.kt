package cz.kss.proj.dataloaderservice.dto

data class Customer(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val address: Address,
    val email: String,
    val additionalInformation: AdditionalInformation,
)

data class Address(
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)

data class AdditionalInformation(
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val gender: Gender,
)

enum class Gender {
    MALE, FEMALE, NON
}
