package no.nav.medlemskap.clients.aareg

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.common.cioHttpClient
import no.nav.medlemskap.config.Configuration
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.time.LocalDateTime

class AaregClientTest {

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
        WireMock.configureFor(server.port())
    }

    @Test
    fun `tester response`() {
        val callId = "12345"

        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.aaregScope).token } returns "dummytoken"

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.OK.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    .withBody(aaRegResponse)
            )
        )

        val client = createAaRegClient(azureAdClient)

        val response = runBlocking { client.hentArbeidsforhold("26104635775", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }

        val arbeidsforholdType = response[0].type
        Assertions.assertEquals("ordinaertArbeidsforhold", arbeidsforholdType)

        val arbeidsavtale = response[0].arbeidsavtaler[0]
        Assertions.assertEquals(AaRegGyldighetsperiode(LocalDate.parse("2018-04-01"), null), arbeidsavtale.gyldighetsperiode)
        Assertions.assertEquals(AaRegBruksperiode(LocalDateTime.parse("2018-09-13T10:31:09.343"), null), arbeidsavtale.bruksperiode)
        Assertions.assertEquals("ikkeSkift", arbeidsavtale.arbeidstidsordning)
        Assertions.assertEquals("8322108", arbeidsavtale.yrke)
        Assertions.assertEquals(100.0, arbeidsavtale.stillingsprosent)
        Assertions.assertEquals(37.5, arbeidsavtale.antallTimerPrUke)

        val arbeidsgiver = response[0].arbeidsgiver
        Assertions.assertEquals("985672744", arbeidsgiver.organisasjonsnummer)
        Assertions.assertEquals(AaRegOpplysningspliktigArbeidsgiverType.Organisasjon, arbeidsgiver.type)

        val periode = response[0].ansettelsesperiode
        Assertions.assertEquals(AaRegPeriode(LocalDate.parse("2008-01-01"), LocalDate.parse("2018-10-30")), periode.periode)

        val permisjonPermittering = response[0].permisjonPermitteringer?.get(0)
        Assertions.assertEquals(AaRegPeriode(LocalDate.parse("1975-10-10"), LocalDate.parse("2020-08-01")), permisjonPermittering?.periode)
        Assertions.assertEquals("000000", permisjonPermittering?.permisjonPermitteringId)
        Assertions.assertEquals(100.0, permisjonPermittering?.prosent)
        Assertions.assertEquals("permisjon", permisjonPermittering?.type)
        Assertions.assertEquals("PPIDHI", permisjonPermittering?.varslingskode)
    }

    @Test
    fun `tester ServerResponseException`() {
        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.aaregScope).token } returns "dummytoken"

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.InternalServerError.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )
        val client = AaRegClient(server.baseUrl(), azureAdClient, cioHttpClient, config, "123")

        Assertions.assertThrows(ServerResponseException::class.java) {
            runBlocking { client.hentArbeidsforhold("26104635775", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }
        }
    }

    @Test
    fun `tester ClientRequestException`() {
        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.aaregScope).token } returns "dummytoken"

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.Forbidden.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val client = createAaRegClient(azureAdClient)

        Assertions.assertThrows(ClientRequestException::class.java) {
            runBlocking { client.hentArbeidsforhold("26104635775", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }
        }
    }

    @Test
    fun `404 gir tom liste`() {
        val callId = "12345"
        val azureAdClient: AzureAdClient = mockk()
        coEvery { azureAdClient.hentToken(config.register.aaregScope).token } returns "dummytoken"

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.NotFound.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            )
        )

        val client = createAaRegClient(azureAdClient)
        val response = runBlocking { client.hentArbeidsforhold("26104635775", callId, LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1, 1)) }

        Assertions.assertEquals(0, response.size)
    }

    private fun createAaRegClient(azureAdClient: AzureAdClient): AaRegClient {
        return AaRegClient(
            baseUrl = server.baseUrl(),
            azureAdClient = azureAdClient,
            configuration = config,
            httpClient = cioHttpClient,
            aaRegApiKey = "123"
        )
    }

    private val aaRegResponse =
        """
[
  {
    "navArbeidsforholdId": 33536256,
     "permisjonPermitteringer": [ {
                "periode": {
                  "fom" : "1975-10-10",
                  "tom" : "2020-08-01"
                }, 
                "permisjonPermitteringId": "000000", 
                "prosent": 100.0, 
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2019-06-19T07:21:31.491",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "c2b82646-0587-490c-9662-bab5dd31e9f5",
                  "opprettetTidspunkt": "2015-01-23T07:50:43.200"
                },
                "type": "permisjon",
                "varslingskode": "PPIDHI"
            }
     ],
    "arbeidsforholdId": "00163EDC07471AA7ACD32B7DC8C675EA",
    "arbeidstaker": {
      "type": "Person",
      "offentligIdent": "26109135775",
      "aktoerId": "1999962807916"
    },
    "arbeidsgiver": {
      "type": "Organisasjon",
      "organisasjonsnummer": "985672744"
    },
    "opplysningspliktig": {
      "type": "Organisasjon",
      "organisasjonsnummer": "985615616"
    },
    "type": "ordinaertArbeidsforhold",
    "ansettelsesperiode": {
      "periode": {
        "fom": "2008-01-01",
        "tom": "2018-10-30"
      },
      "bruksperiode": {
        "fom": "2018-11-15T21:37:40.835"
      },
      "sporingsinformasjon": {
        "opprettetTidspunkt": "2018-11-15T21:37:40.835",
        "opprettetAv": "srvappserver",
        "opprettetKilde": "EDAG",
        "opprettetKildereferanse": "eda00000-0000-0000-0000-001488880167",
        "endretTidspunkt": "2018-11-15T21:37:40.835",
        "endretAv": "srvappserver",
        "endretKilde": "EDAG",
        "endretKildereferanse": "eda00000-0000-0000-0000-001488880167"
      }
    },
    "arbeidsavtaler": [
      {
        "arbeidstidsordning": "ikkeSkift",
        "yrke": "8322108",
        "stillingsprosent": 100,
        "antallTimerPrUke": 37.5,
        "beregnetAntallTimerPrUke": 37.5,
        "sistLoennsendring": "2018-04-01",
        "sistStillingsendring": "2017-09-01",
        "bruksperiode": {
          "fom": "2018-09-13T10:31:09.343"
        },
        "gyldighetsperiode": {
          "fom": "2018-04-01"
        },
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-09-13T10:31:09.354",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001385477431",
          "endretTidspunkt": "2018-09-13T10:31:09.354",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001385477431"
        }
      }
    ],
    "antallTimerForTimeloennet": [
      {
        "periode": {},
        "antallTimer": 14.5,
        "rapporteringsperiode": "2016-08",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2016-08-22T13:14:58.704",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000136757537",
          "endretTidspunkt": "2016-08-22T13:14:58.704",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000136757537"
        }
      },
      {
        "periode": {},
        "antallTimer": 37.43,
        "rapporteringsperiode": "2016-09",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2016-09-16T14:21:24.362",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000178501421",
          "endretTidspunkt": "2016-09-16T14:21:24.362",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000178501421"
        }
      },
      {
        "periode": {},
        "antallTimer": 39.79,
        "rapporteringsperiode": "2016-10",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2016-10-20T13:20:30.45",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000227312938",
          "endretTidspunkt": "2016-10-20T13:20:30.45",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000227312938"
        }
      },
      {
        "periode": {},
        "antallTimer": 26.97,
        "rapporteringsperiode": "2016-11",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2016-11-17T12:23:05.403",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000272667516",
          "endretTidspunkt": "2016-11-17T12:23:05.403",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000272667516"
        }
      },
      {
        "periode": {},
        "antallTimer": 9,
        "rapporteringsperiode": "2016-12",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2016-12-27T09:49:55.437",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000350849717",
          "endretTidspunkt": "2016-12-27T09:49:55.437",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000350849717"
        }
      },
      {
        "periode": {},
        "antallTimer": 50.48,
        "rapporteringsperiode": "2017-01",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-01-25T07:48:57.886",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000401682508",
          "endretTidspunkt": "2017-01-25T07:48:57.886",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000401682508"
        }
      },
      {
        "periode": {},
        "antallTimer": 22.19,
        "rapporteringsperiode": "2017-02",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-02-21T13:05:58.3",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000453636343",
          "endretTidspunkt": "2017-02-21T13:05:58.3",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000453636343"
        }
      },
      {
        "periode": {},
        "antallTimer": 19.82,
        "rapporteringsperiode": "2017-03",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-03-17T13:18:50.072",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000495210866",
          "endretTidspunkt": "2017-03-17T13:18:50.072",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000495210866"
        }
      },
      {
        "periode": {},
        "antallTimer": 13.82,
        "rapporteringsperiode": "2017-04",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-04-25T09:25:53.627",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000544413562",
          "endretTidspunkt": "2017-04-25T09:25:53.627",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000544413562"
        }
      },
      {
        "periode": {},
        "antallTimer": 19.13,
        "rapporteringsperiode": "2017-05",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-05-16T15:45:57.296",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000581854873",
          "endretTidspunkt": "2017-05-16T15:45:57.296",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000581854873"
        }
      },
      {
        "periode": {},
        "antallTimer": 26.29,
        "rapporteringsperiode": "2017-06",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-06-22T11:52:53.968",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000630418558",
          "endretTidspunkt": "2017-06-22T11:52:53.968",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000630418558"
        }
      },
      {
        "periode": {},
        "antallTimer": 18.35,
        "rapporteringsperiode": "2017-07",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-07-21T12:58:17.226",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000683087184",
          "endretTidspunkt": "2017-07-21T12:58:17.226",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000683087184"
        }
      },
      {
        "periode": {},
        "antallTimer": 23.34,
        "rapporteringsperiode": "2017-08",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-08-24T12:52:40.183",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000729531602",
          "endretTidspunkt": "2017-08-24T12:52:40.183",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000729531602"
        }
      },
      {
        "periode": {},
        "antallTimer": 39.56,
        "rapporteringsperiode": "2017-09",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2017-09-27T14:01:37.097",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-000786163023",
          "endretTidspunkt": "2017-09-27T14:01:37.097",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-000786163023"
        }
      },
      {
        "periode": {
          "fom": "2017-09-01",
          "tom": "2017-09-30"
        },
        "antallTimer": 13.96,
        "rapporteringsperiode": "2017-10",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:35:03.553",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488765214",
          "endretTidspunkt": "2018-11-15T21:35:03.553",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488765214"
        }
      },
      {
        "periode": {
          "fom": "2017-10-01",
          "tom": "2017-10-31"
        },
        "antallTimer": 29.39,
        "rapporteringsperiode": "2017-11",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:35:29.993",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488795152",
          "endretTidspunkt": "2018-11-15T21:35:29.993",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488795152"
        }
      },
      {
        "periode": {
          "fom": "2017-11-01",
          "tom": "2017-11-30"
        },
        "antallTimer": 33.19,
        "rapporteringsperiode": "2017-12",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:35:44.471",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488815605",
          "endretTidspunkt": "2018-11-15T21:35:44.471",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488815605"
        }
      },
      {
        "periode": {
          "fom": "2017-12-01",
          "tom": "2017-12-31"
        },
        "antallTimer": 17.26,
        "rapporteringsperiode": "2018-01",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:35:57.632",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488821672",
          "endretTidspunkt": "2018-11-15T21:35:57.632",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488821672"
        }
      },
      {
        "periode": {
          "fom": "2017-12-01",
          "tom": "2017-12-31"
        },
        "antallTimer": 0.9,
        "rapporteringsperiode": "2018-02",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:36:07.64",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488828150",
          "endretTidspunkt": "2018-11-15T21:36:07.64",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488828150"
        }
      },
      {
        "periode": {
          "fom": "2018-01-01",
          "tom": "2018-01-31"
        },
        "antallTimer": 28.82,
        "rapporteringsperiode": "2018-02",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:36:07.64",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488828150",
          "endretTidspunkt": "2018-11-15T21:36:07.64",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488828150"
        }
      },
      {
        "periode": {
          "fom": "2018-02-01",
          "tom": "2018-02-28"
        },
        "antallTimer": 26.31,
        "rapporteringsperiode": "2018-03",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:36:21.639",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488834422",
          "endretTidspunkt": "2018-11-15T21:36:21.639",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488834422"
        }
      },
      {
        "periode": {
          "fom": "2018-03-01",
          "tom": "2018-03-31"
        },
        "antallTimer": 10.66,
        "rapporteringsperiode": "2018-04",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:36:32.863",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488841070",
          "endretTidspunkt": "2018-11-15T21:36:32.863",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488841070"
        }
      },
      {
        "periode": {
          "fom": "2018-04-01",
          "tom": "2018-04-30"
        },
        "antallTimer": 19.18,
        "rapporteringsperiode": "2018-05",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:36:41.599",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488847492",
          "endretTidspunkt": "2018-11-15T21:36:41.599",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488847492"
        }
      },
      {
        "periode": {
          "fom": "2018-05-01",
          "tom": "2018-05-31"
        },
        "antallTimer": 23.04,
        "rapporteringsperiode": "2018-06",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:36:56.156",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488854725",
          "endretTidspunkt": "2018-11-15T21:36:56.156",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488854725"
        }
      },
      {
        "periode": {
          "fom": "2018-06-01",
          "tom": "2018-06-30"
        },
        "antallTimer": 35.9,
        "rapporteringsperiode": "2018-07",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:37:05.898",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488861234",
          "endretTidspunkt": "2018-11-15T21:37:05.898",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488861234"
        }
      },
      {
        "periode": {
          "fom": "2018-08-01",
          "tom": "2018-08-31"
        },
        "antallTimer": 9.62,
        "rapporteringsperiode": "2018-09",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:37:29.599",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
          "endretTidspunkt": "2018-11-15T21:37:29.599",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488805209"
        }
      },
      {
        "periode": {
          "fom": "2018-05-01",
          "tom": "2018-05-31"
        },
        "antallTimer": 0,
        "rapporteringsperiode": "2018-09",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:37:29.601",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
          "endretTidspunkt": "2018-11-15T21:37:29.601",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488805209"
        }
      },
      {
        "periode": {
          "fom": "2018-04-01",
          "tom": "2018-04-30"
        },
        "antallTimer": 0,
        "rapporteringsperiode": "2018-09",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:37:29.601",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
          "endretTidspunkt": "2018-11-15T21:37:29.601",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488805209"
        }
      },
      {
        "periode": {
          "fom": "2018-06-01",
          "tom": "2018-06-30"
        },
        "antallTimer": 0,
        "rapporteringsperiode": "2018-09",
        "sporingsinformasjon": {
          "opprettetTidspunkt": "2018-11-15T21:37:29.601",
          "opprettetAv": "srvappserver",
          "opprettetKilde": "EDAG",
          "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
          "endretTidspunkt": "2018-11-15T21:37:29.601",
          "endretAv": "srvappserver",
          "endretKilde": "EDAG",
          "endretKildereferanse": "eda00000-0000-0000-0000-001488805209"
        }
      }
    ],
    "innrapportertEtterAOrdningen": true,
    "registrert": "2015-01-23T07:49:27.94",
    "sistBekreftet": "2019-06-19T07:04:23",
    "sporingsinformasjon": {
      "opprettetTidspunkt": "2015-01-23T07:50:43.2",
      "opprettetAv": "srvappserver",
      "opprettetKilde": "EDAG",
      "opprettetKildereferanse": "c2b82646-0587-490c-9662-bab5dd31e9f5",
      "endretTidspunkt": "2019-06-19T07:21:31.491",
      "endretAv": "srvappserver",
      "endretKilde": "EDAG",
      "endretKildereferanse": "eda00000-0000-0000-0000-001908745214"
    }
  }
]
        """.trimIndent()

    private val queryMapping: MappingBuilder = WireMock.get(WireMock.urlPathEqualTo("/v1/arbeidstaker/arbeidsforhold"))
        .withHeader(HttpHeaders.Authorization, equalTo("Bearer dummytoken"))
        .withHeader("Nav-Personident", equalTo("26104635775"))
        .withHeader("Nav-Call-Id", equalTo("12345"))
        .withQueryParam("ansettelsesperiodeFom", equalTo("2010-01-01"))
        .withQueryParam("ansettelsesperiodeTom", equalTo("2016-01-01"))
        .withQueryParam("historikk", equalTo("true"))
        .withQueryParam("regelverk", equalTo("ALLE"))
}
