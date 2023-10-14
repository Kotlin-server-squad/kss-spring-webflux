package cz.kss.proj.clientservice.service

import cz.kss.proj.clientservice.dto.CreateCustomerDto

interface CustomerService {
    suspend fun createCustomer(customerDto: CreateCustomerDto): CreateCustomerDto

    suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CreateCustomerDto>
}