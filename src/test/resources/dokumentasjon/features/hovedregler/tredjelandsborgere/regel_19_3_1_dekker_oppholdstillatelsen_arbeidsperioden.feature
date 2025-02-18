# language: no
# encoding: UTF-8

Egenskap: 19.3.1: Dekker oppholdstillatelsen arbeidsperioden bakover i tid?

  Scenariomal: Regel 19.3.1: Dekker oppholdstillatelsen arbeidsperioden bakover i tid?

    Gitt følgende oppholdstillatelse
      | Gyldig fra og med               | Gyldig til og med               | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | <Oppholdstillatelse fra og med> | <Oppholdstillatelse til og med> | Ja             | PERMANENT | Nei                                      |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato     | Til og med dato     | Arbeidsgivertype | Arbeidsforholdstype |
      | <Arbeid fra og med> | <Arbeid til og med> | Organisasjon     | NORMALT             |

    Når regel "19.3.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 23.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Oppholdstillatelse fra og med | Oppholdstillatelse til og med | Arbeid fra og med | Arbeid til og med | Svar |
      | 01.01.2018                    |                               | 01.01.2018        |                   | Ja   |
      | 22.01.2019                    | 22.03.2020                    | 22.01.2019        | 22.03.2020        | Ja   |
      | 22.01.2019                    |                               | 22.01.2019        | 22.03.2020        | Ja   |
      | 22.01.2019                    | 22.03.2020                    | 22.01.2019        |                   | Ja   |
      | 23.01.2019                    |                               | 01.01.2018        |                   | Nei  |
      | 23.01.2019                    | 31.12.2019                    | 23.01.2019        | 01.01.2020        | Nei  |