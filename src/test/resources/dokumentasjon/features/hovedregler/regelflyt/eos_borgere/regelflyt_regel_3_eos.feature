# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 3, for EØS-borgere

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 01.01.2000      |                 |

  Scenariomal: Regelflyt regel 3 for EØS-borgere

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato   | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | <Fra og med dato> |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 100              |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse   |
      | 30.01.2020      | 30.01.2021      | Nei                           | <Ytelse> |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "3" gi svaret "<Regel 3>"

    Eksempler:
      | Ytelse     | Fra og med dato | Regel 3 | Medlemskap |
      | SYKEPENGER | 01.01.2018      | Ja      | Ja         |
      | SYKEPENGER | 02.02.2019      | Nei     | Uavklart   |
      | DAGPENGER  | 02.02.2019      | Nei     | Uavklart   |
