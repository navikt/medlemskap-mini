# language: no
# encoding: UTF-8

Egenskap: Regel 10: Bruker med flere typer adresser i kombinasjon med historiske adresser

  Scenariomal: Bruker har folkeregistrert flere postadresser og bostedsadresser hvor tom-dato er null

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode     | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR          | 01.01.2000      | 31.12.2000      | Ja        |
      | Oslo    | FRA          | 01.01.2001      | 31.12.2001      | Ja        |
      | Oslo    | <Bor i land> | 01.01.2002      |                 | Nei       |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode    | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | <Post land> | 01.01.2000      |                 | Nei       |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 14.01.2021      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Bor i land | Post land | Svar |
      | NOR        | NOR       | Ja   |
      | FRA        | NOR       | Nei  |


  Scenario: Bruker har norsk bostedsadresse og en utenlandsk kontaktadresse som er historisk og uten periode skal regel 10 gi ja

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 01.01.2000      | 31.12.2000      | Ja        |
      | Oslo    | FRA      | 01.01.2001      | 31.12.2001      | Ja        |
      | Oslo    | NOR      | 01.01.2002      |                 | Nei       |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | SWE      |                 |                 | Ja        |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 14.01.2021      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId


  Scenario: Bruker har norsk bostedsadresse og en utenlandsk kontaktadresse som er historisk og med fom-dato før 2017 og uten tom-periode skal gi ja

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 01.01.2000      | 31.12.2000      | Ja        |
      | Oslo    | FRA      | 01.01.2001      | 31.12.2001      | Ja        |
      | Oslo    | NOR      | 01.01.2002      |                 | Nei       |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | SWE      | 31.12.2016      |                 | Ja        |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 14.01.2021      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenario: Bruker har norsk bostedsadresse og to kontaktadresser hvorav en er utenlandsk, historisk og uten periode, skal gi Ja

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 01.01.2000      | 31.12.2000      | Ja        |
      | Oslo    | FRA      | 01.01.2001      | 31.12.2017      | Ja        |
      | Oslo    | NOR      | 01.01.2018      |                 | Nei       |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | USA      |                 |                 | Ja        |
      | Oslo    | NOR      | 01.01.2018      |                 | Nei       |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 14.01.2021      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenario: Bruker har norsk bostedsadresse og to kontaktadresser hvorav en er utenlandsk, historisk og uten periode, skal gi Ja

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 01.01.2000      | 31.12.2000      | Ja        |
      | Oslo    | FRA      | 01.01.2001      | 31.12.2017      | Ja        |
      | Oslo    | NOR      | 01.01.2018      |                 | Nei       |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | USA      |                 |                 | Ja        |
      | Oslo    | NOR      | 01.01.2018      |                 | Nei       |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 14.01.2021      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenario: Bruker har norsk bostedsadresse, kontaktadresser og oppholdsadresse, hvorav en kontaktadresse er utenlandsk i kontrollperiode

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 01.01.2000      |                 | Nei       |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 02.09.2019      |                 | Ja        |
      | Oslo    | LTU      | 02.09.2019      | 17.03.2020      | Ja        |
      | Oslo    | NOR      | 17.03.2020      | 10.02.2020      | Nei       |

    Gitt følgende oppholdsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato | Historisk |
      | Oslo    | NOR      | 02.09.2019      | 16.03.2020      | Ja        |
      | Oslo    | NOR      | 17.03.2020      |                 | Nei       |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 21.12.2020      | 25.12.2020      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId