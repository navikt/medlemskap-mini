# language: no
# encoding: UTF-8

Egenskap: Regel 19.5: Er arbeidsagang uavklart?

  Scenariomal: Regel 19.5 - er arbeidsadgang uavklart?

    Gitt følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | ArbeidsadgangType   | ArbeidomfangKategori | Arbeidsadgang |
      | 12.02.2019        | 12.02.2022        | <ArbeidsadgangType> | KUN_ARBEID_HELTID    | Ja            |

    Når regel "19.5" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | ArbeidsadgangType            | Svar |
      | BESTEMT_ARBEID_ELLER_OPPDRAG | Nei  |
      | UAVKLART                     | Ja   |