package cz.kss.proj.clientservice.mapper

import cz.kss.proj.clientservice.dto.*
import cz.kss.proj.clientservice.entity.AdditionalInformation
import cz.kss.proj.clientservice.entity.Address
import cz.kss.proj.clientservice.entity.Customer
import cz.kss.proj.clientservice.entity.Gender


fun CreateCustomerDto.toEntity(): Customer = Customer(
    id = null,
    userName = this.userName,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    address = this.address.toEntity(),
    additionalInformation = this.additionalInformation.toEntity()
)

fun CreateAddressDto.toEntity(): Address = Address(
    id = null,
    zipCode = this.zipCode,
    street = this.street,
    town = this.town,
    country = this.country,
    region = this.region
)

fun CreateAdditionalInformationDto.toEntity(): AdditionalInformation = AdditionalInformation(
    id = null,
    birthDay = this.birthDay,
    birthMonth = this.birthMonth,
    birthYear = this.birthYear,
    gender = this.gender.toEntity()
)

fun GenderDto.toEntity(): Gender = when (this) {
    GenderDto.MALE -> Gender.MALE
    GenderDto.FEMALE -> Gender.FEMALE
    GenderDto.NON -> Gender.FEMALE
}

fun Customer.toDto(): CustomerDto = CustomerDto(
    id = this.id,
    userName = this.userName, firstName = this.firstName, lastName = this.lastName,
    address = this.address!!.toDto(),
    email = this.email,
    additionalInformation = this.additionalInformation!!.toDto()
)

fun Address.toDto(): AddressDto = AddressDto(
    id = this.id,
    zipCode = this.zipCode,
    street = this.street,
    town = this.town,
    country = this.country,
    region = this.region,
)

fun AdditionalInformation.toDto(): AdditionalInformationDto = AdditionalInformationDto(
    id = this.id,
    birthDay = this.birthDay,
    birthMonth = this.birthMonth,
    birthYear = this.birthYear,
    gender = this.gender.toDto()
)

fun Gender.toDto(): GenderDto = when (this) {
    Gender.MALE -> GenderDto.MALE
    Gender.FEMALE -> GenderDto.FEMALE
    Gender.NON -> GenderDto.NON
}



