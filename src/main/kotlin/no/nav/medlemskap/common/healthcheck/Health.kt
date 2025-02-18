package no.nav.medlemskap.common.healthcheck

import io.github.resilience4j.kotlin.retry.executeSuspendFunction
import io.github.resilience4j.retry.Retry
import io.ktor.client.statement.HttpResponse
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

interface Result {
    val name: String
    val result: String
}

data class Healthy(override val name: String, override val result: String) : Result

data class UnHealthy(override val name: String, override val result: String) : Result

interface HealthCheck {
    val name: String
    suspend fun check(): Result
}

class TryCatchHealthCheck(
    override val name: String,
    private val block: suspend () -> Unit,
    private val retry: Retry? = null
) : HealthCheck {
    override suspend fun check(): Result {
        return try {
            if (retry != null) {
                retry.executeSuspendFunction {
                    block.invoke()
                    Healthy(name = name, result = "Healthy!")
                }
            } else {
                block.invoke()
                Healthy(name = name, result = "Healthy!")
            }
        } catch (cause: Throwable) {
            logger.error("Feil under helsesjekk mot $name", cause)
            UnHealthy(name = name, result = if (cause.message == null) "Unhealthy!" else cause.message!!)
        }
    }
}

class HttpResponseHealthCheck(
    override val name: String,
    private val block: suspend () -> HttpResponse,
    private val retry: Retry? = null
) : HealthCheck {
    override suspend fun check(): Result {
        return try {
            val httpResponse = if (retry != null) {
                retry.executeSuspendFunction { block.invoke() }
            } else {
                block.invoke()
            }
            if (httpResponse.status.value in 200..299)
                Healthy(name = name, result = "${httpResponse.status.value} (${httpResponse.status.description})")
            else
                UnHealthy(name = name, result = "${httpResponse.status.value} (${httpResponse.status.description})")
        } catch (cause: Throwable) {
            logger.error("Feil under helsesjekk mot $name", cause)
            UnHealthy(name = name, result = cause.message ?: "Unhealthy!")
        }
    }
}
