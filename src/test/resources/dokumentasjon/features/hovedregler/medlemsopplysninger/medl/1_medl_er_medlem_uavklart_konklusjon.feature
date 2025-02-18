# language: no
# encoding: UTF-8

Egenskap: Regel 1 uavklart konklusjon som følge av endret arbeidsforhold

  Scenario: Regel 1 uavklart konklusjon som følge av endret arbeidsforhold

    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | FTL_2-6 | 01.01.2019      | 01.06.2020      | Ja        | ENDL    | NOR          | GYLD          |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2019      | 01.04.2020      | Organisasjon     | NORMALT             | 1               |
      | 02.04.2019      | 01.06.2020      | Organisasjon     | NORMALT             | 2               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse     |
      | 30.01.2020      | 10.02.2020      | Nei                           | SYKEPENGER |

    Så skal svaret være "Uavklart"