package cz.kss.proj.customerservice.service

import cz.kss.proj.customerservice.dto.CreateCustomerDto
import cz.kss.proj.customerservice.dto.CustomerDto

interface CustomerService {
    suspend fun createCustomer(customerDto: CreateCustomerDto): CustomerDto
    suspend fun getCustomer(customerId: Long)
    suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CustomerDto>
}