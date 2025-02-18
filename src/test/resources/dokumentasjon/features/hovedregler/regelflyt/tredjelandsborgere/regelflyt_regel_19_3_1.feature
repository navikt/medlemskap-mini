# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 19.3.1

  Bakgrunn:

    Gitt følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      |                 |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | organisasjonsnummer | STAT             | NOR      | 10             | STAT                |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til |
      | 10             | 10.10.1975                   | 01.08.2021                   |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 10.10.1975      | 01.08.2020      | yrkeskode | 100              |

  Scenariomal: Regelflyt for regel 19.3.1
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | USA      | 10.10.1975      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato     | Til og med dato     | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | <Arbeid fra og med> | <Arbeid til og med> | Organisasjon     | NORMALT             | 1               |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med    | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2017        | <Opphold til og med> | Ja             | PERMANENT | Nei                                      |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | 01.01.2017        |                   | Ja            | GENERELL          | KUN_ARBEID_HELTID    |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2018      | 12.02.2018      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "19.3.1" gi svaret "<Regel 19.3.1>"
    Og skal regel-årsaker være "<Årsaker>"

    Eksempler:
      | Opphold til og med | Arbeid fra og med | Arbeid til og med | Regel 19.3.1 | Medlemskap | Årsaker |
      | 20.03.2017         | 01.01.2017        |                   | Ja           | Ja         |         |
      | 20.01.2017         | 01.01.2017        | 12.02.2018        | Nei          | Uavklart   | 19.3.1  |
      | 20.01.2017         | 31.12.2016        | 12.02.2018        | Nei          | Uavklart   | 19.3.1  |