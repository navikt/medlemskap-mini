# language: no
# encoding: UTF-8

Egenskap: Man er medlem i Folketrygden hvis man er EØS-borger og bor og jobber i Norge

  Scenario: Norsk statsborger som bor og jobber i Norge

    Gitt følgende datagrunnlag json
"""
{
  "fnr": "",
  "periode": {
    "fom": "2020-01-30",
    "tom": "2020-02-28"
  },
  "brukerinput": {
    "arbeidUtenforNorge": false
  },
  "pdlpersonhistorikk": {
     "statsborgerskap": [
      {
        "landkode": "BEL",
        "fom": "2016-01-30",
        "tom": "2020-01-30",
        "historisk": false
      }
    ],
    "bostedsadresser": [
       {
        "landkode": "NOR",
        "fom": "2020-01-01",
        "tom": null
        }
    ],
    "doedsfall": [],
    "kontaktadresser": [],
    "oppholdsadresser": [],
    "sivilstand": [],
    "forelderBarnRelasjon": [],
    "innflyttingTilNorge": [],
    "utflyttingFraNorge": [],
    "navn": []
  },
  "medlemskap": [],
  "arbeidsforhold": [
    {
      "periode": {
        "fom": "2018-01-01",
        "tom": "2022-06-30"
      },
      "utenlandsopphold": [],
      "arbeidsgivertype": "Organisasjon",
      "arbeidsgiver": {
        "type": "BEDR",
        "identifikator": "1",
        "antallAnsatte": "6"
      },
      "arbeidsforholdstype": "NORMALT",
      "arbeidsavtaler": [
        {
          "periode": {
            "fom": "2018-01-30",
            "tom": "2020-06-30"
          },
          "gyldighetsperiode": {
            "fom": "2018-01-30",
            "tom": "2020-06-30"
          },
          "yrkeskode": "0001"
        }
      ]
    }
  ],
  "inntekt": [],
  "oppgaver": [],
  "dokument": [],
  "ytelse": "SYKEPENGER"
}
"""

    Når lovvalg og medlemskap beregnes fra datagrunnlag json

    Så skal medlemskap i Folketrygden være "Ja"
    Og omfattet av grunnforordningen være "Ja"
