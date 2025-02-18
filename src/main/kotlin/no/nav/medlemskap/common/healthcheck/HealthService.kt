package no.nav.medlemskap.common.healthcheck

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging

class HealthService(
    private val healthChecks: Set<HealthCheck>
) {
    private companion object {
        private val logger = KotlinLogging.logger { }
    }

    internal suspend fun check(): List<Result> {
        val results = coroutineScope {
            val futures = mutableListOf<Deferred<Result>>()
            healthChecks.forEach { healthCheck ->
                futures.add(
                    async {
                        try {
                            healthCheck.check()
                        } catch (cause: Throwable) {
                            logger.error("Feil ved eksekvering av helsesjekk.", cause)
                            UnHealthy(
                                name = healthCheck.name,
                                result = cause.message
                                    ?: "Feil ved eksekvering av helsesjekk."
                            )
                        }
                    }
                )
            }
            futures.awaitAll()
        }
        results.filterIsInstance<UnHealthy>().forEach {
            logger.info("Failing Health Check: $it")
        }
        return results
    }
}
