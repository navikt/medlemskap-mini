# language: no
# encoding: UTF-8


Egenskap: Regel 7.1: Er bruker ansatt på et norsk skip?

  Scenariomal: Regel 7.1 Bruker som er ansatt på et norsk skip får "Ja"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | MARITIMT            |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte | Konkursstatus |
      | 1             | BEDR             | 9              |               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Skipsregister   | Stillingsprosent | Fartsområde   |
      | 01.01.2018      |                 | <Yrkeskode> | <Skipsregister> | 100              | <Fartsområde> |


    Når regel "7.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Yrkeskode | Skipsregister | Fartsområde | Svar |
      | 1         |               |             | Nei  |
      | 1         | NIS           |             | Nei  |
      | 1         | UTL           |             | Nei  |
      | 1         | NIS           | UTENRIKS    | Nei  |
      | 1         | NIS           | INNENRIKS   | Ja   |
      | 3113149   | NIS           | UTENRIKS    | Ja   |
      | 3113149   | NIS           |             | Ja   |
      | 1         | NOR           |             | Ja   |

  Scenario: Regel 7.1 Bruker som er ansatt på et norsk skip med flere arbeidsavtaler får "Ja"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | MARITIMT            |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte | Konkursstatus |
      | 1             | BEDR             | 9              |               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Skipsregister | Stillingsprosent |
      | 01.01.2018      |                 | 001       | NOR           | 100              |
      | 02.09.2018      |                 | 002       | NOR           | 100              |


    Når regel "7.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenariomal: 7.1 Bruker som er ansatt på et NIS skip med flere arbeidsavtaler
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | MARITIMT            |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte | Konkursstatus |
      | 1             | BEDR             | 9              |               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Skipsregister    | Stillingsprosent | Fartsområde    |
      | 01.01.2018      |                 | 001       | <Skipsregister1> | 100              | <Fartsområde1> |
      | 02.09.2018      |                 | 002       | <Skipsregister2> | 100              | <Fartsområde2> |

    Når regel "7.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Fartsområde1 | Fartsområde2 | Skipsregister1 | Skipsregister2 | Svar |
      | INNENRIKS    | INNENRIKS    | NIS            | NIS            | Ja   |
      | INNENRIKS    |              | NIS            | NIS            | Nei  |
      | INNENRIKS    |              | NIS            | NOR            | Ja   |
      | INNENRIKS    | INNENRIKS    | NIS            | UTL            | Nei  |
      | UTENRIKS     | INNENRIKS    | NIS            | NIS            | Nei  |


  Scenariomal: 7.1 Bruker med godtatt yrkeskode skal få "Ja"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | MARITIMT            |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte | Konkursstatus |
      | 1             | BEDR             | 9              |               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Skipsregister | Stillingsprosent |
      | 01.01.2018      |                 | <Yrkeskode> | NIS           | 100              |


    Når regel "7.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Yrkeskode | Svar |
      | 1         | Nei  |
      | 3113149   | Ja   |
      | 3132106   | Ja   |
      | 3132108   | Ja   |
      | 3141101   | Ja   |
      | 3141102   | Ja   |
      | 3141103   | Ja   |
      | 3141107   | Ja   |
      | 3141109   | Ja   |
      | 3141110   | Ja   |
      | 3141112   | Ja   |
      | 3141113   | Ja   |
      | 3141114   | Ja   |
      | 3141115   | Ja   |
      | 3141117   | Ja   |
      | 3141118   | Ja   |
      | 7134105   | Ja   |
      | 7221105   | Ja   |
      | 7241110   | Ja   |
      | 7241161   | Ja   |
      | 7242112   | Ja   |
      | 8342101   | Ja   |
      | 8342102   | Ja   |
      | 8342103   | Ja   |
      | 8342104   | Ja   |
      | 8342105   | Ja   |
      | 8342106   | Ja   |
      | 8342110   | Ja   |
      | 8342111   | Ja   |
      | 8342114   | Ja   |
      | 8341101   | Ja   |
      | 8341102   | Ja   |
      | 8341104   | Ja   |
      | 8341105   | Ja   |
      | 8341106   | Ja   |
      | 8341107   | Ja   |
      | 8341108   | Ja   |
      | 8341109   | Ja   |