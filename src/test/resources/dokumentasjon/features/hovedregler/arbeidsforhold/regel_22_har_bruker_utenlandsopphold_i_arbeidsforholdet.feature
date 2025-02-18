# language: no
# encoding: UTF-8

Egenskap: Regel 22: Er det registrert utenlandsopphold i et arbeidsforhold bruker har vært i siste 12 måneder?

  Scenariomal: Bruker med utenlandsopphold i arbeidsforholdet i kontrollperioden får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Og følgende utenlandsopphold i arbeidsforholdet
      | Landkode | Fra og med dato | Til og med dato | Rapporteringsperiode |
      | FRA      |                 |                 | 2019-07              |

    Når regel "22" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2019      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Fra og med | Til og med | Svar |
      | 01.01.2018 | 30.06.2019 | Ja   |
      | 29.01.2016 | 29.06.2017 | Nei  |

  Scenario: Bruker uten utenlandsopphold får "Nei"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Når regel "22" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2029      | 30.01.2020      | Nei                           |

    Så skal svaret være "Nei"