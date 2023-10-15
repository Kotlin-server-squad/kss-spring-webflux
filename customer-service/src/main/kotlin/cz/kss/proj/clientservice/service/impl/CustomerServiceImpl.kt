package cz.kss.proj.clientservice.service.impl

import cz.kss.proj.clientservice.dto.CreateCustomerDto
import cz.kss.proj.clientservice.dto.CustomerDto
import cz.kss.proj.clientservice.mapper.toDto
import cz.kss.proj.clientservice.mapper.toEntity
import cz.kss.proj.clientservice.repository.AdditionalInformationRepository
import cz.kss.proj.clientservice.repository.AddressRepository
import cz.kss.proj.clientservice.repository.CustomerRepository
import cz.kss.proj.clientservice.repository.CustomerRepositoryT
import cz.kss.proj.clientservice.service.CustomerService
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepositoryT: CustomerRepositoryT,
    private val logger: Logger,
) : CustomerService {
    //    override suspend fun createCustomer(customerDto: CreateCustomerDto): CreateCustomerDto {
//
//        val addressSaved = addressRepository.save(customerDto.address.toEntity())
//        val additionalInformationSaved =
//            additionalInformationRepository.save(customerDto.additionalInformation.toEntity())
//
//        val customerSaved =
//            customerRepository.save(customerDto.toEntity(addressSaved.id!!, additionalInformationSaved.id!!))
//        return CreateCustomerDto(
//            userName = customerSaved.userName,
//            firstName = customerSaved.firstName,
//            lastName = customerSaved.lastName,
//            address = addressSaved.toDto(),
//            email = customerSaved.email,
//            additionalInformation = additionalInformationSaved.toDto()
//        )
//    }
    override suspend fun createCustomer(customerDto: CreateCustomerDto): CustomerDto {
      return customerRepositoryT.save(customerDto.toEntity()).toDto()
    }

    override suspend fun getCustomer(customerId: Long) {
        customerRepositoryT.findById(customerId)?.let {
            logger.info("Customer $it")
        }
    }

    override suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CustomerDto> {
        TODO("Not yet implemented")
    }

}