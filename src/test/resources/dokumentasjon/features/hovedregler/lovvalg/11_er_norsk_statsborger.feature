# language: no
# encoding: UTF-8

Egenskap: Regel 11: Er bruker norsk statborger?

  Scenariomal: Regel 11 - Samme statsborgerskap siste 12 måneder
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.2000      |                 |

    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Statsborgerskap | Svar |
      | NOR             | Ja   |
      | FRA             | Nei  |

  Scenariomal: Regel 11 - Forskjellige statsborgerskap siste 12 måneder
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.1995      | 31.10.2019      |
      | FRA               | 01.11.2019      | 30.11.2019      |
      | NOR               | 01.12.2019      |                 |

    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Statsborgerskap | Svar |
      | NOR             | Ja   |
      | FRA             | Nei  |

  Scenario: Regel 11 - Parallelle statsborgerskap siste 12 måneder
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | FRA      | 01.01.2000      |                 |
      | NOR      | 01.12.2005      |                 |

    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
