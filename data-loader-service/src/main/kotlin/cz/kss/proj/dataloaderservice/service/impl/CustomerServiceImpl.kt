package cz.kss.proj.dataloaderservice.service.impl

import cz.kss.proj.dataloaderservice.dto.Customer
import cz.kss.proj.dataloaderservice.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class CustomerServiceImpl(
    private val customerWebClient: WebClient
) : CustomerService {
    override suspend fun createCustomer(customer: Customer) {
        customerWebClient.post()
            .uri("/api/customer")
            .bodyValue(customer)
            .awaitExchange { clientResponse ->
                if (clientResponse.statusCode() == HttpStatus.OK) {
                    println("Request was successful")
                } else {
                    println("Failed with status: ${clientResponse.statusCode()}")
                }
            }
    }
}