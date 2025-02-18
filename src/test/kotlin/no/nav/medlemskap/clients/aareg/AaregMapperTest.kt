package no.nav.medlemskap.clients.aareg

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.arbeidsforhold.*
import no.nav.medlemskap.services.aareg.mapAaregResultat
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth

class AaregMapperTest {

    @Test
    fun mapAaregResultatTilArbeidsforhold() {

        val aaRegArbeidsforhold = objectMapper.readValue<AaRegArbeidsforhold>(jsonStringAaRegArbeidsforholdList)

        val juridiskEnhetOrgnummerEnhetstype = mutableMapOf<String, String?>()
        juridiskEnhetOrgnummerEnhetstype["123456789"] = "AS"

        val arbeidsgiver = Arbeidsgiver(
            navn = "null",
            organisasjonsnummer = "985672744",
            ansatte = listOf(
                Ansatte(
                    antall = 5,
                    gyldighetsperiode = null
                )
            ),
            konkursStatus = null,
            juridiskeEnheter = listOf(
                JuridiskEnhet(
                    organisasjonsnummer = "123456789",
                    enhetstype = "AS",
                    antallAnsatte = 40
                )
            )
        )

        val mappedAaregResultatList = mapAaregResultat(listOf(aaRegArbeidsforhold), listOf(arbeidsgiver))
        Assert.assertEquals(1, mappedAaregResultatList.size)

        val mappedAaregResultat = mappedAaregResultatList.first()
        Assert.assertEquals("985672744", mappedAaregResultat.arbeidsgiver.organisasjonsnummer)
        Assert.assertEquals(5, mappedAaregResultat.arbeidsgiver.ansatte!!.first().antall)
        Assert.assertEquals(1, mappedAaregResultat.arbeidsgiver.juridiskeEnheter!!.size)
        Assert.assertEquals("123456789", mappedAaregResultat.arbeidsgiver.juridiskeEnheter!!.first()?.organisasjonsnummer)
        Assert.assertEquals("AS", mappedAaregResultat.arbeidsgiver.juridiskeEnheter!!.first()?.enhetstype)
        Assert.assertEquals(40, mappedAaregResultat.arbeidsgiver.juridiskeEnheter!!.first()?.antallAnsatte)

        Assert.assertEquals(1, mappedAaregResultat.arbeidsavtaler.size)
        Assert.assertEquals(LocalDate.parse("2018-09-13"), mappedAaregResultat.arbeidsavtaler.first().periode.fom)
        Assert.assertNull(mappedAaregResultat.arbeidsavtaler.first().periode.tom)
        Assert.assertEquals(null, mappedAaregResultat.arbeidsavtaler.first().skipsregister)
        Assert.assertEquals(100.0, mappedAaregResultat.arbeidsavtaler.first().stillingsprosent)
        Assert.assertEquals("8322108", mappedAaregResultat.arbeidsavtaler.first().yrkeskode)

        Assert.assertEquals("Organisasjon", mappedAaregResultat.arbeidsgivertype.name)
        Assert.assertEquals(Arbeidsforholdstype.NORMALT, mappedAaregResultat.arbeidsforholdstype)
        Assert.assertEquals(Periode(LocalDate.parse("2008-01-01"), LocalDate.parse("2018-10-30")), mappedAaregResultat.periode)

        Assert.assertEquals(1, mappedAaregResultat.utenlandsopphold?.size)
        Assert.assertEquals("SWE", mappedAaregResultat.utenlandsopphold?.first()?.landkode)
        Assert.assertEquals(Periode(LocalDate.parse("1975-10-10"), LocalDate.parse("2020-08-01")), mappedAaregResultat.utenlandsopphold?.first()?.periode)
        Assert.assertEquals(YearMonth.parse("2010-01"), mappedAaregResultat.utenlandsopphold?.first()?.rapporteringsperiode)

        Assert.assertEquals(Periode(LocalDate.parse("1975-10-10"), LocalDate.parse("2020-08-01")), mappedAaregResultat.permisjonPermittering?.get(0)?.periode)
        Assert.assertEquals("000000", mappedAaregResultat.permisjonPermittering?.get(0)?.permisjonPermitteringId)
        Assert.assertEquals(100.0, mappedAaregResultat.permisjonPermittering?.get(0)?.prosent)
        Assert.assertEquals(PermisjonPermitteringType.PERMISJON, mappedAaregResultat.permisjonPermittering?.get(0)?.type)
        Assert.assertEquals("PPIDHI", mappedAaregResultat.permisjonPermittering?.get(0)?.varslingskode)
    }

