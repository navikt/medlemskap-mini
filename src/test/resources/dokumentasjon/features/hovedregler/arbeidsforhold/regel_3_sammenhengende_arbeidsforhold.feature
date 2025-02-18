# language: no
# encoding: UTF-8

Egenskap: Regel 3: Har bruker sammenhengende arbeidsforhold siste 12 måneder?

  Regel: Bruker må ha sammenhengende arbeidsforhold siste 12 måneder

    Scenario: Person med ett sammenhengende arbeidsforhold i hele perioden får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 01.01.2018      |                 | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "Ja"
      Og skal avklaringen være som definert i RegelId
      Og skal begrunnelsen være som definert i RegelId

    Scenariomal: Tillater maks 2 dager hull først og sist i kontrollperioden
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 01.01.2019      |                 | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato   | Til og med dato | Har hatt arbeid utenfor Norge |
        | <Fra og med dato> | 30.02.2020      | Nei                           |

      Så skal svaret være "<Regel 3>"

      Eksempler:
        | Fra og med dato | Regel 3 | Beskrivelse                                    |
        | 30.01.2020      | Ja      |                                                |
        | 01.01.2020      | Ja      |                                                |
        | 31.12.2019      | Ja      | 1 dag hull i slutten av kontrollperioden       |
        | 30.12.2019      | Ja      | 2 dager hull i slutten av kontrollperioden     |
        | 29.12.2019      | Nei     | 3 dager hull i slutten av kontrollperioden     |
        | 01.01.2020      | Ja      |                                                |
        | 31.12.2019      | Ja      | 1 dag hull i begynnelsen av kontrollperioden   |
        | 30.12.2019      | Ja      | 2 dager hull i begynnelsen av kontrollperioden |
        | 29.12.2019      | Nei     | 3 dager hull i begynnelsen av kontrollperioden |

    Scenario: Person med flere sammenhengende arbeidsforhold i perioden skal få "ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 08.04.2019      | 30.09.2019      | Organisasjon     | NORMALT             |
        | 01.10.2019      | 31.12.2020      | Organisasjon     | NORMALT             |
        | 01.01.2020      | 31.12.2020      | Organisasjon     | NORMALT             |
        | 01.01.2020      |                 | Organisasjon     | NORMALT             |
        | 24.08.2020      | 25.08.2020      | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 24.09.2020      | 15.10.2020      | Nei                           |

      Så skal svaret være "Ja"

    Scenario: Person med 3 overlappende arbeidsforhold i perioden skal få "ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 20.11.2018      | 31.12.2019      | Organisasjon     | NORMALT             |
        | 01.03.2018      | 31.07.2020      | Organisasjon     | NORMALT             |
        | 01.06.2020      |                 | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 05.10.2020      | 25.10.2020      | Nei                           |

      Så skal svaret være "Ja"

    Scenariomal: Tillat 2 dagers hull mellom to arbeidsforhold hvis ikke begge arbeidsforholdene er 100 % stilling.
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fom>           | <Tom>           | Organisasjon     | NORMALT             |
        | <Fom 2>         | <Tom 2>         | Organisasjon     | NORMALT             |

      Og følgende arbeidsavtaler i arbeidsforhold 1
        | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
        | 01.01.2018      |                 | 001       | 99               |               |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 12.02.2020      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fom        | Tom        | Fom 2      | Tom 2      | Svar | Kommentar                                   |
        | 01.01.2019 | 15.03.2019 | 19.03.2019 |            | Nei  | Arbeidsforhold med tre dager opphold        |
        | 01.01.2019 | 15.03.2019 | 17.04.2019 |            | Nei  | Arbeidsforhold som ikke er sammenhengende   |
        | 01.01.2016 | 15.03.2019 | 16.03.2019 | 29.05.2019 | Nei  | Mangler arbeidsforhold i bakkant            |
        | 01.03.2019 | 15.03.2019 | 16.03.2019 | 29.05.2019 | Nei  | Mangler arbeidsforhold i forkant og bakkant |
        | 01.03.2019 | 15.03.2019 | 16.03.2019 |            | Nei  | Mangler arbeidsforhold i forkant            |
        | 01.01.2019 | 15.03.2019 | 18.03.2019 |            | Ja   | Arbeidsforhold med to dagers opphold        |
        | 01.01.2019 | 30.11.2019 | 01.10.2019 |            | Ja   | Arbeidsforhold med overlapp                 |

    Scenariomal: Tillat 35 dagers hull mellom to 100 % stillingsforhold
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fom>           | <Tom>           | Organisasjon     | NORMALT             |
        | <Fom 2>         | <Tom 2>         | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fom        | Tom        | Fom 2      | Tom 2      | Svar | Kommentar                                   |
        | 01.01.2019 | 15.03.2019 | 19.03.2019 |            | Ja   | Arbeidsforhold med tre dager opphold        |
        | 01.01.2019 | 15.03.2019 | 19.04.2019 |            | Ja   | Arbeidsforhold med 35 dager opphold         |
        | 01.01.2019 | 15.03.2019 | 20.04.2019 |            | Nei  | Arbeidsforhold med over 35 dager opphold    |
        | 01.01.2016 | 15.03.2019 | 16.03.2019 | 29.05.2019 | Nei  | Mangler arbeidsforhold i bakkant            |
        | 01.03.2019 | 15.03.2019 | 16.03.2019 | 29.05.2019 | Nei  | Mangler arbeidsforhold i forkant og bakkant |
        | 01.03.2019 | 15.03.2019 | 16.03.2019 |            | Nei  | Mangler arbeidsforhold i forkant            |
        | 01.01.2019 | 15.03.2019 | 18.03.2019 |            | Ja   | Arbeidsforhold med to dagers opphold        |
        | 01.01.2019 | 30.11.2019 | 01.10.2019 |            | Ja   | Arbeidsforhold med overlapp                 |


  Regel: Bruker får ikke ha mer enn 10 arbeidsforhold i kontrollperioden

    Scenario: Bruker med  mer enn 10 arbeidsforhold i perioden
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 01.01.2018      | 15.02.2019      | Organisasjon     | NORMALT             |
        | 16.02.2019      | 15.03.2019      | Organisasjon     | NORMALT             |
        | 16.03.2019      | 15.04.2019      | Organisasjon     | NORMALT             |
        | 16.04.2019      | 15.05.2019      | Organisasjon     | NORMALT             |
        | 16.05.2019      | 15.06.2019      | Organisasjon     | NORMALT             |
        | 16.06.2019      | 15.07.2019      | Organisasjon     | NORMALT             |
        | 16.07.2019      | 15.08.2019      | Organisasjon     | NORMALT             |
        | 16.08.2019      | 15.09.2019      | Organisasjon     | NORMALT             |
        | 16.09.2019      | 15.10.2019      | Organisasjon     | NORMALT             |
        | 16.10.2019      | 15.11.2019      | Organisasjon     | NORMALT             |
        | 16.11.2019      |                 | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 14.02.2020      | Nei                           |

      Så skal svaret være "Nei"
      Og skal avklaringen være som definert i RegelId
      Og skal begrunnelsen være som definert i RegelId


  Regel: Hvis "første dag for ytelse" er angitt, så benyttes denne istedet for inputPeriodens "Fra og med dato".

    Scenariomal: Hvis "første dag for ytelse" er angitt, så skal kontrollperioden være 12 måneder før denne datoen.
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | 01.01.2019      |                 | Organisasjon     | NORMALT             |

      Når regel "3" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Første dag for ytelse   | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.02.2020      | <Første dag for ytelse> | Nei                           |

      Så skal svaret være "<Regel 3>"

      Eksempler:
        | Første dag for ytelse | Regel 3 | Beskrivelse                                                            |
        |                       | Ja      | Hvis første dag for ytelse ikke er angitt, så benyttes fra og med dato |
        | 30.01.2020            | Ja      |                                                                        |
        | 01.01.2020            | Ja      |                                                                        |
        | 31.12.2019            | Ja      | 31.12 minus en dag = 30.12.2019, og så 12 måneder tilbake: 31.12.2018  |
        | 30.12.2019            | Ja      | 1 dag hull i slutten av kontrollperioden                               |
        | 29.12.2019            | Ja      | 2 dager hull i slutten av kontrollperioden                             |
        | 28.12.2019            | Nei     | 3 dager hull i slutten av kontrollperioden                             |
        | 01.01.2020            | Ja      |                                                                        |
        | 31.12.2019            | Ja      | Ingen dager med hull i kontrollperioden                                |
        | 30.12.2019            | Ja      | 1 dager hull i begynnelsen av kontrollperioden                         |
        | 29.12.2019            | Ja      | 2 dager hull i begynnelsen av kontrollperioden                         |
        | 28.12.2019            | Nei     | 3 dager hull i begynnelsen av kontrollperioden                         |