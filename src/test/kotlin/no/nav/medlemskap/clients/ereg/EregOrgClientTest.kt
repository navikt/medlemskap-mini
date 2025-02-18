package no.nav.medlemskap.clients.ereg

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.clients.sts.StsRestClient
import no.nav.medlemskap.common.cioHttpClient
import no.nav.medlemskap.config.Configuration
import org.junit.jupiter.api.*

class EregOrgClientTest {

    companion object {
        val server: WireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
        val httpClient = cioHttpClient

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

        val stsClient: StsRestClient = mockk()
        coEvery { stsClient.oidcToken() } returns "dummytoken"

        WireMock.stubFor(
            queryMapping.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.OK.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    .withBody(eregResponse)
            )
        )

        val client = EregClient(server.baseUrl(), httpClient, config, "123")

        val response = runBlocking { client.hentOrganisasjon("977074010", callId) }
        println(response)
        Assertions.assertEquals(response.navn?.navnelinje1, "NAV FAMILIE- OG PENSJONSYTELSER")
    }

    private val config = Configuration()

    private val eregResponse =
        """
       {
  "navn": {
    "bruksperiode": {
      "fom": "2015-01-06T21:44:04.748",
      "tom": "2015-12-06T19:45:04"
    },
    "gyldighetsperiode": {
      "fom": "2014-07-01",
      "tom": "2015-12-31"
    },
    "navnelinje1": "NAV FAMILIE- OG PENSJONSYTELSER",
    "navnelinje2": "string",
    "navnelinje3": "string",
    "navnelinje4": "string",
    "navnelinje5": "string",
    "redigertnavn": "NAV FAMILIE- OG PENSJONSYTELSER OSL"
  },
  "organisasjonDetaljer": {
    "ansatte": [
      {
        "antall": 123,
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        }
      }
    ],
    "dubletter": [
      null
    ],
    "enhetstyper": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "enhetstype": "BEDR",
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        }
      }
    ],
    "epostadresser": [
      {
        "adresse": "post@organisasjon.no",
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        }
      }
    ],
    "formaal": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "formaal": "Veivedlikehold, vaktmestertjenester, transport.",
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        }
      }
    ],
    "forretningsadresser": [
      {
        "adresselinje1": "string",
        "adresselinje2": "string",
        "adresselinje3": "string",
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "kommunenummer": "0301",
        "landkode": "JPN",
        "postnummer": "0557",
        "poststed": "string"
      }
    ],
    "hjemlandregistre": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "navn1": "string",
        "navn2": "string",
        "navn3": "string",
        "postadresse": {
          "adresselinje1": "string",
          "adresselinje2": "string",
          "adresselinje3": "string",
          "bruksperiode": {
            "fom": "2015-01-06T21:44:04.748",
            "tom": "2015-12-06T19:45:04"
          },
          "gyldighetsperiode": {
            "fom": "2014-07-01",
            "tom": "2015-12-31"
          },
          "kommunenummer": "0301",
          "landkode": "JPN",
          "postnummer": "0557",
          "poststed": "string"
        },
        "registernummer": "0932568"
      }
    ],
    "internettadresser": [
      {
        "adresse": "www.nav.no",
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        }
      }
    ],
    "maalform": "NB",
    "mobiltelefonnummer": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "nummer": "21 07 00 00",
        "telefontype": "TFON"
      }
    ],
    "naeringer": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "hjelpeenhet": false,
        "naeringskode": 62.03
      }
    ],
    "navSpesifikkInformasjon": {
      "bruksperiode": {
        "fom": "2015-01-06T21:44:04.748",
        "tom": "2015-12-06T19:45:04"
      },
      "erIA": true,
      "gyldighetsperiode": {
        "fom": "2014-07-01",
        "tom": "2015-12-31"
      }
    },
    "navn": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "navnelinje1": "NAV FAMILIE- OG PENSJONSYTELSER",
        "navnelinje2": "string",
        "navnelinje3": "string",
        "navnelinje4": "string",
        "navnelinje5": "string",
        "redigertnavn": "NAV FAMILIE- OG PENSJONSYTELSER OSL"
      }
    ],
    "opphoersdato": "2016-12-31",
    "postadresser": [
      {
        "adresselinje1": "string",
        "adresselinje2": "string",
        "adresselinje3": "string",
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "kommunenummer": "0301",
        "landkode": "JPN",
        "postnummer": "0557",
        "poststed": "string"
      }
    ],
    "registreringsdato": "2014-07-15T12:10:58.059",
    "registrertMVA": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "registrertIMVA": true
      }
    ],
    "sistEndret": "2015-01-13",
    "statuser": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "kode": "KONK"
      }
    ],
    "stiftelsesdato": "2014-07-15",
    "telefaksnummer": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "nummer": "21 07 00 00",
        "telefontype": "TFON"
      }
    ],
    "telefonnummer": [
      {
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "nummer": "21 07 00 00",
        "telefontype": "TFON"
      }
    ],
    "underlagtHjemlandLovgivningForetaksform": [
      {
        "beskrivelseHjemland": "string",
        "beskrivelseNorge": "string",
        "bruksperiode": {
          "fom": "2015-01-06T21:44:04.748",
          "tom": "2015-12-06T19:45:04"
        },
        "foretaksform": "LTD",
        "gyldighetsperiode": {
          "fom": "2014-07-01",
          "tom": "2015-12-31"
        },
        "landkode": "GB"
      }
    ]
  },
  "organisasjonsnummer": 990983666,
  "type": "Virksomhet"
}
        """.trimIndent()

    private val orgnummer = "977074010"
    private val queryMapping: MappingBuilder = WireMock.get(WireMock.urlPathEqualTo("/v1/organisasjon/$orgnummer"))
        .withHeader("Nav-Call-Id", WireMock.equalTo("12345"))
        .withHeader("Nav-Consumer-Id", WireMock.equalTo("test"))
}
