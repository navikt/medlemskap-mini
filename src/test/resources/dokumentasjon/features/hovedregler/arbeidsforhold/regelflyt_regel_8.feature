# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 8

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |


  Scenariomal: Regelflyt regel 8: Bruker får "ja" på spørsmålet om medlemskap hvis han er pilot eller kabinansatt

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Stillingsprosent |
      | 01.01.2018      |                 | <Yrkeskode> | 100              |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel-årsaker være "<Årsaker>"
    Og skal regel "8" gi svaret "<Regel 8>"

    Eksempler:
      | Yrkeskode | Regel 8 | Medlemskap | Årsaker |
      | 001       | Nei     | Ja         |         |
      | 5111105   | Ja      | UAVKLART   |8        |