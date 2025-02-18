# language: no
# encoding: UTF-8

Egenskap: Regel 21: Er bruker arbeidstaker i kontrollperiode for stønadsområde?

  Regel: Regel 21: Er bruker arbeidstaker i kontrollperiode for stønadsområde?

    Scenariomal: Person som har jobbet sammenhengende de siste 28 dagene får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |

      Når regel "21" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"
      Og skal avklaringen være som definert i RegelId
      Og skal begrunnelsen være som definert i RegelId

      Eksempler:
        | Fra og med | Til og med | Svar |
        | 01.01.2018 |            | Ja   |
        | 01.01.2018 | 01.02.2018 | Nei  |


    Scenariomal: Person med to arbeidsforhold som har jobbet sammenhengende de siste 28 dagene får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato     | Til og med dato     | Arbeidsgivertype | Arbeidsforholdstype |
        | <Første fra og med> | <Første til og med> | Organisasjon     | NORMALT             |
        | <Andre fra og med>  | <Andre til og med>  | Organisasjon     | NORMALT             |

      Når regel "21" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 10.06.2020      | 10.06.2020      | Nei                           |

      Så skal svaret være "<Svar>"
      Og skal avklaringen være som definert i RegelId
      Og skal begrunnelsen være som definert i RegelId

      Eksempler:
        | Første fra og med | Første til og med | Andre fra og med | Andre til og med | Svar |
        | 01.01.2020        |                   | 01.01.2020       |                  | Ja   |
        | 01.01.2020        | 01.06.2020        | 01.06.2020       |                  | Ja   |
        | 01.01.2020        | 01.06.2020        | 04.06.2020       |                  | Nei  |