package no.nav.medlemskap.clients.aareg

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.clients.Token
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.clients.medl.MedlClient
import no.nav.medlemskap.common.cioHttpClient
import no.nav.medlemskap.config.Configuration
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.time.LocalDateTime

class medlClientTest {

    companion object {
        val server: WireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
        private val config = Configuration()

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
        WireMock.configureFor(server.port())
    }

    @Test
    fun `tester response 200 OK`() {
        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()

        coEvery { azureAdClient.hentToken(config.register.medlScope) } returns Token("dummytoken", "", 1)

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.OK.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    .withBody(medlResponse)
            )
        )

        val client = createMedlClient(azureAdClient)
        val response = runBlocking { client.hentMedlemskapsunntak("10109000398", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }

        Assertions.assertEquals("Full", response[0].dekning)
        Assertions.assertEquals(100064642, response[0].unntakId)
        Assertions.assertEquals("25109209177", response[0].ident)
        Assertions.assertEquals(LocalDate.of(2018, 8, 16), response[0].fraOgMed)
        Assertions.assertEquals(LocalDate.of(2019, 6, 15), response[0].tilOgMed)
        Assertions.assertEquals("GYLD", response[0].status)
        Assertions.assertEquals(true, response[0].helsedel)
        Assertions.assertEquals(true, response[0].medlem)
        Assertions.assertEquals("NOR", response[0].lovvalgsland)
        Assertions.assertEquals("ENDL", response[0].lovvalg)
        Assertions.assertEquals("FTL_2-5", response[0].grunnlag)

        Assertions.assertEquals("0", response[0].sporingsinformasjon?.versjon)
        Assertions.assertEquals(LocalDate.of(2018, 6, 3), response[0].sporingsinformasjon?.registrert)
        Assertions.assertEquals(LocalDate.of(2018, 7, 16), response[0].sporingsinformasjon?.besluttet)
        Assertions.assertEquals("LAANEKASSEN", response[0].sporingsinformasjon?.kilde)
        Assertions.assertEquals("Henv_Soknad", response[0].sporingsinformasjon?.kildedokument)
        Assertions.assertEquals(LocalDateTime.of(2017, 1, 5, 12, 20, 38), response[0].sporingsinformasjon?.opprettet)
        Assertions.assertEquals("BMEDL2003", response[0].sporingsinformasjon?.opprettetAv)
        Assertions.assertEquals(LocalDateTime.of(2018, 6, 28, 12, 20, 38), response[0].sporingsinformasjon?.sistEndret)
        Assertions.assertEquals("REG-150", response[0].sporingsinformasjon?.sistEndretAv)

        Assertions.assertEquals("NOR", response[0].studieinformasjon?.statsborgerland)
        Assertions.assertEquals("USA", response[0].studieinformasjon?.studieland)
        Assertions.assertEquals(true, response[0].studieinformasjon?.delstudie)
        Assertions.assertEquals(true, response[0].studieinformasjon?.soeknadInnvilget)
    }

    @Test
    fun `tester ServerResponseException`() {
        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.medlScope) } returns Token("dummytoken", "", 1)

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.InternalServerError.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val client = createMedlClient(azureAdClient)

        Assertions.assertThrows(ServerResponseException::class.java) {
            runBlocking { client.hentMedlemskapsunntak("10109000398", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }
        }
    }

    @Test
    fun `tester ClientRequestException`() {

        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.medlScope) } returns Token("dummytoken", "", 1)

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.Forbidden.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val client = createMedlClient(azureAdClient)

        Assertions.assertThrows(ClientRequestException::class.java) {
            runBlocking { client.hentMedlemskapsunntak("10109000398", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }
        }
    }

    @Test
    fun `404 gir tom liste`() {
        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.medlScope) } returns Token("dummytoken", "", 1)

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.NotFound.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val client = createMedlClient(azureAdClient)
        val response = runBlocking { client.hentMedlemskapsunntak("10109000398", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }

        Assertions.assertEquals(0, response.size)
    }

    private fun createMedlClient(azureAdClient: AzureAdClient): MedlClient {
        return MedlClient(server.baseUrl(), azureAdClient, config, cioHttpClient)
    }

    private val queryMapping: MappingBuilder = WireMock.get(WireMock.urlPathEqualTo("/api/v1/medlemskapsunntak"))
        .withHeader(HttpHeaders.Authorization, equalTo("Bearer dummytoken"))
        .withHeader("Nav-Personident", equalTo("10109000398"))
        .withHeader("Nav-Call-Id", equalTo("12345"))
        .withQueryParam("fraOgMed", equalTo("2010-01-01"))
        .withQueryParam("fraOgMed", equalTo("2010-01-01"))

    private val medlResponse =
        """
[
  {
    "unntakId": 100064642,
    "ident": "25109209177",
    "fraOgMed": "2018-08-16",
    "tilOgMed": "2019-06-15",
    "status": "GYLD",
    "dekning": "Full",
    "helsedel": true,
    "medlem": true,
    "lovvalgsland": "NOR",
    "lovvalg": "ENDL",
    "grunnlag": "FTL_2-5",
    "sporingsinformasjon": {
      "versjon": 0,
      "registrert": "2018-06-03",
      "besluttet": "2018-07-16",
      "kilde": "LAANEKASSEN",
      "kildedokument": "Henv_Soknad",
      "opprettet": "2017-01-05T12:20:38",
      "opprettetAv": "BMEDL2003",
      "sistEndret": "2018-06-28T12:20:38",
      "sistEndretAv": "REG-150"
    },
    "studieinformasjon": {
      "statsborgerland": "NOR",
      "studieland": "USA",
      "delstudie": true,
      "soeknadInnvilget": true
    }
  }
]
        """.trimIndent()
}
