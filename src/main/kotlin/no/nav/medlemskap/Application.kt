package no.nav.medlemskap

import no.nav.medlemskap.common.configurePrometheusMeterRegistry

data class ApplicationState(var running: Boolean = true, var initialized: Boolean = false)

fun main() {
    val applicationState = ApplicationState()

    val prometheusMeterRegistry = configurePrometheusMeterRegistry()
    // configureSensuInfluxMeterRegistry()

    val applicationServer = createHttpServer(applicationState = applicationState, prometheusRegistry = prometheusMeterRegistry)

    Runtime.getRuntime().addShutdownHook(
        Thread {
            applicationState.initialized = false
            applicationServer.stop(5000, 5000)
        }
    )

    applicationServer.start(wait = true)
}
