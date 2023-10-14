package cz.kss.proj.clientservice.dto


data class CreateCustomerDto(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val address: AddressDto,
    val email: String,
    val additionalInformation: AdditionalInformationDto,
)

data class AddressDto(
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)

data class AdditionalInformationDto(
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val gender: GenderDto,
)

enum class GenderDto {
    MALE, FEMALE, NON
}
