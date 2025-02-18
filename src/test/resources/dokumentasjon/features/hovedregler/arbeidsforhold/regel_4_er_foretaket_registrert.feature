# language: no
# encoding: UTF-8

Egenskap: Regel 4: Er foretaket registrert i Foretaksregisteret?

  Scenariomal: Regel 4: Er foretaket registrert i Foretaksregisteret?
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype   | Arbeidsforholdstype |
      | 01.01.2018      |                 | <Arbeidsgivertype> | NORMALT             |

    Når regel "4" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Arbeidsgivertype | Svar |
      | Organisasjon     | Ja   |
      | Person           | Nei  |


  Scenario: Regel 4: Regelen skal svar "Nei" hvis det ikke er noen arbeidsforhold

    Når regel "4" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

