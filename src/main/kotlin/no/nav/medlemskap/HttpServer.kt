package no.nav.medlemskap

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import io.prometheus.client.CollectorRegistry
import mu.KotlinLogging
import no.nav.medlemskap.clients.Services
import no.nav.medlemskap.common.*
import no.nav.medlemskap.common.JwtConfig.Companion.REALM
import no.nav.medlemskap.common.healthcheck.healthRoute
import no.nav.medlemskap.config.AzureAdOpenIdConfiguration
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.config.getAadConfig
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Request
import no.nav.medlemskap.routes.*
import org.slf4j.event.Level
import java.util.*

private val logger = KotlinLogging.logger { }

fun
createHttpServer(
    applicationState: ApplicationState,
    useAuthentication: Boolean = true,
    configuration: Configuration = Configuration(),
    azureAdOpenIdConfiguration: AzureAdOpenIdConfiguration = getAadConfig(configuration.azureAd),
    services: Services = Services(configuration),
    port: Int = 7070,
    prometheusRegistry: PrometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT),
    createDatagrunnlag: suspend (request: Request, callId: String, services: Services, clientId: String?) -> Datagrunnlag = ::defaultCreateDatagrunnlag
): ApplicationEngine = embeddedServer(Netty, port) {

    install(StatusPages) {
        exceptionHandler()
    }

    install(CallLogging) {
        level = Level.INFO
        callIdMdc(MDC_CALL_ID)
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(objectMapper))
    }

    install(CallId) {
        header(MDC_CALL_ID)
        generate { UUID.randomUUID().toString() }
        verify { callId: String -> callId.isNotEmpty() }
    }

    if (useAuthentication) {
        logger.info { "Installerer authentication" }
        install(Authentication) {
            jwt("azureAuth") {
                val jwtConfig = JwtConfig(configuration, azureAdOpenIdConfiguration)
                realm = REALM
                verifier(jwtConfig.jwkProvider, azureAdOpenIdConfiguration.issuer)
                validate { credentials ->
                    jwtConfig.validate(credentials)
                }
            }
        }
    } else {
        logger.info { "Installerer IKKE authentication" }
    }

    install(MicrometerMetrics) {
        registry = prometheusRegistry
        meterBinders = listOf(
            ClassLoaderMetrics(),
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            ProcessorMetrics(),
            JvmThreadMetrics(),
            FileDescriptorMetrics()
        )
    }
    val requestContextService = RequestContextService()
    if (useAuthentication) {
        routing {
            naisRoutes(
                readinessCheck = { applicationState.initialized }, livenessCheck = { applicationState.running }, collectorRegistry = CollectorRegistry.defaultRegistry
            )
            evalueringRoute(services, configuration, requestContextService, createDatagrunnlag)
            reglerRoute()
            healthRoute("/healthCheck", services.healthService)
        }
    } else {
        routing {
            naisRoutes(readinessCheck = { applicationState.initialized }, livenessCheck = { applicationState.running }, collectorRegistry = CollectorRegistry.defaultRegistry)
            evalueringTestRoute(services, configuration, requestContextService, createDatagrunnlag)
            reglerRoute()
            healthRoute("/healthCheck", services.healthService)
        }
    }

    applicationState.initialized = true
}
