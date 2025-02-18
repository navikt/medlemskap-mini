package no.nav.medlemskap.common

import kotlinx.coroutines.asContextElement
import no.nav.medlemskap.domene.Ytelse
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

// For bruk i suspending functions
// https://blog.tpersson.io/2018/04/22/emulating-request-scoped-objects-with-kotlin-coroutines/
private class CoroutineRequestContext(
    val ytelse: Ytelse
) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<CoroutineRequestContext>
}

class RequestContext(
    val requestId: String
) : AbstractCoroutineContextElement(RequestContext) {
    companion object Key : CoroutineContext.Key<CoroutineRequestContext>
}

private fun CoroutineContext.requestContext() =
    get(CoroutineRequestContext.Key) ?: throw IllegalStateException("Request Context ikke satt.")
internal fun CoroutineContext.ytelse() = requestContext().ytelse

// For bruk i non suspending functions
// https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/coroutine-context-and-dispatchers.md#thread-local-data
class RequestContextService {

    private companion object {
        private val requestContexts = ThreadLocal<RequestContext>()
    }

    internal fun getCoroutineContext(
        context: CoroutineContext,
        ytelse: Ytelse
    ) = context + requestContexts.asContextElement(
        RequestContext(ytelse)
    ) + CoroutineRequestContext(ytelse)

    private fun getRequestContext() = requestContexts.get() ?: throw IllegalStateException("Request Context ikke satt.")
    internal fun getYtelse() = getRequestContext().ytelse

    internal data class RequestContext(
        val ytelse: Ytelse
    )
}
