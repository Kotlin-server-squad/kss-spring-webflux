package cz.kss.proj.dataloaderservice.service

import cz.kss.proj.dataloaderservice.dto.Customer

interface CustomerService {
    suspend fun createCustomer(customer: Customer)
}