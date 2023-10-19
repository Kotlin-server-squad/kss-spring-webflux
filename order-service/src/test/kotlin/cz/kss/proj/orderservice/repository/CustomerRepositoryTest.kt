package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.Customer
import cz.kss.proj.orderservice.verifySavedEntity
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
                "John",
                "Doe",
                "john.doe@gmail.com"
            )
        )
        assertNotNull(customer.id, "Customer ID should not be null")
        assertEquals("John", customer.firstName)
        assertEquals("Doe", customer.lastName)
        assertEquals("john.doe@gmail.com", customer.email)
    }

    @Test
    fun `should update an existing customer`() = runBlocking {
        val customer = customerRepository.save(
            customer(
                "John",
                "Doe",
                "john.doe@gmail.com"
            )
        )
        val updatedCustomer = customerRepository.save(
            customer.copy(firstName = "Jane", lastName = "Doe", email = "jane.doe@gmail.com")
        )
        assertEquals(updatedCustomer.id, customer.id, "Customer ID should not change")
        val foundCustomer = updatedCustomer.id?.let {
            customerRepository.findById(it)
        } ?: fail("Customer not found")
        verifySavedEntity(updatedCustomer, foundCustomer)
    }

    @Test
    fun `should find a customer by id`() = runBlocking {
        createAndSaveCustomers().forEach { customer ->
            val foundCustomer = customer.id?.let {
                customerRepository.findById(it)
            } ?: fail("Customer not found")
            assertEquals(customer.id, foundCustomer.id, "Customer ID should match")
            assertEquals(customer.firstName, foundCustomer.firstName, "Customer first name should match")
            assertEquals(customer.lastName, foundCustomer.lastName, "Customer last name should match")
            assertEquals(customer.email, foundCustomer.email, "Customer email should match")
            assertNotNull(foundCustomer.createdAt, "Customer created_at should not be null")
            assertNotNull(foundCustomer.updatedAt, "Customer updated_at should not be null")
        }
    }

    @Test
    fun `should find all customers`() = runBlocking {
        val customers = createAndSaveCustomers()
        val foundCustomers = customerRepository.findAll().toList()
        assertEquals(customers.size, foundCustomers.size, "Number of customers should match")
        customers.forEach { customer ->
            val foundCustomer = foundCustomers.find { it.email == customer.email }
            assertNotNull(foundCustomer, "Customer should be found")
            verifySavedEntity(customer, foundCustomer!!)
        }
    }

    @Test
    fun `should delete a customer`() = runBlocking {
        val customer = customerRepository.save(
            customer(
                "John",
                "Toe",
                "john.toe@gmail.com"
            )
        )
        assertTrue(customerRepository.delete(customer), "Customer should be deleted")
        assertNull(customerRepository.findById(customer.id!!), "Customer should not be found")
    }

    private fun customer(firstName: String, lastName: String, email: String): Customer {
        return Customer(null, firstName, lastName, email)
    }

    private suspend fun createAndSaveCustomers(): List<Customer> {
        val customers = listOf(
            customer("John", "Doe", "john.doe@gmail.com"),
            customer("Jane", "Doe", "jane.doe@gmail.com"),
            customer("Tomas", "Test", "tomas@test.com"),
        ).map { customerRepository.save(it) }
        assertTrue(customers.isNotEmpty(), "Customers should not be empty")
        assertTrue(customers.all { it.id != null }, "All customers should be saved")
        return customers
    }

}