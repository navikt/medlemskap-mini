# language: no
# encoding: UTF-8

Egenskap: Regel 1.2: Er det periode både med og uten medlemskap innenfor 12 mnd?

  Scenario: Regel 1.2: Er det periode både med og uten medlemskap innenfor 12 mnd?

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | 01.01.2019      | 01.04.2019      | Ja        | ENDL    | NOR          | GYLD          |
      | 01.05.2019      | 01.08.2019      | Ja        |         | NOR          | GYLD          |
      | 02.08.2019      | 31.12.2019      | Nei       | ENDL    | NOR          | GYLD          |

    Når regel "1.2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenario: Regel 1.2: Er det periode både med og uten medlemskap innenfor 12 mnd?

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | 01.01.2019      | 01.06.2019      | Ja        | ENDL    | NOR          | GYLD          |
      | 02.06.2019      | 31.12.2019      | Ja        | ENDL    | NOR          | GYLD          |

    Når regel "1.2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "Nei"

  Scenario: Periode både med medlemskap og tomt lovvalg(type) og uten medlemskap innenfor 12 mnd?

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland |
      | 01.05.2017      | 01.08.2019      | Ja        |         | NOR          |
      | 01.09.2019      | 01.10.2019      | Ja        | FORL    | NOR          |
      | 02.08.2019      | 31.12.2019      | Nei       | ENDL    | NOR          |

    Når regel "1.2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "Ja"

  Scenario: Periode både med medlemskap og uten medlemskap med uavklart status innenfor 12 mnd skal gi nei

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | 01.05.2019      | 01.08.2019      | Ja        | ENDL    | NOR          | GYLD          |
      | 02.08.2019      | 31.12.2019      | Nei       | ENDL    | NOR          | UAVK          |

    Når regel "1.2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "Nei"