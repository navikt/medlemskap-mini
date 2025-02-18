# language: no
# encoding: UTF-8

Egenskap: Regel 31: Har bruker permanent oppholdstillatelse?

  Scenariomal: Regel 31 - Har bruker permanent oppholdstillatelse?
    Gitt følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type   | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2019        |                   | JA             | <Type> | Nei                                      |

    Når regel "31" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Type        | Svar |
      | MIDLERTIDIG | Nei  |
      | PERMANENT   | Ja   |
