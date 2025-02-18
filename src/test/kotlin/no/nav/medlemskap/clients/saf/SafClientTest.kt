package no.nav.medlemskap.clients.saf

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.clients.saf.generated.enums.Journalposttype
import no.nav.medlemskap.clients.saf.generated.enums.Journalstatus
import no.nav.medlemskap.clients.saf.generated.enums.Tema
import no.nav.medlemskap.clients.sts.StsRestClient
import no.nav.medlemskap.common.cioHttpClient
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

class SafClientTest {

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
    fun `henter journalposter`() {
        val callId = "123456"
        val username = "whatever"
        val stsClient: StsRestClient = mockk()
        coEvery { stsClient.oidcToken() } returns "dummytoken"

        stubFor(
            safRequestMapping
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatusCode.OK.value)
                        .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        .withBody(safResponse)
                )
        )

        val safClient = SafClient(server.baseUrl(), stsClient, username, cioHttpClient, "123")

        val safResponse = runBlocking { safClient.hentJournaldatav2("1234567890", callId) }

        assertEquals("439560100", safResponse.dokumentoversiktBruker.journalposter.first()?.journalpostId)
        assertEquals("MASKERT_FELT", safResponse.dokumentoversiktBruker.journalposter.first()?.tittel)
        assertEquals(Journalposttype.I, safResponse.dokumentoversiktBruker.journalposter.first()?.journalposttype)
        assertEquals(Journalstatus.JOURNALFOERT, safResponse.dokumentoversiktBruker.journalposter.first()?.journalstatus)
        assertEquals(Tema.SYK, safResponse.dokumentoversiktBruker.journalposter.first()?.tema)
        assertEquals("453743887", safResponse.dokumentoversiktBruker.journalposter.first()?.dokumenter?.first()?.dokumentInfoId)
        assertEquals("MASKERT_FELT", safResponse.dokumentoversiktBruker.journalposter.first()?.dokumenter?.first()?.tittel)
    }

    val safRequestMapping: MappingBuilder = post(urlPathEqualTo("/"))
        .withHeader(HttpHeaders.Authorization, equalTo("Bearer dummytoken"))
        .withHeader("Accept", containing("application/json"))
        .withHeader("Nav-Consumer-Id", equalTo("whatever"))

    val safResponse =
        """{
            "data":
            {
                "dokumentoversiktBruker":{
                    "journalposter":[
                         {
                            "journalpostId":"439560100",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"I",
                            "journalstatus":"JOURNALFOERT",
                            "tema":"SYK",
                            "datoOpprettet":"2018-12-27T14:42:26",
                            "dokumenter":[
                                {
                                    "dokumentInfoId":"453743887",
                                    "tittel":"MASKERT_FELT"
                                }
                            ]
                         },
                         {
                            "journalpostId":"439532144",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"I","journalstatus":"JOURNALFOERT",
                            "tema":"SYK",
                            "datoOpprettet":"2018-12-26T18:30:50",
                            "dokumenter":[
                                 {
                                    "dokumentInfoId":"453708906",
                                    "tittel":"MASKERT_FELT"
                                 }
                            ]},
                         {
                            "journalpostId":"437901576",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"I",
                            "journalstatus":"JOURNALFOERT",
                            "tema":"SYM",
                            "datoOpprettet":"2018-11-30T14:22:36",
                            "dokumenter":[
                                 {
                                     "dokumentInfoId":"451771325",
                                     "tittel":"MASKERT_FELT"
                                 },
                                 {
                                      "dokumentInfoId":"451771324",
                                      "tittel":"MASKERT_FELT"
                                 }
                            ]
                         },
                         {
                            "journalpostId":"435804781",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"I",
                            "journalstatus":"JOURNALFOERT",
                            "tema":"SYM",
                            "datoOpprettet":"2018-10-26T16:15:22",
                            "dokumenter":[
                                 {
                                    "dokumentInfoId":"449179779",
                                    "tittel":"MASKERT_FELT"
                                 }
                            ]
                         },
                            {
                            "journalpostId":"429353385",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"I",
                            "journalstatus":"JOURNALFOERT",
                            "tema":"SYK",
                            "datoOpprettet":"2018-06-30T09:22:46",
                            "dokumenter":[
                                  {
                                  "dokumentInfoId":"441299963",
                                  "tittel":"MASKERT_FELT"
                                  }
                            ]
                         },
                         {
                            "journalpostId":"429111291",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"U",
                            "journalstatus":"FERDIGSTILT",
                            "tema":"OPP",
                            "datoOpprettet":"2018-06-27T04:02:13",
                            "dokumenter":[
                                {
                                "dokumentInfoId":"441010176",
                                "tittel":"MASKERT_FELT"
                                }
                                ]
                         },
                         {
                             "journalpostId":"429108246",
                             "tittel":"MASKERT_FELT",
                             "journalposttype":"U",
                             "journalstatus":"FERDIGSTILT",
                             "tema":"OPP",
                             "datoOpprettet":"2018-06-27T03:58:56",
                             "dokumenter": [
                                {
                                "dokumentInfoId":"441007131",
                                "tittel":"MASKERT_FELT"
                                }
                             ]
                         },
                         {
                              "journalpostId":"428965411",
                              "tittel":"MASKERT_FELT",
                              "journalposttype":"I",
                              "journalstatus":"JOURNALFOERT",
                              "tema":"SYM",
                              "datoOpprettet":"2018-06-25T09:22:09",
                              "dokumenter":[
                                    {
                                    "dokumentInfoId":"440831549",
                                    "tittel":"MASKERT_FELT"
                                    },
                                    {
                                    "dokumentInfoId":"440831548",
                                    "tittel":"MASKERT_FELT"
                                    }
                              ]
                         },
                         {
                             "journalpostId":"428936760",
                             "tittel":"MASKERT_FELT",
                             "journalposttype":"U",
                             "journalstatus":"EKSPEDERT",
                             "tema":"HEL",
                             "datoOpprettet":"2018-06-23T00:30:55",
                             "dokumenter":[
                             {
                                "dokumentInfoId":"440795691",
                                 "tittel":"MASKERT_FELT"
                             }
                             ]
                         },
                         {
                            "journalpostId":"428833894",
                            "tittel":"MASKERT_FELT",
                            "journalposttype":"I",
                            "journalstatus":"JOURNALFOERT",
                            "tema":"HEL",
                            "datoOpprettet":"2018-06-21T16:03:26",
                            "dokumenter":[
                                {
                                "dokumentInfoId":"440675418",
                                "tittel":"MASKERT_FELT"
                                }
                            ]
                         }
                    ]
                }
            }
        }
        """.trimIndent()
}
