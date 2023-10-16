package cz.kss.proj.clientservice.service.impl

import cz.kss.proj.clientservice.dto.CreateCustomerDto
import cz.kss.proj.clientservice.dto.CustomerDto
import cz.kss.proj.clientservice.mapper.toDto
import cz.kss.proj.clientservice.mapper.toEntity
import cz.kss.proj.clientservice.repository.CustomerRepository
import cz.kss.proj.clientservice.service.CustomerService
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val logger: Logger,
) : CustomerService {
    override suspend fun createCustomer(customerDto: CreateCustomerDto): CustomerDto {
        return customerRepository.save(customerDto.toEntity()).toDto()
    }

    override suspend fun getCustomer(customerId: Long) {
//        val reactorContext = coroutineContext[ReactorContext]?.context ?: Context.empty()
//        val traceId = reactorContext.getOrDefault(TracingWebFilter.TRACE_ID, "UnknownTraceId")
//        val spanId = reactorContext.getOrDefault(TracingWebFilter.SPAN_ID, "UnknownSpanId")

        customerRepository.findById(customerId)?.let {
            logger.info("customer $it")
        }
    }

    override suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CustomerDto> {
        TODO("Not yet implemented")
    }

}