# language: no
# encoding: UTF-8

Egenskap: Regel 27: Når bruker har to statsborgerskap og det ene er norsk, har bruker nylig blitt norsk statsborger?

  Scenariomal: Regel 27 - Bruker har nylig blitt norsk statsborger
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | FRA      |                 |                 |
      | NOR      | <Fra og med>    | <Til og med>    |

    Når regel "27" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Fra og med | Til og med | Svar |
      | 01.12.2019 |            | Ja   |
      | 01.01.2017 |            | Nei  |

  Scenario: Regel 27 - bruker som ikke har norsk statsborgerskap får "Nei"
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | FRA      | 01.01.2000      |                 |

    Når regel "27" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
