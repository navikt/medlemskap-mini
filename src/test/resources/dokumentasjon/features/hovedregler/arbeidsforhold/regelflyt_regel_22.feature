# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 22

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Stillingsprosent |
      | 01.01.2018      |                 | <Yrkeskode> | 100              |

  Scenariomal: Regelflyt regel 22: Bruker får "uavklart" på spørsmålet om medlemskap hvis det finnes utenlandsopphold

    Gitt følgende utenlandsopphold i arbeidsforholdet
      | Landkode   | Fra og med dato | Til og med dato | Rapporteringsperiode   |
      | <Landkode> | <Fra og med>    | <Til og med>    | <Rapporteringsperiode> |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "UAVKLART"
    Og skal regel "22" gi svaret "Ja"

    Eksempler:
      | Landkode | Fra og med | Til og med | Rapporteringsperiode |
      | FRA      | 01.01.2018 |            | 2019-07              |
      | JPN      |            |            | 2019-07              |


  Scenario: Regelflyt regel 22: bruker får "ja" på spørsmålet om medlemskap hvis det ikke finnes utenlandsopphold

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal regel "22" gi svaret "Nei"
