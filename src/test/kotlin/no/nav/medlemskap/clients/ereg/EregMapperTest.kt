package no.nav.medlemskap.clients.ereg

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.services.ereg.mapOrganisasjonTilArbeidsgiver
import org.junit.Assert
import org.junit.jupiter.api.Test
import java.time.LocalDate

class EregMapperTest {

    @Test
    fun mapOrganisasjonTilArbeidsgiver() {

        val organisasjon = objectMapper.readValue<Organisasjon>(eregHentOrganisasjonResponse)

        val mappedArbeidsgiverFraOrganisasjon = mapOrganisasjonTilArbeidsgiver(organisasjon, null)

        Assert.assertEquals(1, mappedArbeidsgiverFraOrganisasjon.ansatte?.size)
        Assert.assertEquals(19, mappedArbeidsgiverFraOrganisasjon.ansatte?.first()?.antall)
        Assert.assertEquals(LocalDate.parse("2020-07-13"), mappedArbeidsgiverFraOrganisasjon.ansatte?.first()?.gyldighetsperiode?.fom)
        Assert.assertNull(mappedArbeidsgiverFraOrganisasjon.ansatte?.first()?.gyldighetsperiode?.tom)
        Assert.assertEquals("975016684", mappedArbeidsgiverFraOrganisasjon.organisasjonsnummer)
        Assert.assertNull(mappedArbeidsgiverFraOrganisasjon.konkursStatus)
        Assert.assertNull(mappedArbeidsgiverFraOrganisasjon.juridiskeEnheter)
    }

    @Test
    fun mapOrganisasjonSomInngaarIJuridiskEnhetTilArbeidsgiver() {

        val organisasjonMedTilknyttetJuridiskEnhet = objectMapper.readValue<Organisasjon>(eregHentOrganisasjonResponseMedInngaarIJuridiskEnhet)
        val tilknyttetJuridiskEnhet = objectMapper.readValue<Organisasjon>(juridiskEnhetResponse)

        val mappedArbeidsgiverFraOrganisasjon = mapOrganisasjonTilArbeidsgiver(organisasjonMedTilknyttetJuridiskEnhet, listOf(tilknyttetJuridiskEnhet))

        Assert.assertEquals("873159642", mappedArbeidsgiverFraOrganisasjon.organisasjonsnummer)
        Assert.assertEquals(1, mappedArbeidsgiverFraOrganisasjon.juridiskeEnheter?.size)
        Assert.assertEquals("989077457", mappedArbeidsgiverFraOrganisasjon.juridiskeEnheter?.first()?.organisasjonsnummer)
        Assert.assertEquals(80, mappedArbeidsgiverFraOrganisasjon.juridiskeEnheter?.first()?.antallAnsatte)
        Assert.assertEquals("FLI", mappedArbeidsgiverFraOrganisasjon.juridiskeEnheter?.first()?.enhetstype)
    }

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

