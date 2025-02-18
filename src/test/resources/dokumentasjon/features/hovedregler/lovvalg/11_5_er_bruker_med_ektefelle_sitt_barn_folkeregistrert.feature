# language: no
# encoding: UTF-8

Egenskap: Regel 11.5: Er brukers barn folkeregistrert som bosatt i Norge?

  Scenariomal: Regel 11.5 - Er bruker med ektefelle sitt barn folkeregistrert?
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Kontaktadresse | Oppholdsadresse | Fra og med dato | Til og med dato |
      | 10108000398 | NOR    |                |                 | 18.07.2010      | 15.05.2019      |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Kontaktadresse   | Oppholdsadresse   | Fra og med dato | Til og med dato |
      | 09069534888 | NOR    | <Kontaktadresse> | <Oppholdsadresse> | 18.07.2010      | 15.05.2019      |

    Når regel "11.5" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Kontaktadresse | Oppholdsadresse | Svar |
      |                |                 | Ja   |
      | NOR            | NOR             | Ja   |
      | FRA            | NOR             | Nei  |
      | NOR            | FRA             | Nei  |
      | FRA            | FRA             | Nei  |

  Scenariomal: Regel 11.5 - Uavklart hvis ett eller flere barn bor i Norge og ett eller flere barn ikke bor i Norge
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |
      | 10079541651            | BARN                   | FAR                  |


    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Kontaktadresse   | Fra og med dato |
      | 09069534888 | NOR    | <Kontaktadresse> | 18.07.2010      |
      | 10079541651 | NOR    |                  | 18.07.2010      |

    Når regel "11.5" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Kontaktadresse | Svar     |
      |                | Ja       |
      | NOR            | Ja       |
      | FRA            | UAVKLART |
