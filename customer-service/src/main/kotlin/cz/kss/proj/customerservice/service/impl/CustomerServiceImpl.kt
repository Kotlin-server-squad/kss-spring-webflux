package cz.kss.proj.customerservice.service.impl

import cz.kss.proj.customerservice.config.context.CorrelationIdContext
import cz.kss.proj.customerservice.config.context.TraceIdContext
import cz.kss.proj.customerservice.dto.CreateCustomerDto
import cz.kss.proj.customerservice.dto.CustomerDto
import cz.kss.proj.customerservice.mapper.toDto
import cz.kss.proj.customerservice.mapper.toEntity
import cz.kss.proj.customerservice.repository.CustomerRepository
import cz.kss.proj.customerservice.service.CustomerService
import org.slf4j.Logger
import org.springframework.stereotype.Service
import kotlin.coroutines.coroutineContext

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val logger: Logger,
) : CustomerService {
    override suspend fun createCustomer(customerDto: CreateCustomerDto): CustomerDto {
        return customerRepository.save(customerDto.toEntity()).toDto()
    }

    override suspend fun getCustomer(customerId: Long) {
        logger.info("Customer id $customerId")
        customerRepository.findById(customerId)?.let {
            val traceId = coroutineContext[TraceIdContext]
            val correlationIdContext = coroutineContext[CorrelationIdContext]
            logger.info("traceId $traceId correlationId $correlationIdContext customer $it")
        }
    }

    override suspend fun createBatchCustomer(customers: List<CreateCustomerDto>): List<CustomerDto> {
        TODO("Not yet implemented")
    }

}