    private val eregHentOrganisasjonResponseMedInngaarIJuridiskEnhet =
        """
        {
          "organisasjonsnummer": "873159642",
          "type": "Virksomhet",
          "navn": {
            "redigertnavn": "DANIELSEN VIDEREGÅENDE SKOLE",
            "navnelinje1": "DANIELSEN VIDEREGÅENDE SKOLE",
            "bruksperiode": {
              "fom": "2015-02-23T08:04:53.2"
            },
            "gyldighetsperiode": {
              "fom": "2006-02-28"
            }
          },
          "organisasjonDetaljer": {
            "registreringsdato": "1995-02-23T00:00:00",
            "enhetstyper": [
              {
                "enhetstype": "BEDR",
                "bruksperiode": {
                  "fom": "2014-05-21T14:55:08.721"
                },
                "gyldighetsperiode": {
                  "fom": "1995-02-23"
                }
              }
            ],
            "naeringer": [
              {
                "naeringskode": "85.310",
                "hjelpeenhet": false,
                "bruksperiode": {
                  "fom": "2014-05-22T00:40:59.636"
                },
                "gyldighetsperiode": {
                  "fom": "1978-01-25"
                }
              }
            ],
            "navn": [
              {
                "redigertnavn": "DANIELSEN VIDEREGÅENDE SKOLE",
                "navnelinje1": "DANIELSEN VIDEREGÅENDE SKOLE",
                "bruksperiode": {
                  "fom": "2015-02-23T08:04:53.2"
                },
                "gyldighetsperiode": {
                  "fom": "2006-02-28"
                }
              }
            ],
            "forretningsadresser": [
              {
                "type": "Forretningsadresse",
                "adresselinje1": "Nygaten 8",
                "postnummer": "5017",
                "landkode": "NO",
                "kommunenummer": "4601",
                "bruksperiode": {
                  "fom": "2020-01-02T13:49:58.654"
                },
                "gyldighetsperiode": {
                  "fom": "2019-12-31"
                }
              }
            ],
            "internettadresser": [
              {
                "adresse": "www.danielsen-skoler.no",
                "bruksperiode": {
                  "fom": "2016-04-08T04:02:56.831"
                },
                "gyldighetsperiode": {
                  "fom": "2016-04-07"
                }
              }
            ],
            "epostadresser": [
              {
                "adresse": "danadm@danielsen-skoler.no",
                "bruksperiode": {
                  "fom": "2014-05-21T18:44:03.138"
                },
                "gyldighetsperiode": {
                  "fom": "2014-03-18"
                }
              }
            ],
            "telefonnummer": [
              {
                "nummer": "55 55 98 00",
                "telefontype": "TFON",
                "bruksperiode": {
                  "fom": "2014-05-21T18:44:03.138"
                },
                "gyldighetsperiode": {
                  "fom": "2014-03-18"
                }
              }
            ],
            "telefaksnummer": [
              {
                "nummer": "55 55 98 20",
                "bruksperiode": {},
                "gyldighetsperiode": {}
              }
            ],
            "sistEndret": "2020-07-13",
            "dubletter": [
              {
                "organisasjonsnummer": "971561610",
                "navn": {
                  "redigertnavn": "DANIELSEN VIDEREGÅENDE SKOLE",
                  "navnelinje1": "DANIELSEN VIDEREGÅENDE SKOLE",
                  "bruksperiode": {
                    "fom": "2015-02-23T08:04:53.2"
                  },
                  "gyldighetsperiode": {
                    "fom": "1996-12-06"
                  }
                },
                "type": "Organisasjon"
              }
            ],
            "ansatte": [
              {
                "antall": 80,
                "bruksperiode": {
                  "fom": "2020-07-16T00:42:47.399"
                },
                "gyldighetsperiode": {
                  "fom": "2020-07-13"
                }
              }
            ]
          },
          "virksomhetDetaljer": {
            "enhetstype": "BEDR",
            "oppstartsdato": "1965-12-31",
            "eierskiftedato": "2006-01-01"
          },
          "inngaarIJuridiskEnheter": [
            {
              "organisasjonsnummer": "989077457",
              "navn": {
                "redigertnavn": "FORENINGEN DANIELSEN VIDEREGÅENDE SK",
                "navnelinje1": "FORENINGEN DANIELSEN VIDEREGÅENDE",
                "navnelinje2": "SKOLE",
                "bruksperiode": {
                  "fom": "2015-02-23T08:04:53.2"
                },
                "gyldighetsperiode": {
                  "fom": "2005-12-21"
                }
              },
              "bruksperiode": {
                "fom": "2014-05-23T15:17:54.466"
              },
              "gyldighetsperiode": {
                "fom": "2006-02-27"
              }
            }
          ]
        }
        
        """.trimIndent()

