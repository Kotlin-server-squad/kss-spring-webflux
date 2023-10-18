package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.Customer
import io.kotest.common.runBlocking
import kotlinx.coroutines.flow.toList
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(CustomerRepositoryTest::class.java)

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @AfterEach
    fun tearDown() = runBlocking {
        logger.info("Deleting all customers, affected rows: {}", customerRepository.deleteAll())
        assertTrue(
            customerRepository.findAll().toList().isEmpty(),
            "Not all customers were deleted"
        )
    }

    @Test
    fun `should save a new customer`() = runBlocking {
        val customer = customerRepository.save(
            customer(
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
            customer(
                "John Doe",
                "john.doe@gmail.com"
            )
        )
        val updatedCustomer = customerRepository.save(
            customer.copy(name = "Jane Doe", email = "jane.doe@gmail.com")
        )
        assertEquals(updatedCustomer.id, customer.id, "Customer ID should not change")
        val foundCustomer = updatedCustomer.id?.let {
            customerRepository.findById(it)
        } ?: fail("Customer not found")
        assertEquals(updatedCustomer, foundCustomer, "Customer should be updated")
    }

    @Test
    fun `should find a customer by id`() = runBlocking {
        createAndSaveCustomers().forEach { customer ->
            val foundCustomer = customer.id?.let {
                customerRepository.findById(it)
            } ?: fail("Customer not found")
            assertEquals(customer, foundCustomer, "Customer should be found")
        }
    }

    @Test
    fun `should find all customers`() = runBlocking {
        val customers = createAndSaveCustomers()
        assertEquals(customers, customerRepository.findAll().toList(), "Customers should be found")
    }

    @Test
    fun `should delete a customer`() = runBlocking {
        val customer = customerRepository.save(
            customer(
                "John Toe",
                "john.toe@gmail.com"
            )
        )
        assertTrue(customerRepository.delete(customer), "Customer should be deleted")
        assertNull(customerRepository.findById(customer.id!!), "Customer should not be found")
    }

    private fun customer(name: String, email: String): Customer {
        return Customer(null, name, email)
    }

    private suspend fun createAndSaveCustomers(): List<Customer> {
        val customers = listOf(
            customer("John Doe", "john.doe@gmail.com"),
            customer("Jane Doe", "jane.doe@gmail.com"),
            customer("Tomas Test", "tomas@test.com"),
        ).map { customerRepository.save(it) }
        assertTrue(customers.isNotEmpty(), "Customers should not be empty")
        assertTrue(customers.all { it.id != null }, "All customers should be saved")
        return customers
    }

}