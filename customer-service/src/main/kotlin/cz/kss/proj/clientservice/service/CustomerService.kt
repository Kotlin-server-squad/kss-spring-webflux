package cz.kss.proj.clientservice.service

import cz.kss.proj.clientservice.dto.CreateCustomerDto
import cz.kss.proj.clientservice.dto.CustomerDto

interface CustomerService {
    suspend fun createCustomer(customerDto: CreateCustomerDto): CustomerDto
    suspend fun getCustomer(customerId: Long)
    suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CustomerDto>
}