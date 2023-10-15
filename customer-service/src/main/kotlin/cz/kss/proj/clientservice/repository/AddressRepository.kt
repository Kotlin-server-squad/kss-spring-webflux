package cz.kss.proj.clientservice.repository

import cz.kss.proj.clientservice.entity.Address
import cz.kss.proj.clientservice.entity.Customer
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository: CoroutineCrudRepository<Address,Long> {
}