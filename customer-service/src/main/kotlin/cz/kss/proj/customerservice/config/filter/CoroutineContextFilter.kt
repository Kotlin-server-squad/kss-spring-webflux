package cz.kss.proj.customerservice.config.filter

import cz.kss.proj.customerservice.config.context.CorrelationIdContext
import cz.kss.proj.customerservice.config.context.TraceIdContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import kotlin.coroutines.CoroutineContext

@Component
    class CoroutineContextFilter(private val logger: Logger, private val defaultCoroutineContext: CoroutineContext) : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        val correlationId = exchange.request.headers["x-correlation-id"]?.firstOrNull() ?: "requestIdNotProvided"
        val traceId = exchange.request.headers["x-trace-id"]?.firstOrNull() ?: "requestIdNotProvided"

        MDC.put(TraceIdKey, traceId)
        MDC.put(CorrelationIdKey, correlationId)
        val traceIdContext = TraceIdContext(traceId)
        val correlationIdContext = CorrelationIdContext(correlationId)

        withContext(defaultCoroutineContext + MDCContext().plus(traceIdContext).plus(correlationIdContext) ) {
            chain.filter(exchange)
        }
    }

    companion object {
        private const val TraceIdKey: String = "traceId"
        private const val CorrelationIdKey: String = "correlationId"
    }
}