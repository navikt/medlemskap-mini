package no.nav.medlemskap.common

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.Header
import io.restassured.response.ValidatableResponse
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.ApplicationState
import no.nav.medlemskap.clients.Services
import no.nav.medlemskap.config.AzureAdOpenIdConfiguration
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.createHttpServer
import no.nav.medlemskap.cucumber.Medlemskapsparametre
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Request

class LokalWebServer {
    companion object {

        private val configuration = Configuration(
            azureAd = Configuration.AzureAd(
                azureAppWellKnownUrl = "http://localhost/"
            )
        )
        private val services = Services(configuration)
        private val openIdConfiguration = AzureAdOpenIdConfiguration("http://localhost", "", "", "")

        private var applicationState = ApplicationState(false, false)

        var testDatagrunnlag: Datagrunnlag? = null

        fun startServer() {

            if (!applicationState.running) {
                val applicationServer = createHttpServer(
                    applicationState = applicationState,
                    useAuthentication = false,
                    configuration = configuration,
                    azureAdOpenIdConfiguration = openIdConfiguration,
                    services = services,
                    port = 7071,
                    createDatagrunnlag = ::mockCreateDatagrunnlag
                )

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
                RestAssured.port = 7071
                RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                    ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory { _, _ -> objectMapper }
                )
            }
        }

        fun byggInput(medlemskapsparametre: Medlemskapsparametre): String {
            return objectMapper.writeValueAsString(
                Request(
                    fnr = medlemskapsparametre.fnr!!,
                    periode = medlemskapsparametre.inputPeriode,
                    brukerinput = medlemskapsparametre.brukerinput,
                    førsteDagForYtelse = medlemskapsparametre.førsteDagForYtelse,
                    ytelse = medlemskapsparametre.ytelse
                )
            )
        }

        fun medlemskapRequest(medlemskapsparametre: Medlemskapsparametre): String {
            return given()
                .body(medlemskapsparametre.tilJson())
                .header(Header("Content-Type", "application/json"))
                .header(Header("Nav-Call-Id", "7b006d2c-ff75-4404-9663-7babf23f45de"))
                .post("/")
                .then()
                .statusCode(200)
                .extract().asString()
        }

        fun responsUtenStatuskodeSjekk(input: String): String {
            return given()
                .body(input)
                .header(Header("Content-Type", "application/json"))
                .post("/")
                .then()
                .extract().asString()
        }

        fun validerKontraktMedlemskap(medlemskapsparametre: Medlemskapsparametre): ValidatableResponse {
            val validationFilter = OpenApiValidationFilter("src/main/resources/lovme.yaml")

            return given()
                .filter(validationFilter)
                .body(medlemskapsparametre.tilJson())
                .header(Header("Content-Type", "application/json"))
                .header(Header("Authorization", "Bearer dummytoken"))
                .post("/")
                .then()
                .statusCode(200)
        }

        fun validerKontraktRegler(datagrunnlag: Datagrunnlag): ValidatableResponse {
            val validationFilter = OpenApiValidationFilter("src/main/resources/lovme.yaml")

            val response = given()
                .filter(validationFilter)
                .body(datagrunnlag.tilJson())
                .header(Header("Content-Type", "application/json"))
                .header(Header("Authorization", "Bearer dummytoken"))
                .post("regler")
                .then()
                .statusCode(200)

            return response
        }

        fun testResponsKode(input: String, statusKode: Int): String {
            return given()
                .body(input)
                .header(Header("Content-Type", "application/json"))
                .post("/")
                .then()
                .statusCode(statusKode)
                .extract().asString()
        }

        suspend fun mockCreateDatagrunnlag(
            request: Request,
            callId: String,
            services: Services,
            clientId: String?
        ): Datagrunnlag = runBlocking {
            if (testDatagrunnlag == null) {
                throw RuntimeException("testDatagrunnlag er null")
            }

            testDatagrunnlag!!
        }
    }
}
