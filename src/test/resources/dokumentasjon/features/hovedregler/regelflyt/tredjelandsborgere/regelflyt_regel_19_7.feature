# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 19.7 og regel 9

  Bakgrunn:

    Gitt følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      |                 |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2017        | 20.01.2017        | Ja             | PERMANENT | Nei                                      |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | 01.01.2017        |                   | Ja            | GENERELL          | KUN_ARBEID_HELTID    |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2017      | 30.01.2017      | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte |
      | organisasjonsnummer | STAT             | NOR      | 10             |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til |
      | 10             | 10.10.1975                   | 01.08.2021                   |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 10.10.1975      | 01.08.2021      | yrkeskode | 100              |

  Scenariomal: Regelflyt for regel 19.7 og regel 9
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 10.10.1975      |                 |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 05.01.2021      | 12.02.2021      | <Arbeid utenfor Norge>        |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "19.7" gi svaret "<Regel 19.7>"

    Eksempler:
      | Statsborgerskap | Regel 19.7 | Arbeid utenfor Norge | Medlemskap |
      | RUS             | Nei        | Nei                  | Uavklart   |
      | USA             | Nei        | Nei                  | Uavklart   |
      | RUS             | Nei        | Ja                   | Uavklart   |
      | USA             | Nei        | Ja                   | Uavklart   |
      | GBR             | Ja         | Nei                  | Uavklart   |
      | GBR             | Ja         | Ja                   | Uavklart   |