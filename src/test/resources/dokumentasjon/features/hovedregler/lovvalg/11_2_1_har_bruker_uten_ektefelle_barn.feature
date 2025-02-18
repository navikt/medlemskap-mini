# language: no
# encoding: UTF-8

Egenskap: Regel 11.2.1: Har bruker barn i PDL?
  Scenario: Regel 11.2.1 - Barn som har fødselsnummer med 5 nuller
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 2507900000             | BARN                   | FAR                  |

    Når regel "11.2.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"


  Scenario: Regel 11.2.1 - Hvis bruker ikke har barn skal svaret være "Nei"
    Når regel "11.2.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId