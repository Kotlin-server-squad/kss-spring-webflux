package cz.kss.proj.customerservice.handler

import cz.kss.proj.customerservice.dto.CreateCustomerDto
import cz.kss.proj.customerservice.dto.GetCustomerRequest
import cz.kss.proj.customerservice.service.CustomerService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class CustomerHandler(
    private val customerService: CustomerService
) {

    suspend fun createCustomer(req: ServerRequest): ServerResponse {

        val request = req.awaitBodyOrNull<CreateCustomerDto>()
        return request?.let {
            val createCustomer = customerService.createCustomer(request)
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(createCustomer)
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

    suspend fun getCustomer(req: ServerRequest): ServerResponse {
        val request = req.awaitBodyOrNull<GetCustomerRequest>()
        return request?.let {
            val createCustomer = customerService.getCustomer(it.customerId)
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(createCustomer)
        } ?: ServerResponse.badRequest().buildAndAwait()
    }
}