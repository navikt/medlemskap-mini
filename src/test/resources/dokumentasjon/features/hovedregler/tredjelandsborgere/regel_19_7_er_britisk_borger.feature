# language: no
# encoding: UTF-8

Egenskap: Regel 19.7: Er bruker britisk borger?

  Scenariomal: Regel 19.7 - Er bruker britisk borger?
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.2000      |                 |

    Når regel "19.7" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Statsborgerskap | Svar |
      | NOR             | Nei  |
      | FRA             | Nei  |
      | USA             | Nei  |
      | CHE             | Nei  |
      | GBR             | Ja   |


  Scenariomal: Regel 19.7 - Forskjellige statsborgerskap siste 12 måneder
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.1995      | 31.10.2019      |
      | GBR               | 01.11.2019      |                 |

    Når regel "19.7" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Statsborgerskap | Svar |
      | GBR             | Ja   |
      | CHE             | Nei  |
      | FRA             | Nei  |
      | NOR             | Nei  |


  Scenario: Regel 19.7 - Parallelle statsborgerskap siste 12 måneder
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | GBR      | 01.01.2000      |                 |
      | NOR      | 01.12.2005      |                 |

    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
