package no.nav.medlemskap.clients

import io.github.resilience4j.kotlin.retry.executeSuspendFunction
import io.github.resilience4j.retry.Retry
import io.micrometer.core.instrument.Timer
import kotlinx.coroutines.CancellationException
import mu.KotlinLogging
import no.nav.medlemskap.common.clientCounter
import no.nav.medlemskap.common.clientTimer

private val logger = KotlinLogging.logger { }

suspend fun <T> runWithRetryAndMetrics(service: String, operation: String, retry: Retry?, block: suspend () -> T): T {
    try {
        retry?.let {
            return it.executeSuspendFunction {
                runWithMetrics(service, operation, block)
            }
        }
        return runWithMetrics(service, operation, block)
    } catch (jce: CancellationException) {
        logger.info("Kall mot $service:$operation kanselleres pga feil i kall mot annet baksystem", jce)
        throw jce
    } catch (t: Throwable) {
        logger.warn("Feilet under kall mot $service:$operation : ${t.message}", t)
        throw t
    }
}

suspend fun <T> runWithMetrics(service: String, operation: String, block: suspend () -> T): T {
    var timer: Timer.Sample? = null
    try {
        timer = Timer.start()
    } catch (t: Throwable) {
        logger.warn("Feilet under opprettelsen av timer for $service:$operation", t)
    }
    try {
        val result: T = block.invoke()
        try {
            clientCounter(service, operation, "success").increment()
        } catch (t: Throwable) {
            logger.warn("Feilet under inkrementinger av counter for $service:$operation", t)
        }
        return result
    } catch (t: Throwable) {
        try {
            clientCounter(service, operation, "failure").increment()
        } catch (t: Throwable) {
            logger.warn("Feilet under inkrementinger av counter for $service:$operation", t)
        }
        throw t
    } finally {
        try {
            timer?.let { it.stop(clientTimer(service, operation)) }
        } catch (t: Throwable) {
            logger.warn("Feilet under loggingen av timer for $service:$operation", t)
        }
    }
}
