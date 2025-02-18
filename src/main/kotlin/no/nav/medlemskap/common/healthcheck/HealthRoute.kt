package no.nav.medlemskap.common.healthcheck

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.system.measureTimeMillis

fun Routing.healthRoute(path: String, healthService: HealthService) {
    get(path) {
        var healthy: List<Result>
        var unhealthy: List<Result>

        val duration = Duration.of(
            measureTimeMillis {
                val results = healthService.check()
                healthy = results.filterIsInstance<Healthy>()
                unhealthy = results.filterIsInstance<UnHealthy>()
            },
            ChronoUnit.MILLIS
        )

        call.respond(
            status = if (unhealthy.isEmpty()) HttpStatusCode.OK else HttpStatusCode.ServiceUnavailable,
            message = mapOf(
                "duration" to duration.toString(), // ISO-8601
                "healthy" to healthy,
                "unhealthy" to unhealthy
            )
        )
    }
}
