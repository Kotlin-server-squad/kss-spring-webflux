package cz.kss.proj.clientservice.handler

import cz.kss.proj.clientservice.config.filter.TracingWebFilter
import cz.kss.proj.clientservice.dto.CreateCustomerDto
import cz.kss.proj.clientservice.dto.GetCustomerRequest
import cz.kss.proj.clientservice.service.CustomerService
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.util.context.Context
import kotlin.coroutines.coroutineContext

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