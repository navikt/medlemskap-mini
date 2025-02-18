# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 11, er bruker norsk statsborger?

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

  Scenariomal: Regelflyt for regel 11, er bruker norsk statsborger?

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | <Land>   | 20.02.2000      |                 |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "11" gi svaret "<Regel 11>"
    Og skal regel-årsaker være "<Årsaker>"

    Eksempler:
      | Land | Regel 11 | Medlemskap | Årsaker |
      | FRA  | Nei      | Ja         |         |
      | NOR  | Ja       | Ja         |         |


  Scenario: Regelflyt for regel 11, hvis bruker ikke er EØS-borger skal ikke regel 11 kjøres

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | <Land>   | 20.02.2000      |                 |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "UAVKLART"
    Og skal regel "11" ikke finnes i resultatet


