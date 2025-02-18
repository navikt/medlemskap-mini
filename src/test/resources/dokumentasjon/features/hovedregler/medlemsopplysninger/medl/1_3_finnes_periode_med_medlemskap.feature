# language: no
# encoding: UTF-8

Egenskap: Regel 1.3: Er det periode med medlemskap innenfor 12 mnd?

  Scenariomal: Regel 1.3: Er det periode med medlemskap innenfor 12 mnd?

    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato   | Er medlem   | Lovvalg | Lovvalgsland | Periodestatus |
      |         | 01.01.2019      | <Til og med dato> | <Er medlem> | ENDL    | NOR          | GYLD          |

    Når regel "1.3" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Er medlem | Til og med dato | Svar |
      | Ja        | 01.06.2020      | Ja   |
      | Ja        | 02.02.2020      | Ja   |
      | Ja        | 02.02.9999      | Ja   |
      | Nei       | 01.06.2020      | Nei  |
