package no.nav.medlemskap.clients.ereg

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.clients.sts.StsRestClient
import no.nav.medlemskap.common.cioHttpClient
import no.nav.medlemskap.config.Configuration
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EregClientTest {

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
    fun `tester response med juridisk enhet`() {
        val callId = "12345"

        val stsClient: StsRestClient = mockk()
        coEvery { stsClient.oidcToken() } returns "dummytoken"

        WireMock.stubFor(
            queryMappingForHentOrganisasjon.willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatusCode.OK.value)
                    .withHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    .withTransformerParameter("inkluderHierarki", true)
                    .withBody(eregHentOrganisasjonResponse)
            )
        )

        val client = EregClient(server.baseUrl(), httpClient, config, "123")

        val response = runBlocking { client.hentOrganisasjon("977074010", callId) }
        assertEquals(1, response.getOrganisasjonsnumreJuridiskeEnheter().size)
        assertEquals("975016684", response.organisasjonsnummer)
        assertEquals("Virksomhet", response.type)
        assertEquals("BEDR", response.organisasjonDetaljer?.enhetstyper?.get(0)?.enhetstype)
        assertEquals(19, response.organisasjonDetaljer?.ansatte?.get(0)?.antall)
    }

    private val config = Configuration(
        azureAd = Configuration.AzureAd(azureAppWellKnownUrl = "http://localhost")
    )
    private val orgnummer = "977074010"

    private val queryMappingForHentOrganisasjon: MappingBuilder = WireMock.get(WireMock.urlPathEqualTo("/v1/organisasjon/$orgnummer"))
        .withHeader("Nav-Call-Id", equalTo("12345"))
        .withHeader("Nav-Consumer-Id", equalTo("test"))

    private val eregHentOrganisasjonResponse =
        """
        {
          "organisasjonsnummer": "975016684",
          "type": "Virksomhet",
          "navn": {
            "redigertnavn": "FROGNER BARNEHAGE",
            "navnelinje1": "FROGNER BARNEHAGE",
            "bruksperiode": {
              "fom": "2015-02-23T08:04:53.2"
            },
            "gyldighetsperiode": {
              "fom": "1998-04-21"
            }
          },
          "organisasjonDetaljer": {
            "registreringsdato": "1998-04-21T00:00:00",
            "enhetstyper": [
              {
                "enhetstype": "BEDR",
                "bruksperiode": {
                  "fom": "2014-05-21T15:21:17.549"
                },
                "gyldighetsperiode": {
                  "fom": "1998-04-21"
                }
              }
            ],
            "naeringer": [
              {
                "naeringskode": "88.911",
                "hjelpeenhet": false,
                "bruksperiode": {
                  "fom": "2014-05-22T00:58:27.229"
                },
                "gyldighetsperiode": {
                  "fom": "2003-01-13"
                }
              }
            ],
            "navn": [
              {
                "redigertnavn": "FROGNER BARNEHAGE",
                "navnelinje1": "FROGNER BARNEHAGE",
                "bruksperiode": {
                  "fom": "2015-02-23T08:04:53.2"
                },
                "gyldighetsperiode": {
                  "fom": "1998-04-21"
                }
              }
            ],
            "forretningsadresser": [
              {
                "type": "Forretningsadresse",
                "adresselinje1": "Professor Dahls gate 44",
                "postnummer": "0260",
                "landkode": "NO",
                "kommunenummer": "0301",
                "bruksperiode": {
                  "fom": "2015-02-23T10:38:34.403"
                },
                "gyldighetsperiode": {
                  "fom": "2010-08-12"
                }
              }
            ],
            "epostadresser": [
              {
                "adresse": "frogner@bfr.oslo.kommune.no",
                "bruksperiode": {
                  "fom": "2016-09-13T04:04:24.12"
                },
                "gyldighetsperiode": {
                  "fom": "2016-09-12"
                }
              }
            ],
            "sistEndret": "2020-07-13",
            "ansatte": [
              {
                "antall": 19,
                "bruksperiode": {
                  "fom": "2020-07-16T00:51:50.236"
                },
                "gyldighetsperiode": {
                  "fom": "2020-07-13"
                }
              }
            ]
          },
          "virksomhetDetaljer": {
            "enhetstype": "BEDR",
            "oppstartsdato": "1982-01-01",
            "eierskiftedato": "2002-06-30"
          },
          "bestaarAvOrganisasjonsledd": [
            {
              "organisasjonsledd": {
                "organisasjonsnummer": "874778702",
                "type": "Organisasjonsledd",
                "navn": {
                  "redigertnavn": "OSLO KOMMUNE BYDEL 5 FROGNER",
                  "navnelinje1": "OSLO KOMMUNE BYDEL 5 FROGNER",
                  "bruksperiode": {
                    "fom": "2015-02-23T08:04:53.2"
                  },
                  "gyldighetsperiode": {
                    "fom": "2004-02-06"
                  }
                },
                "organisasjonsleddOver": [
                  {
                    "organisasjonsledd": {
                      "organisasjonsnummer": "876819872",
                      "type": "Organisasjonsledd",
                      "navn": {
                        "redigertnavn": "OSLO KOMMUNE, BYRÅDSAVDELING FOR",
                        "navnelinje1": "OSLO KOMMUNE, BYRÅDSAVDELING FOR",
                        "navnelinje2": "HELSE, ELDRE OG INNBYGGERTJENESTER",
                        "bruksperiode": {
                          "fom": "2020-07-03T04:00:45.796"
                        },
                        "gyldighetsperiode": {
                          "fom": "2020-07-02"
                        }
                      },
                      "inngaarIJuridiskEnheter": [
                        {
                          "organisasjonsnummer": "958935420",
                          "navn": {
                            "redigertnavn": "OSLO KOMMUNE",
                            "navnelinje1": "OSLO KOMMUNE",
                            "bruksperiode": {
                              "fom": "2015-02-23T08:04:53.2"
                            },
                            "gyldighetsperiode": {
                              "fom": "1997-08-30"
                            }
                          },
                          "bruksperiode": {
                            "fom": "2014-05-23T15:24:51.336"
                          },
                          "gyldighetsperiode": {
                            "fom": "1997-10-29"
                          }
                        }
                      ]
                    },
                    "bruksperiode": {
                      "fom": "2014-05-23T15:22:19.065"
                    },
                    "gyldighetsperiode": {
                      "fom": "1998-08-19"
                    }
                  }
                ]
              },
              "bruksperiode": {
                "fom": "2014-05-23T17:30:16.985"
              },
              "gyldighetsperiode": {
                "fom": "2002-08-21"
              }
            }
          ]
        }
        """.trimIndent()
}
