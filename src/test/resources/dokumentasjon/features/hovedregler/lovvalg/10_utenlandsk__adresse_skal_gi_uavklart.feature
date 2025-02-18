# language: no
# encoding: UTF-8

Egenskap: Regel 10: Hvis postadresse eller midlertidig adresse er utenlandsk, så skal medlemsvalget være uavklart

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

  Scenariomal: Regel 10: Hvis postadresse eller midlertidig adresse er utenlandsk, så skal medlemsvalget være uavklart
    Gitt følgende kontaktadresser i personhistorikken
      | Adresse | Landkode        | Fra og med dato | Til og med dato |
      | Oslo    | <Post landkode> | 01.01.2000      |                 |

    Og følgende oppholdsadresser i personhistorikken
      | Adresse | Landkode               | Fra og med dato | Til og med dato |
      | Oslo    | <Midlertidig landkode> | 01.01.2000      |                 |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal regel "10" gi svaret "<Regel 10>"

    Eksempler:
      | Post landkode | Midlertidig landkode | Svar     | Regel 10 |
      | NOR           | NOR                  | Ja       | Ja       |
      | NOR           | FRA                  | UAVKLART | Nei      |
      | FRA           | NOR                  | UAVKLART | Nei      |