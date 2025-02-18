# language: no
# encoding: UTF-8

Egenskap: Regel 14: Er bruker ansatt i staten eller i en kommune?

  Scenario: Regel 14 - Offentlig stilling med mindre enn 25 %
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | STAT             | NOR      | 9              | STAT                |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 20               |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"


  Scenario: Regel 14 - Offentlig stilling med mer enn 25 %
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | STAT             | NOR      | 9              | STAT                |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 80               |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"

  Scenario: Regel 14 - Stilling uten statlig eller kommunal juridisk enhetstype
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | STAT             | NOR      | 9              | AS                  |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"

  Scenario: Regel 14 - Stilling uten juridisk enhetstype
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | STAT             | NOR      | 9              |                     |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"


  Scenariomal: Regel 14 - Stilling med statlig eller kommunal juridisk enhetstype
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype   |
      | 1             | STAT             | NOR      | 9              | <Juridisk enhetstype> |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"

    Eksempler:
      | Juridisk enhetstype |
      | STAT                |
      | KOMM                |
      | FKF                 |
      | FYLK                |
      | KF                  |
      | SF                  |
      | SÆR                 |


  Scenario: Regel 14 - To parallelle stillinger hos forskjellige arbeidsgivere
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforhold 1
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | BEDR             | NOR      | 9              | STAT                |

    Og følgende arbeidsavtaler i arbeidsforhold 1
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 25               |               |

    Og følgende arbeidsgiver i arbeidsforhold 2
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | BEDR             | NOR      | 9              | AS                  |

    Og følgende arbeidsavtaler i arbeidsforhold 2
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"


  Scenario: Regel 14 - To statlige stillinger etter hverandre
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2019      | 01.01.2020      | Organisasjon     | NORMALT             |
      | 01.01.2019      | 01.01.2020      | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforhold 1
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 1             | BEDR             | NOR      | 9              | STAT                |

    Og følgende arbeidsavtaler i arbeidsforhold 1
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2019      | 01.06.2019      | 001       | 25               |               |

    Og følgende arbeidsgiver i arbeidsforhold 2
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | 2             | BEDR             | NOR      | 9              | STAT                |

    Og følgende arbeidsavtaler i arbeidsforhold 2
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.06.2019      | 01.01.2020      | 002       | 25               |               |

    Når regel "14" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 01.01.2021      | Nei                           |

    Så skal svaret være "Ja"
