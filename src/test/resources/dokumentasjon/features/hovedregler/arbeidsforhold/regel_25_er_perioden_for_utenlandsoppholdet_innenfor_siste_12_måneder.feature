# language: no
# encoding: UTF-8

Egenskap: Regel 25: Er perioden for utenlandsoppholdet innenfor siste 12 måneder?

  Scenariomal: Bruker med periode i utenlandsoppholdet innenfor siste 12 måneder får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      | 30.06.2019      | Organisasjon     | NORMALT             |

    Og følgende utenlandsopphold i arbeidsforholdet
      | Landkode | Fra og med dato | Til og med dato | Rapporteringsperiode |
      | JPN      | <Fra og med>    | <Til og med>    | 2019-06              |

    Når regel "25" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2019      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Fra og med | Til og med | Svar |
      | 01.05.2018 | 30.05.2018 | Ja   |
      | 15.01.2019 | 30.02.2019 | Ja   |
      | 01.01.2017 | 30.01.2017 | Nei  |
      |            |            | Nei  |
      | 01.01.2017 |            | Nei  |
      |            | 01.01.2017 | Nei  |
