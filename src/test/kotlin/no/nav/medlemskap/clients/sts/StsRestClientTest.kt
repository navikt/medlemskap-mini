package no.nav.medlemskap.clients.sts

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.common.cioHttpClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StsRestClientTest {

    companion object {
        val server: WireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())

        @BeforeAll
        @JvmStatic
        fun start() {
            server.start()
        }

        @AfterAll
        @JvmStatic
        fun stop() {
            server.stop()
        }
    }

    @BeforeEach
    fun configure() {
        configureFor(server.port())
    }

    @Test
    fun `should cache tokens`() {
        stubFor(
            stsRequestMapping
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatusCode.OK.value)
                        .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        .withBody(default_token)
                )
                .inScenario("caching")
                .whenScenarioStateIs(STARTED)
                .willSetStateTo("token acquired")
        )

        stubFor(
            stsRequestMapping
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatusCode.OK.value)
                        .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        .withBody(bad_token)
                )
                .inScenario("caching")
                .whenScenarioStateIs("token acquired")
        )

        val authHelper = StsRestClient(baseUrl = server.baseUrl(), username = "foo", password = "bar", httpClient = cioHttpClient, apiKey = "stsApiKey")
        val first = runBlocking { authHelper.oidcToken() }

        val second: String = runBlocking { authHelper.oidcToken() }

        println(first)

        assertEquals(first, second)
        assertEquals("default access token", second)
    }
}

private val stsRequestMapping: MappingBuilder = get(urlPathEqualTo("/rest/v1/sts/token"))
    .withQueryParam("grant_type", equalTo("client_credentials"))
    .withQueryParam("scope", equalTo("openid"))
    .withBasicAuth("foo", "bar")
    .withHeader("Accept", equalTo("application/json"))

private val default_token =
    """{
  "access_token": "default access token",
  "token_type": "Bearer",
  "expires_in": 3600
}
    """.trimIndent()

private val short_lived_token =
    """{
  "access_token": "short lived token",
  "token_type": "Bearer",
  "expires_in": 1
}
    """.trimIndent()

private val bad_token =
    """{
  "access_token": "this token shouldn't be requested",
  "token_type": "Bearer",
  "expires_in": 1000000000000
}
    """.trimIndent()
