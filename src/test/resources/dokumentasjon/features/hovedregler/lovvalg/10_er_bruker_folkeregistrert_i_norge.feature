# language: no
# encoding: UTF-8

Egenskap: Regel 10: Er bruker folkeregistert i Norge?

  Scenariomal: Bruker har folkeregistrert postadresse og bostedsadresse

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode     | Fra og med dato | Til og med dato |
      | Oslo    | <Bor i land> | 01.01.2000      |                 |

    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode    | Fra og med dato | Til og med dato |
      | Oslo    | <Post land> | 01.01.2000      |                 |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Bor i land | Post land | Svar |
      | NOR        | NOR       | Ja   |
      | NOR        | FRA       | Nei  |


  Scenariomal: Ingen postadresse, men bostedsadresse

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | XXX     | <Land>   | 01.01.2000      |                 |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Land | Svar |
      | NOR  | Ja   |
      | FRA  | NEI  |

  Scenariomal: Postadresse, men ikke bostedsadresse
    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | XXX     | <Land>   | 01.01.2000      |                 |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Land | Svar |
      | NOR  | Nei  |
      | FRA  | Nei  |

  Scenario: Norsk bostedsadresse, men midlertidig utenlandsadresse

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | XXX     | NOR      | 01.01.2000      |                 |

    Og følgende oppholdsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | XXX     | FRA      | 01.01.2000      |                 |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"

  Scenario: Adresser der "Til og med dato" er før "Fra og med dato"

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | XXX     | NOR      | 05.02.2000      | 01.02.2000      |

    Og følgende oppholdsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | XXX     | FRA      | 05.02.2000      | 01.02.2000      |

    Når regel "10" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"

