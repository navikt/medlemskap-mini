# language: no
# encoding: UTF-8

Egenskap: Regelflyt for reglene 11.3.x for bruker som har ektefelle, men ikke barn

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

  Scenariomal: Bruker med ektefelle bosatt i Norge får "Ja"
    Gitt følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Fra og med dato |
      | 10108000398 | NOR    | 29.06.2015      |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "11.3.1" gi svaret "Ja"
    Og skal regel "11.6" gi svaret "<Regel 11.6>"

    Eksempler:
      | Stillingsprosent | Regel 11.6 | Medlemskap | Årsaker |
      | 79               | Nei        | UAVKLART   | 11.6.1  |
      | 80               | Ja         | Ja         |         |
      | 85               | Ja         | Ja         |         |

  Scenariomal: Bruker med ektefelle som ikke er bosatt i Norge
    Gitt følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Fra og med dato |
      | 10108000398 | 29.06.2015      |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "11.3.1" gi svaret "Nei"
    Og skal regel "11.3.1.1" gi svaret "<Regel 11.3.1.1>"

    Eksempler:
      | Stillingsprosent | Regel 11.3.1.1 | Medlemskap | Årsaker  |
      | 99               | Nei            | UAVKLART   | 11.3.1.1 |
      | 100              | Ja             | Ja         |          |
      | 105              | Ja             | Ja         |          |
