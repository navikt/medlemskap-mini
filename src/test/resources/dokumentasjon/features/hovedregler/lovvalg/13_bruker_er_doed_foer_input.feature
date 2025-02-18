# language: no
# encoding: UTF-8

Egenskap: Regel 13: Er bruker død?

  Scenariomal: Regel 13 - Bruker er død før input

    Gitt følgende opplysninger om dødsfall i personhistorikken:
      | Dødsdato   |
      | <Dødsdato> |

    Og følgende bostedsadresser i personhistorikken
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

    Når regel "13" kjøres med følgende parametre, skal svaret være "<Svar>"
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Eksempler:
      | Dødsdato   | Svar       |
      | 23.10.2019 | UAVKLART |
      | 23.10.2020 | UAVKLART |




