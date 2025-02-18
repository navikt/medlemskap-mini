package no.nav.medlemskap.clients.oppgave

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.clients.Token
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.common.cioHttpClient
import no.nav.medlemskap.config.Configuration
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate

class OppgaveClientTest {

    private val config = Configuration()

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
    fun `henter oppgaver`() {
        val callId = "123456"

        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.oppgaveScope) } returns Token("dummytoken", "", 1)

        stubFor(
            oppgaveRequestMapping
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatusCode.OK.value)
                        .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        .withBody(oppgaveResponse)
                )
        )

        val oppgaveclient = createOppgaveClient(azureAdClient)

        val oppgaveResponse = runBlocking { oppgaveclient.hentOppgaver(listOf("1234567890"), callId) }
        val oppgave = oppgaveResponse.oppgaver[0]

        assertEquals(1, oppgaveResponse.antallTreffTotalt)
        assertEquals("Z000001", oppgave.tilordnetRessurs)
        assertEquals(LocalDate.of(2010, 1, 1), oppgave.aktivDato)
        assertEquals(OppgPrioritet.HOY, oppgave.prioritet)
        assertEquals(OppgStatus.AAPNET, oppgave.status)
        assertEquals(1, oppgave.versjon)
        assertEquals("Z000001", oppgave.tilordnetRessurs)
        assertEquals("Testbeskrivelse", oppgave.beskrivelse)
    }

    @Test
    fun `tester ServerResponseException`() {
        val callId = "123456"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.oppgaveScope) } returns Token("dummytoken", "", 1)

        stubFor(
            oppgaveRequestMapping.willReturn(
                aResponse()
                    .withStatus(HttpStatusCode.InternalServerError.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val oppgaveclient = createOppgaveClient(azureAdClient)

        Assertions.assertThrows(ServerResponseException::class.java) {
            runBlocking { oppgaveclient.hentOppgaver(listOf("1234567890"), callId) }
        }
    }

    @Test
    fun `tester ClientRequestException`() {
        val callId = "123456"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.oppgaveScope) } returns Token("dummytoken", "", 1)

        stubFor(
            oppgaveRequestMapping.willReturn(
                aResponse()
                    .withStatus(HttpStatusCode.Forbidden.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val oppgaveclient = createOppgaveClient(azureAdClient)

        Assertions.assertThrows(ClientRequestException::class.java) {
            runBlocking { oppgaveclient.hentOppgaver(listOf("1234567890"), callId) }
        }
    }

    private fun createOppgaveClient(azureAdClient: AzureAdClient): OppgaveClient {
        return OppgaveClient(server.baseUrl(), azureAdClient, config, cioHttpClient)
    }
}

val oppgaveRequestMapping: MappingBuilder = get(urlPathEqualTo("/api/v1/oppgaver"))
    .withHeader(HttpHeaders.Authorization, equalTo("Bearer dummytoken"))
    .withHeader("Accept", equalTo("application/json"))
    .withHeader("X-Correlation-Id", equalTo("123456"))
    .withQueryParam("aktoerId", equalTo("1234567890"))
    .withQueryParam("tema", equalTo("MED"))
    .withQueryParam("tema", equalTo("UFM"))
    .withQueryParam("tema", equalTo("TRY"))

val oppgaveResponse =
    """
    {
        "antallTreffTotalt": 1,
        "oppgaver": [
            {
                "aktivDato": "2010-01-01",
                "prioritet": "HOY",
                "status": "AAPNET",
                "versjon": 1,
                "tilordnetRessurs": "Z000001",
                "tema": "MED",
                "beskrivelse": "Testbeskrivelse",
                "shouldBeIgnored": "ign"
            }
        ]
    }
    """.trimIndent()
