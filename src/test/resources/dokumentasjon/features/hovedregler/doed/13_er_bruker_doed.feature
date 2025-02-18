# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 13

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

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Stillingsprosent |
      | 01.01.2018      |                 | <Yrkeskode> | 100              |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

  Scenariomal: Regelflyt for regel 13

    Gitt følgende opplysninger om dødsfall i personhistorikken:
      | Dødsdato   |
      | <Dødsdato> |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"

    Og skal regel "13" gi svaret "<Regel 13>"


    Eksempler:
      | Dødsdato   | Regel 13 | Medlemskap |
      | 23.10.2022 | Ja       | Ja         |






