package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.Customer
import io.kotest.common.runBlocking
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun init(@Autowired flyway: Flyway) {
            flyway.migrate()
        }
    }

    @Autowired
    private lateinit var customerRepository: CustomerRepository


    @Test
    fun `should save a new customer`() = runBlocking {
        val customer = customerRepository.save(
            Customer(
                null,
                "John Doe",
                "john.doe@gmail.com"
            )
        )
        assertNotNull(customer.id, "Customer ID should not be null")
        assertEquals("John Doe", customer.name)
        assertEquals("john.doe@gmail.com", customer.email)
    }

    @Test
    fun `should update an existing customer`() = runBlocking {
        val customer = customerRepository.save(
            Customer(
                null,
                "John Doe",
                "john.doe@gmail.com"
            )
        )
        val updatedCustomer = customerRepository.save(
            customer.copy(name = "Jane Doe", email = "jane.doe@gmail.com")
        )
        assertEquals(updatedCustomer.id, customer.id, "Customer ID should not change")
    }

}