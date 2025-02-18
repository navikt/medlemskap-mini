# language: no
# encoding: UTF-8

Egenskap: Regel 12: Har bruker vært i minst 25 % stilling siste 12 måneder?

  Scenario: Regel 12 - Stilling med mindre enn 25 %
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 20               |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId


  Scenario: Regel 12 - En stilling med mer enn 25 %
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 80               |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenariomal: Regel 12 - To stillinger hos samme arbeidsgiver
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   | Skipsregister |
      | 01.01.2018      |                 | 001       | 10                 |               |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Stillingsprosent | Svar |
      | 0                | Nei  |
      | 5                | Nei  |
      | 15               | Ja   |
      | 20               | Ja   |


  Scenariomal: Regel 12 - To stillinger etter hverandre hos samme arbeidsgiver, siste 12 måneder
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   | Skipsregister |
      | 01.01.2018      |                 | 001       | 10                 |               |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Stillingsprosent | Svar |
      | 0                | Nei  |
      | 5                | Nei  |
      | 15               | Ja   |
      | 20               | Ja   |


  Scenariomal: Regel 12 - To parallelle stillinger hos forskjellige arbeidsgivere
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforhold 1
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 1
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 10               |               |

    Og følgende arbeidsgiver i arbeidsforhold 2
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 2
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   | Skipsregister |
      | 01.01.2018      |                 | 001       | <Stillingsprosent> |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Stillingsprosent | Svar |
      | 0                | Nei  |
      | 5                | Nei  |
      | 15               | Ja   |
      | 20               | Ja   |

  Scenariomal: Regel 12 - To stillinger etter hverandre siste 12 måneder hos forskjellige arbeidsgivere
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      | 30.12.2019      | Organisasjon     | NORMALT             |
      | 31.12.2019      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforhold 1
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 1
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      | 30.12.2019      | 001       | 80               |               |

    Og følgende arbeidsgiver i arbeidsforhold 2
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 2             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 2
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   | Skipsregister |
      | 31.12.2019      |                 | 001       | <Stillingsprosent> |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.03.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Stillingsprosent | Svar |
      | 5                | Nei  |
      | 15               | Nei  |
      | 25               | Ja   |
      | 30               | Ja   |


  Scenariomal: Regel 12 - To parallelle arbeidsforhold med varierende stillingsprosent
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      | 30.12.2019      | Organisasjon     | NORMALT             |
      | 31.12.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforhold 1
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 1
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent   | Skipsregister |
      | 01.01.2018      | 30.12.2019      | 001       | <Stillingsprosent> |               |

    Og følgende arbeidsgiver i arbeidsforhold 2
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 2             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 2
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 31.12.2018      |                 | 0012      | 15               |               |

    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.03.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Stillingsprosent | Svar |
      | 0                | Nei  |
      | 5                | Nei  |
      | 10               | Ja   |
      | 20               | Ja   |
      | 50               | Ja   |
      | 100              | Ja   |


  Scenario: Regel 12 - tre delvis parallelle arbeidsforhold. Ett arbeidsforhold har arbeidsavtaler med gyldighetsperiode utenom arbeidsforhold.
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 30.09.2019      | 30.12.2019      | Organisasjon     | NORMALT             |
      | 26.09.2019      | 26.10.2019      | Organisasjon     | NORMALT             |
      | 26.10.2019      | 26.10.2019      | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforhold 1
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 1
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.11.2019      |                 | 001       | 100              |               |

    Og følgende arbeidsgiver i arbeidsforhold 2
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 2             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 2
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.08.2020      |                 | 9132126   | 100              |               |
      | 01.09.2019      | 31.07.2020      | 5221126   | 100              |               |

    Og følgende arbeidsgiver i arbeidsforhold 3
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 2             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforhold 3
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.08.2020      |                 | 9132126   | 100              |               |


    Når regel "12" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 06.10.2020      | 19.10.2020      | Nei                           |

    Så skal svaret være "Ja"