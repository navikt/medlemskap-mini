# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 27, Når bruker har to statsborgerskap og det ene er norsk, har bruker nylig blitt norsk statsborger??

  Bakgrunn:

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 100              |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2019        |                   | Ja             | PERMANENT | Nei                                      |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | 01.01.2019        |                   | Ja            | GENERELL          | KUN_ARBEID_HELTID    |

  Scenariomal: Regelflyt for regel 27, Når bruker har to statsborgerskap og det ene er norsk, har bruker nylig blitt norsk statsborger?

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato | Historisk   |
      | JPN      |                 |                 |             |
      | NOR      | <Fra og med>    | <Til og med>    | <Historisk> |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal regel "27" gi svaret "<Regel 27>"
    Og skal svaret være "<Svar>"

    Eksempler:
      | Fra og med | Til og med | Historisk | Svar     | Regel 27 |
      | 20.02.2019 |            | Nei       | UAVKLART | Ja       |
      | 20.02.2019 |            | Ja        | Ja       | Nei      |
      | 01.05.2020 |            | Nei       | UAVKLART | Ja       |


  Scenario: Regelflyt for regel 27, tredjelandsborger

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | JPN      | 20.02.2016      |                 |


    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal regel "27" gi svaret "Nei"

