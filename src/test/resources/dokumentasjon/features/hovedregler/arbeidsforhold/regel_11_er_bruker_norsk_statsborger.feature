# language: no
# encoding: UTF-8

Egenskap: Regel 11: Har bruker norsk statsborgerskap?

  Scenario: Bruker har norsk statsborgerskap
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |
    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |
    Så skal svaret være "Ja"

  Scenario: Bruker har ikke norsk statsborgerskap
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse   | Landkode | Fra og med dato | Til og med dato |
      | Stockholm | SWE      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | SWE      | 01.01.2000      |                 |
    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |
    Så skal svaret være "Nei"

  Scenario: Bruker har hatt norsk statsborgerskap tidligere
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      | 01.01.2020      |
    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |
    Så skal svaret være "Nei"

  Scenario: Bruker har dobbelt statsborgerskap
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse   | Landkode | Fra og med dato | Til og med dato |
      | Oslo      | NOR      | 01.01.2000      |                 |
      | Stockholm | SWE      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |
      | SWE      | 01.01.2000      |                 |
    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |
    Så skal svaret være "Ja"

  Scenario: USA
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Seattle | USA      | 01.01.2000      |                 |
      | Oslo    | NOR      | 30.01.2020      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.02.2019      |                 |
      | USA      | 01.01.2020      |                 |
    Når regel "11" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |
    Så skal svaret være "Nei"