    private val jsonStringAaRegArbeidsforholdList =
        """
        
          {
            "ansettelsesperiode": {
              "bruksperiode": {
                "fom": "2018-11-15T21:37:40.835"
              },
              "periode": {
                "fom": "2008-01-01",
                "tom": "2018-10-30"
              },
              "sporingsinformasjon": {
                "endretAv": "srvappserver",
                "endretKilde": "EDAG",
                "endretTidspunkt": "2018-11-16T21:37:40.835",
                "opprettetAv": "srvappserver",
                "opprettetKilde": "EDAG",
                "opprettetKildereferanse": "eda00000-0000-0000-0000-001488880167",
                "opprettetTidspunkt": "2018-11-17T21:37:40.835"
              }
            },
            "antallTimerForTimeloennet": [
              {
                "antallTimer": 14.5,
                "periode": {},
                "rapporteringsperiode": "2016-08",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2016-08-22T13:14:58.704",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000136757537",
                  "opprettetTidspunkt": "2016-08-22T13:14:58.704"
                }
              },
              {
                "antallTimer": 37.43,
                "periode": {},
                "rapporteringsperiode": "2016-09",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2016-09-16T14:21:24.362",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000178501421",
                  "opprettetTidspunkt": "2016-09-16T14:21:24.362"
                }
              },
              {
                "antallTimer": 39.79,
                "periode": {},
                "rapporteringsperiode": "2016-10",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2016-10-20T13:20:30.450",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000227312938",
                  "opprettetTidspunkt": "2016-10-20T13:20:30.450"
                }
              },
              {
                "antallTimer": 26.97,
                "periode": {},
                "rapporteringsperiode": "2016-11",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2016-11-17T12:23:05.403",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000272667516",
                  "opprettetTidspunkt": "2016-11-17T12:23:05.403"
                }
              },
              {
                "antallTimer": 9.0,
                "periode": {},
                "rapporteringsperiode": "2016-12",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2016-12-27T09:49:55.437",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000350849717",
                  "opprettetTidspunkt": "2016-12-27T09:49:55.437"
                }
              },
              {
                "antallTimer": 50.48,
                "periode": {},
                "rapporteringsperiode": "2017-01",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-01-25T07:48:57.886",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000401682508",
                  "opprettetTidspunkt": "2017-01-25T07:48:57.886"
                }
              },
              {
                "antallTimer": 22.19,
                "periode": {},
                "rapporteringsperiode": "2017-02",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-02-21T13:05:58.300",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000453636343",
                  "opprettetTidspunkt": "2017-02-21T13:05:58.300"
                }
              },
              {
                "antallTimer": 19.82,
                "periode": {},
                "rapporteringsperiode": "2017-03",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-03-17T13:18:50.072",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000495210866",
                  "opprettetTidspunkt": "2017-03-17T13:18:50.072"
                }
              },
              {
                "antallTimer": 13.82,
                "periode": {},
                "rapporteringsperiode": "2017-04",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-04-25T09:25:53.627",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000544413562",
                  "opprettetTidspunkt": "2017-04-25T09:25:53.627"
                }
              },
              {
                "antallTimer": 19.13,
                "periode": {},
                "rapporteringsperiode": "2017-05",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-05-16T15:45:57.296",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000581854873",
                  "opprettetTidspunkt": "2017-05-16T15:45:57.296"
                }
              },
              {
                "antallTimer": 26.29,
                "periode": {},
                "rapporteringsperiode": "2017-06",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-06-22T11:52:53.968",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000630418558",
                  "opprettetTidspunkt": "2017-06-22T11:52:53.968"
                }
              },
              {
                "antallTimer": 18.35,
                "periode": {},
                "rapporteringsperiode": "2017-07",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-07-21T12:58:17.226",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000683087184",
                  "opprettetTidspunkt": "2017-07-21T12:58:17.226"
                }
              },
              {
                "antallTimer": 23.34,
                "periode": {},
                "rapporteringsperiode": "2017-08",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-08-24T12:52:40.183",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000729531602",
                  "opprettetTidspunkt": "2017-08-24T12:52:40.183"
                }
              },
              {
                "antallTimer": 39.56,
                "periode": {},
                "rapporteringsperiode": "2017-09",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2017-09-27T14:01:37.097",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-000786163023",
                  "opprettetTidspunkt": "2017-09-27T14:01:37.097"
                }
              },
              {
                "antallTimer": 13.96,
                "periode": {
                  "fom": "2017-09-01",
                  "tom": "2017-09-30"
                },
                "rapporteringsperiode": "2017-10",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:35:03.553",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488765214",
                  "opprettetTidspunkt": "2018-11-15T21:35:03.553"
                }
              },
              {
                "antallTimer": 29.39,
                "periode": {
                  "fom": "2017-10-01",
                  "tom": "2017-10-31"
                },
                "rapporteringsperiode": "2017-11",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:35:29.993",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488795152",
                  "opprettetTidspunkt": "2018-11-15T21:35:29.993"
                }
              },
              {
                "antallTimer": 33.19,
                "periode": {
                  "fom": "2017-11-01",
                  "tom": "2017-11-30"
                },
                "rapporteringsperiode": "2017-12",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:35:44.471",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488815605",
                  "opprettetTidspunkt": "2018-11-15T21:35:44.471"
                }
              },
              {
                "antallTimer": 17.26,
                "periode": {
                  "fom": "2017-12-01",
                  "tom": "2017-12-31"
                },
                "rapporteringsperiode": "2018-01",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:35:57.632",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488821672",
                  "opprettetTidspunkt": "2018-11-15T21:35:57.632"
                }
              },
              {
                "antallTimer": 0.9,
                "periode": {
                  "fom": "2017-12-01",
                  "tom": "2017-12-31"
                },
                "rapporteringsperiode": "2018-02",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:36:07.640",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488828150",
                  "opprettetTidspunkt": "2018-11-15T21:36:07.640"
                }
              },
              {
                "antallTimer": 28.82,
                "periode": {
                  "fom": "2018-01-01",
                  "tom": "2018-01-31"
                },
                "rapporteringsperiode": "2018-02",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:36:07.640",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488828150",
                  "opprettetTidspunkt": "2018-11-15T21:36:07.640"
                }
              },
              {
                "antallTimer": 26.31,
                "periode": {
                  "fom": "2018-02-01",
                  "tom": "2018-02-28"
                },
                "rapporteringsperiode": "2018-03",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:36:21.639",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488834422",
                  "opprettetTidspunkt": "2018-11-15T21:36:21.639"
                }
              },
              {
                "antallTimer": 10.66,
                "periode": {
                  "fom": "2018-03-01",
                  "tom": "2018-03-31"
                },
                "rapporteringsperiode": "2018-04",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:36:32.863",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488841070",
                  "opprettetTidspunkt": "2018-11-15T21:36:32.863"
                }
              },
              {
                "antallTimer": 19.18,
                "periode": {
                  "fom": "2018-04-01",
                  "tom": "2018-04-30"
                },
                "rapporteringsperiode": "2018-05",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:36:41.599",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488847492",
                  "opprettetTidspunkt": "2018-11-15T21:36:41.599"
                }
              },
              {
                "antallTimer": 23.04,
                "periode": {
                  "fom": "2018-05-01",
                  "tom": "2018-05-31"
                },
                "rapporteringsperiode": "2018-06",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:36:56.156",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488854725",
                  "opprettetTidspunkt": "2018-11-15T21:36:56.156"
                }
              },
              {
                "antallTimer": 35.9,
                "periode": {
                  "fom": "2018-06-01",
                  "tom": "2018-06-30"
                },
                "rapporteringsperiode": "2018-07",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:37:05.898",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488861234",
                  "opprettetTidspunkt": "2018-11-15T21:37:05.898"
                }
              },
              {
                "antallTimer": 9.62,
                "periode": {
                  "fom": "2018-08-01",
                  "tom": "2018-08-31"
                },
                "rapporteringsperiode": "2018-09",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:37:29.599",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
                  "opprettetTidspunkt": "2018-11-15T21:37:29.599"
                }
              },
              {
                "antallTimer": 0.0,
                "periode": {
                  "fom": "2018-05-01",
                  "tom": "2018-05-31"
                },
                "rapporteringsperiode": "2018-09",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:37:29.601",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
                  "opprettetTidspunkt": "2018-11-15T21:37:29.601"
                }
              },
              {
                "antallTimer": 0.0,
                "periode": {
                  "fom": "2018-04-01",
                  "tom": "2018-04-30"
                },
                "rapporteringsperiode": "2018-09",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:37:29.601",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
                  "opprettetTidspunkt": "2018-11-15T21:37:29.601"
                }
              },
              {
                "antallTimer": 0.0,
                "periode": {
                  "fom": "2018-06-01",
                  "tom": "2018-06-30"
                },
                "rapporteringsperiode": "2018-09",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-11-15T21:37:29.601",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001488805209",
                  "opprettetTidspunkt": "2018-11-15T21:37:29.601"
                }
              }
            ],
            "arbeidsavtaler": [
              {
                "antallTimerPrUke": 37.5,
                "arbeidstidsordning": "ikkeSkift",
                "beregnetAntallTimerPrUke": 37.5,
                "bruksperiode": {
                  "fom": "2018-09-13T10:31:09.343"
                },
                "gyldighetsperiode": {
                  "fom": "2018-04-01"
                },
                "sistLoennsendring": "2018-04-01",
                "sistStillingsendring": "2017-09-01",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2018-09-13T10:31:09.354",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "eda00000-0000-0000-0000-001385477431",
                  "opprettetTidspunkt": "2018-09-13T10:31:09.354"
                },
                "stillingsprosent": 100.0,
                "yrke": "8322108"
              }
            ],
            "arbeidsforholdId": "001234566788",
            "arbeidsgiver": {
              "organisasjonsnummer": "985672744",
              "type": "Organisasjon"
            },
            "arbeidstaker": {
              "aktoerId": "999990000",
              "offentligIdent": "01019199999",
              "type": "Person"
            },
            "innrapportertEtterAOrdningen": true,
            "navArbeidsforholdId": 123456780,
            "opplysningspliktig": {
              "organisasjonsnummer": "985615616",
              "type": "Organisasjon"
            },
            "registrert": "2015-01-23T07:49:27.940",
            "sistBekreftet": "2019-06-19T07:04:23.940",
            "sporingsinformasjon": {
              "endretAv": "srvappserver",
              "endretKilde": "EDAG",
              "endretTidspunkt": "2019-06-19T07:21:31.491",
              "opprettetAv": "srvappserver",
              "opprettetKilde": "EDAG",
              "opprettetKildereferanse": "c2b82646-0587-490c-9662-bab5dd31e9f5",
              "opprettetTidspunkt": "2015-01-23T07:50:43.200"
            },
            "type": "ordinaertArbeidsforhold",
            "utenlandsopphold" : [ {
                "landkode" : "SWE",
                "periode" : {
                  "fom" : "1975-10-10",
                  "tom" : "2020-08-01"
                },
                "rapporteringsperiode" : "2010-01",
                "sporingsinformasjon": {
                  "endretAv": "srvappserver",
                  "endretKilde": "EDAG",
                  "endretTidspunkt": "2019-06-19T07:21:31.491",
                  "opprettetAv": "srvappserver",
                  "opprettetKilde": "EDAG",
                  "opprettetKildereferanse": "c2b82646-0587-490c-9662-bab5dd31e9f5",
                  "opprettetTidspunkt": "2015-01-23T07:50:43.200"
                }
            }], 
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
            ]
          }
        
        """.trimIndent()
}
