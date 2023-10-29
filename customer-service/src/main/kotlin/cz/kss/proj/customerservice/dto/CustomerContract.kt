package cz.kss.proj.customerservice.dto



data class GetCustomerRequest(
    val customerId: Long,
)




data class CreateCustomerDto(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val address: CreateAddressDto,
    val email: String,
    val additionalInformation: CreateAdditionalInformationDto,
)

data class CreateAddressDto(
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)

data class CreateAdditionalInformationDto(
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val gender: GenderDto,
)

data class CustomerDto(
    val id: Long?,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val address: AddressDto,
    val email: String,
    val additionalInformation: AdditionalInformationDto,
)

data class AddressDto(
    val id: Long?,
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
)

data class AdditionalInformationDto(
    val id: Long?,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val gender: GenderDto,
)



enum class GenderDto {
    MALE, FEMALE, NON
}
