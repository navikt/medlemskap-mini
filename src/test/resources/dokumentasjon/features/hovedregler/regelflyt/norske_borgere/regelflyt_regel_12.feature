# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 12
  Bruker som er folkeregistrert (regel 10), er norsk borger (regel 11) og har jobb i Norge (regel 12)

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |

    Og følgende kontaktadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

  Scenariomal: Regel 12: Bruker får ja på spørsmålet om medlemskap, hvis stillingsprosent er over 25 %, uavklart ellers
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   | Skipsregister |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse   |
      | 30.01.2020      | 30.01.2021      | <Jobbet utenfor Norge>        | <Ytelse> |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "10" gi svaret "Ja"
    Og skal regel "12" gi svaret "<Regel 12>"

    Eksempler:
      | Ytelse     | Stillingsprosent | Jobbet utenfor Norge | Regel 12 | Medlemskap | Årsaker | Forklaring          |
      | SYKEPENGER | 100              | Nei                  | Ja       | Ja         |         |                     |
      | DAGPENGER  | 20               | Nei                  | Nei      | UAVKLART   | 12      |                     |
      | DAGPENGER  | 25               | Nei                  | Ja       | Ja         |         |                     |
