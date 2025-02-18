# language: no
# encoding: UTF-8

Egenskap: Regel 18: Er bruker hovedsaklig arbeidstaker?

  Regel: Regel 18: Er bruker hovedsaklig arbeidstaker?

    Scenariomal: Person som har jobbet sammenhengende mer enn 51 % i perioden får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 01.01.2018      |                 | Organisasjon     | NORMALT             |

      Og følgende arbeidsgiver i arbeidsforholdet
        | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
        | 1             | BEDR             | NOR      | 9              |

      Og følgende arbeidsavtaler i arbeidsforholdet
        | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
        | 01.01.2018      |                 | 001       | <Stillingsprosent> |

      Når regel "18" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"
      Og skal avklaringen være som definert i RegelId
      Og skal begrunnelsen være som definert i RegelId

      Eksempler:
        | Stillingsprosent | Svar |
        | 52               | Ja   |
        | 51               | Ja   |
        | 50               | Nei  |
        | 49               | Nei  |


    Scenariomal: Person som har gjennomsnittlig stilingsprosent over 51 % i perioden får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 01.01.2018      |                 | Organisasjon     | NORMALT             |

      Og følgende arbeidsgiver i arbeidsforholdet
        | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
        | 1             | BEDR             | NOR      | 9              |

      Og følgende arbeidsavtaler i arbeidsforholdet
        | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
        | 01.01.2018      | 15.05.2019      | 001       | <Stillingsprosent> |
        | 05.09.2019      |                 | 001       | 100                |

      Når regel "18" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Stillingsprosent | Svar |
        | 52               | Ja   |
        | 40               | Ja   |
        | 30               | Nei  |


    Scenario: Person uten arbeidsforhold får "Nei"

      Når regel "18" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "Nei"

