package no.nav.medlemskap.common

import io.ktor.server.engine.*
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig.objectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.response.ResponseBodyExtractionOptions
import io.restassured.specification.RequestSpecification
import no.nav.medlemskap.ApplicationState
import no.nav.medlemskap.clients.Services
import no.nav.medlemskap.config.AzureAdOpenIdConfiguration
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.createHttpServer
import org.junit.jupiter.api.BeforeAll

open class ServerTest {

    protected fun RequestSpecification.When(): RequestSpecification {
        return this.`when`()
    }

    protected inline fun <reified T> ResponseBodyExtractionOptions.to(): T {
        return this.`as`(T::class.java)
    }

    companion object {

        private val configuration = Configuration(
            azureAd = Configuration.AzureAd(
                azureAppWellKnownUrl = "http://localhost/"
            )
        )
        private val services = Services(configuration)
        private val openIdConfiguration = AzureAdOpenIdConfiguration("http://localhost", "", "", "")

        private var applicationState = ApplicationState(false, false)

        private lateinit var server: ApplicationEngine

        @BeforeAll
        @JvmStatic
        fun startServer() {

            if (!applicationState.running) {
                val applicationServer = createHttpServer(applicationState, true, configuration, openIdConfiguration, services)

                Runtime.getRuntime().addShutdownHook(
                    Thread {
                        applicationState.initialized = false
                        applicationServer.stop(5000, 5000)
                    }
                )

                applicationServer.start()
                applicationState.initialized = true
                applicationState.running = true

                RestAssured.baseURI = "http://localhost"
                RestAssured.basePath = "/"
                RestAssured.port = 7070
                RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                    objectMapperConfig()
                        .jackson2ObjectMapperFactory { _, _ -> objectMapper }
                )
            }
        }
    }
}
