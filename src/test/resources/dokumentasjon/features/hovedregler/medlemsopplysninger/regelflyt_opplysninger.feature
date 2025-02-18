# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel opplysninger

  Bakgrunn:

    Gitt følgende journalposter fra Joark
      | JournalpostId | Tittel | JournalpostType | Journalstatus | Tema |
      | Id            | Tittel | Posttype        | Status        | MED  |

    Og følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      | 01.08.2020      |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      | 01.08.2020      |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 10.10.1975      | 01.08.2020      | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte |
      | organisasjonsnummer | STAT             | NOR      | 10             |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til |
      | 10             | 10.10.1975                   | 01.08.2020                   |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 10.10.1975      | 01.08.2020      | yrkeskode | 100              |

  Scenario: Opplysninger i Joark
    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2019      | 31.12.2019      | Nei                           |

    Så skal svaret være "UAVKLART"
    Og skal regel-årsaker være "OPPLYSNINGER-JOARK"