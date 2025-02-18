# language: no
# encoding: UTF-8

Egenskap: Regel 11.6.1: Har ektefelle til bruker vært i 100% stilling siste 12 mnd?

  Scenariomal: Regel 11.6.1
    Gitt følgende arbeidsforhold til ektefelle fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsavtaler til ektefelle i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

    Når regel "11.6.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Stillingsprosent | Svar |
      | 0                | Nei  |
      | 99               | Nei  |
      | 100              | Ja   |
      | 110              | Ja   |