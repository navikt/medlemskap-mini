package no.nav.medlemskap.common

import io.ktor.client.engine.*
import io.ktor.server.application.*
import org.slf4j.MDC
import java.util.*

const val MDC_CALL_ID = "Nav-Call-Id"

val callIdGenerator: ThreadLocal<String> = ThreadLocal.withInitial {
    UUID.randomUUID().toString()
}

class CorrelationId(private val id: String) {
    override fun toString(): String = id
}

internal fun getCorrelationId(): CorrelationId {
    if (MDC.get(MDC_CALL_ID) == null) {
        return CorrelationId(callIdGenerator.get())
    }
    return CorrelationId(MDC.get(MDC_CALL_ID))
}
internal fun getCorrelationId(callId: String?): CorrelationId {
    if (callId == null) {
        return CorrelationId(callIdGenerator.get())
    }
    return CorrelationId(callId)
}
