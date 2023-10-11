package cz.kss.proj.clientservice.repository

import cz.kss.proj.clientservice.entity.Customer
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CoroutineCrudRepository<Customer,Long> {
}