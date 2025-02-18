package no.nav.medlemskap.common.healthcheck

import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.common.clientsGauge
import no.nav.medlemskap.common.totalGauge
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.fixedRateTimer

class HealthReporter(
    private val healthService: HealthService,
    frequency: Duration = Duration.ofSeconds(30)
) {

    private companion object {
        private const val HEALTHY = 0
        private const val UNHEALTHY = 1

        private val clientGauges: MutableMap<String, AtomicInteger> = mutableMapOf()
        private val totalGauge: AtomicInteger = totalGauge()
    }

    private val timer: Timer

    init {
        timer = fixedRateTimer(
            name = "health_reporter",
            initialDelay = Duration.ofMinutes(1).toMillis(),
            period = frequency.toMillis()
        ) {
            val results = runBlocking { healthService.check() }
            results.setTotalStatus(totalGauge)
            results.setClientStatus(clientGauges)
        }

        Runtime.getRuntime().addShutdownHook(
            Thread {
                timer.cancel()
            }
        )
    }

    private fun List<Result>.setTotalStatus(totalGauge: AtomicInteger) {
        totalGauge.set(if (any { it is UnHealthy }) UNHEALTHY else HEALTHY)
    }

    private fun List<Result>.setClientStatus(clientGauges: MutableMap<String, AtomicInteger>) {
        forEach { clientGauges.getOrPut(it.name) { clientsGauge(it.name) }.set(if (it is UnHealthy) UNHEALTHY else HEALTHY) }
    }
}
