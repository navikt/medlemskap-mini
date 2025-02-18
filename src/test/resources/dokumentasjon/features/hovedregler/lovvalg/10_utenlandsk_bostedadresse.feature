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

    Eksempler:
      | Bor i land | Post land | Svar |
      | FRA        | NOR       | NEI  |
      | NOR        | FRA       | NEI  |
      | NOR        | NOR       | JA   |
