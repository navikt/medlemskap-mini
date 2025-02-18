# language: no
# encoding: UTF-8

Egenskap: Borgere i EØS omfattes av grunnforordningen

  Scenariomal: Regel 2: EØS-borgere omfattes av grunnforordningen

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | <Land>   | 30.01.2000      |                 |

    Når regel "2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse     |
      | 15.01.2020      | 30.01.2020      | Nei                           | SYKEPENGER |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Land | Svar |
      | NOR  | Ja   |
      | FRA  | Ja   |
      | USA  | Nei  |

  Scenario: Regel 2: Person med bare amerikansk statsborgerskap omfattes ikke av grunnforordningen

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | USA      | 30.01.2000      |                 |

    Når regel "2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "Nei"
