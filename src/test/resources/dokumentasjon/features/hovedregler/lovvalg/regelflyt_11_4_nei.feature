# language: no
# encoding: UTF-8

Egenskap: Regelflyt for reglene 11.4.1 for bruker som har ektefelle og barn. Ektefellen er ikke bosatt i Norge.

  Bakgrunn: Belgisk statsborger som er bosatt i Norge og som jobber i Norge
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 01.01.2000      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

  Scenariomal: Barnet er ikke bosatt i Norge
    Gitt følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

    Og følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 09069534888 |        | 18.07.2010      |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 10108000398 |        | 29.06.2015      |

    Og følgende barn i personhistorikk for ektefelle fra PDL
      | Ident       |
      | 09069534888 |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "11.2.2.1" gi svaret "<Regel 11.2.2.1>"

    Eksempler:
      | Stillingsprosent | Regel 11.2.2.1 | Medlemskap | Årsaker  |
      | 85               | Nei            | UAVKLART   | 11.2.2.1 |
      | 100              | Ja             | Ja         |          |

  Scenario: Regelflyt for regel 11.4: Barnet er bosatt i Norge
    Gitt følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 100              |

    Og følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 10108000398 |        | 29.06.2015      |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 09069534888 | NOR    | 18.07.2010      |

    Og følgende barn i personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 09069534888 | NOR    | 18.07.2010      |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "UAVKLART"
    Og skal regel-årsaker være "11.5.1"

    Og skal regel "11" gi svaret "Nei"
    Og skal regel "11.2" gi svaret "Ja"
    Og skal regel "11.3" gi svaret "Ja"
    Og skal regel "11.4" gi svaret "Nei"
    Og skal regel "11.4.1" gi svaret "Ja"
    Og skal regel "11.5.1" gi svaret "Ja"

  Scenario: Regelflyt for regel 11.4: Ett barn bosatt i Norge og ett som ikke er det
    Gitt følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 100              |

    Og følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |
      | 10079541651            | BARN                   | FAR                  |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 10108000398 |        | 29.06.2015      |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 09069534888 | NOR    | 18.07.2010      |
      | 10079541651 | USA    | 18.07.2010      |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "UAVKLART"
    Og skal regel "11" gi svaret "Nei"
    Og skal regel "11.2" gi svaret "Ja"
    Og skal regel "11.3" gi svaret "Ja"
    Og skal regel "11.4" gi svaret "Nei"
    Og skal regel "11.4.1" gi svaret "UAVKLART"
    Og skal regel "11.4.2" gi svaret "Ja"
