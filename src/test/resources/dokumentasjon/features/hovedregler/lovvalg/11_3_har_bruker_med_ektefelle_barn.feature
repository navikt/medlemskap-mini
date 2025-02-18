# language: no
# encoding: UTF-8

Egenskap: Regel 11.3: Har bruker barn i PDL?

  Scenario: Regel 11.3 - Bruker har barn
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident   | Relatert persons rolle | Min rolle for person |
      | <Relatert persons ident> | BARN                   | FAR                  |

    Og følgende personhistorikk for barn fra PDL
      | Ident       |
      | 25079528660 |

    Når regel "11.3" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"


  Scenario: Regel 11.3 - Hvis bruker ikke har barn skal svaret være "Nei"
    Når regel "11.3" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
