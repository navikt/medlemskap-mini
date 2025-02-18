# language: no
# encoding: UTF-8

Egenskap: Regelflyt for reglene 11.2.x for bruker som ikke har ektefelle i PDL

  Bakgrunn: Belgisk statsborger som er bosatt i Norge
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

  Scenariomal: Bruker uten ektefelle må ha stillingsprosent 100 eller mer for å få "Ja"
    Gitt følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

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
      | 120              | Ja             | Ja         |          |

  Scenariomal: Bruker uten ektefelle med barn må ha minst 80 % stilling for å få "Ja"
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Fra og med dato | Til og med dato |
      | 09069534888 | NOR    | 18.07.2010      |                 |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "11" gi svaret "Nei"
    Og skal regel "11.2" gi svaret "Nei"
    Og skal regel "11.2.1" gi svaret "Ja"
    Og skal regel "11.2.2" gi svaret "Ja"
    Og skal regel "11.2.3" gi svaret "<Regel 11.2.3>"

    Eksempler:
      | Stillingsprosent | Regel 11.2.3 | Medlemskap | Årsaker |
      | 79               | Nei          | UAVKLART   | 11.2.3  |
      | 81               | Ja           | Ja         |         |

  Scenario: Bruker med ett barn som bor i Norge og ett barn som ikke bor i Norge skal få "UAVKLART"
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |
      | 10079541651            | BARN                   | FAR                  |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 09069534888 | NOR    | 18.07.2010      |
      | 10079541651 | SWE    | 18.07.2010      |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 80               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "UAVKLART"
    Og skal regel-årsaker være "11.2.2"

    Og skal regel "11.2.2" gi svaret "UAVKLART"
    Og skal regel "11.2.2.2" gi svaret "Ja"

  Scenariomal: Bruker uten ektefelle og med barn som ikke bor i Norge må ha minst 100 % stilling for å få "Ja"
    Gitt følgende familerelasjoner i personhistorikk fra PDL
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 09069534888            | BARN                   | FAR                  |

    Og følgende personhistorikk for barn fra PDL
      | Ident       | Fra og med dato | Til og med dato |
      | 09069534888 | 18.07.2010      |                 |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "11.2.2.1" gi svaret "<Regel 11.2.2.1>"

    Eksempler:
      | Stillingsprosent | Regel 11.2.2.1 | Medlemskap | Årsaker  |
      | 99               | Nei            | UAVKLART   | 11.2.2.1 |
      | 100              | Ja             | Ja         |          |
