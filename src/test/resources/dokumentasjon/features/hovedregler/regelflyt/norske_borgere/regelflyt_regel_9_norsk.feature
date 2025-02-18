# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 9, for norske borgere
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

  Scenariomal: Regelflyt for regel 9, for norske borgere: Forskjellige svar på spørsmålet "Har hatt arbeid utenfor Norge"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 25               |               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge   | Ytelse   |
      | 30.01.2020      | 30.01.2021      | <Har hatt arbeid utenfor Norge> | <Ytelse> |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"

    Og skal regel "9" gi svaret "<Regel 9>"

    Eksempler:
      | Ytelse     | Har hatt arbeid utenfor Norge | Regel 9 | Medlemskap | Årsaker |
      | DAGPENGER  | Nei                           | Nei     | Ja         |         |
      | DAGPENGER  | Ja                            | Ja      | UAVKLART   | 9       |
      | SYKEPENGER | Ja                            | Ja      | UAVKLART   | 9       |


  Scenariomal: Regelflyt for regel 9, for norske borgere: nye brukerspørsmål skal overstyre regel 9
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 25               |               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Har oppholdstillatelse   |
      | 30.01.2020      | 30.01.2021      | Ja                            | <Har oppholdstillatelse> |

    Så skal svaret være "<Medlemskap>"

    Og skal regel "9" gi svaret "<Regel 9>"

    Eksempler:
      | Har oppholdstillatelse | Regel 9 | Medlemskap |
      | Ja                     | Nei     | Ja         |
      |                        | Ja      | UAVKLART   |
      |                        | Ja      | UAVKLART   |

