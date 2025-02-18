package no.nav.medlemskap.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat

fun Routing.naisRoutes(
    readinessCheck: () -> Boolean,
    livenessCheck: () -> Boolean = { true },
    collectorRegistry: CollectorRegistry = CollectorRegistry.defaultRegistry
) {

    get("/isAlive") {
        if (livenessCheck()) {
            call.respondText("Alive")
        } else {
            call.respondText("Not alive", status = HttpStatusCode.InternalServerError)
        }
    }
    get("/health") {
        if (livenessCheck()) {
            call.respond(HttpStatusCode.OK, Health(Status.OK, null, null))
        } else {
            call.respond(HttpStatusCode.OK, Health(Status.DOWN, null, null))
        }
    }

    get("/isReady") {
        if (readinessCheck()) {
            call.respondText("Ready")
        } else {
            call.respondText("Not ready", status = HttpStatusCode.InternalServerError)
        }
    }

    get("/metrics") {
        val names = call.request.queryParameters.getAll("name[]")?.toSet() ?: setOf()
        call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
            TextFormat.write004(this, collectorRegistry.filteredMetricFamilySamples(names))
        }
    }
}
data class Health(val status: Status, val description: String?, val logLink: String?)
enum class Status() {
    OK,
    DOWN,
    ISSUE
}