    private val juridiskEnhetResponse =
        """
        {
          "organisasjonsnummer": "989077457",
          "type": "JuridiskEnhet",
          "navn": {
            "redigertnavn": "FORENINGEN DANIELSEN VIDEREGÅENDE SK",
            "navnelinje1": "FORENINGEN DANIELSEN VIDEREGÅENDE",
            "navnelinje2": "SKOLE",
            "bruksperiode": {
              "fom": "2015-02-23T08:04:53.2"
            },
            "gyldighetsperiode": {
              "fom": "2005-12-21"
            }
          },
          "organisasjonDetaljer": {
            "registreringsdato": "2005-12-21T00:00:00",
            "stiftelsesdato": "2005-12-05",
            "enhetstyper": [
              {
                "enhetstype": "FLI",
                "bruksperiode": {
                  "fom": "2014-05-21T21:46:40.332"
                },
                "gyldighetsperiode": {
                  "fom": "2005-12-21"
                }
              }
            ],
            "naeringer": [
              {
                "naeringskode": "85.310",
                "hjelpeenhet": false,
                "bruksperiode": {
                  "fom": "2014-05-22T01:14:37.814"
                },
                "gyldighetsperiode": {
                  "fom": "2006-02-27"
                }
              }
            ],
            "navn": [
              {
                "redigertnavn": "FORENINGEN DANIELSEN VIDEREGÅENDE SK",
                "navnelinje1": "FORENINGEN DANIELSEN VIDEREGÅENDE",
                "navnelinje2": "SKOLE",
                "bruksperiode": {
                  "fom": "2015-02-23T08:04:53.2"
                },
                "gyldighetsperiode": {
                  "fom": "2005-12-21"
                }
              }
            ],
            "forretningsadresser": [
              {
                "type": "Forretningsadresse",
                "adresselinje1": "Nygaten 8",
                "postnummer": "5017",
                "landkode": "NO",
                "kommunenummer": "4601",
                "bruksperiode": {
                  "fom": "2020-01-03T03:54:18.704"
                },
                "gyldighetsperiode": {
                  "fom": "2019-12-31"
                }
              }
            ],
            "internettadresser": [
              {
                "adresse": "www.danielsen-skoler.no",
                "bruksperiode": {
                  "fom": "2016-04-08T04:03:16.13"
                },
                "gyldighetsperiode": {
                  "fom": "2016-04-07"
                }
              }
            ],
            "epostadresser": [
              {
                "adresse": "danadm@danielsen-skoler.no",
                "bruksperiode": {
                  "fom": "2016-04-08T04:03:16.128"
                },
                "gyldighetsperiode": {
                  "fom": "2016-04-07"
                }
              }
            ],
            "telefonnummer": [
              {
                "nummer": "55 55 98 00",
                "telefontype": "TFON",
                "bruksperiode": {
                  "fom": "2014-05-21T21:46:40.332"
                },
                "gyldighetsperiode": {
                  "fom": "2014-03-18"
                }
              }
            ],
            "telefaksnummer": [
              {
                "nummer": "55 55 98 20",
                "bruksperiode": {},
                "gyldighetsperiode": {}
              }
            ],
            "formaal": [
              {
                "formaal": "Undervisning på videregående nivå",
                "bruksperiode": {
                  "fom": "2014-05-22T00:18:59.579"
                },
                "gyldighetsperiode": {
                  "fom": "2014-03-18"
                }
              }
            ],
            "maalform": "NB",
            "sistEndret": "2020-07-13",
            "ansatte": [
              {
                "antall": 80,
                "bruksperiode": {
                  "fom": "2020-07-16T00:55:03.03"
                },
                "gyldighetsperiode": {
                  "fom": "2020-07-13"
                }
              }
            ]
          },
          "juridiskEnhetDetaljer": {
            "enhetstype": "FLI",
            "sektorkode": "7000"
          },
          "driverVirksomheter": [
            {
              "organisasjonsnummer": "873159642",
              "navn": {
                "redigertnavn": "DANIELSEN VIDEREGÅENDE SKOLE",
                "navnelinje1": "DANIELSEN VIDEREGÅENDE SKOLE",
                "bruksperiode": {
                  "fom": "2015-02-23T08:04:53.2"
                },
                "gyldighetsperiode": {
                  "fom": "2006-02-28"
                }
              },
              "bruksperiode": {
                "fom": "2014-05-23T15:17:54.466"
              },
              "gyldighetsperiode": {
                "fom": "2006-02-27"
              }
            }
          ]
        }
        """.trimIndent()
}
