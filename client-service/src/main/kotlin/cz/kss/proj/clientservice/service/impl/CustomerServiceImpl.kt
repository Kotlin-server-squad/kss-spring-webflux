package cz.kss.proj.clientservice.service.impl

import cz.kss.proj.clientservice.dto.CreateCustomerDto
import cz.kss.proj.clientservice.mapper.toDto
import cz.kss.proj.clientservice.mapper.toEntity
import cz.kss.proj.clientservice.repository.AdditionalInformationRepository
import cz.kss.proj.clientservice.repository.AddressRepository
import cz.kss.proj.clientservice.repository.CustomerRepository
import cz.kss.proj.clientservice.service.CustomerService
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository,
    private val additionalInformationRepository: AdditionalInformationRepository,
) : CustomerService {
    override suspend fun createCustomer(customerDto: CreateCustomerDto): CreateCustomerDto {
       return customerRepository.save(customerDto.toEntity()).toDto()
    }

    override suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CreateCustomerDto> {
        TODO("Not yet implemented")
    }

}