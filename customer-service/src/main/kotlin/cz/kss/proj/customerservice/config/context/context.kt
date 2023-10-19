package cz.kss.proj.customerservice.config.context

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class TraceIdContext(val traceId: String) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<TraceIdContext>
}

data class CorrelationIdContext(val correlationId: String) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<CorrelationIdContext>
}


