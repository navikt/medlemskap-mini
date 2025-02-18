# language: no
# encoding: UTF-8

Egenskap: Regel 19.6.1: Dekker arbeidstillatelsen arbeidsperioden bakover i tid?

  Scenariomal:

    Gitt følgende arbeidsadgang
      | Gyldig fra og med          | Gyldig til og med          | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | <Arbeidsadgang fra og med> | <Arbeidsadgang til og med> | Ja            | GENERELL          | KUN_ARBEID_HELTID    |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato     | Til og med dato     | Arbeidsgivertype | Arbeidsforholdstype |
      | <Arbeid fra og med> | <Arbeid til og med> | Organisasjon     | NORMALT             |

    Når regel "19.6.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 23.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Arbeidsadgang fra og med | Arbeidsadgang til og med | Arbeid fra og med | Arbeid til og med | Svar |
      | 01.01.2018               |                          | 01.01.2018        |                   | Ja   |
      | 22.01.2019               | 22.03.2020               | 22.01.2019        | 22.03.2020        | Ja   |
      | 22.01.2019               |                          | 22.01.2019        | 22.03.2020        | Ja   |
      | 22.01.2019               | 22.03.2020               | 22.01.2019        |                   | Ja   |
      | 23.01.2019               |                          | 01.01.2018        |                   | Nei  |
      | 23.01.2019               | 31.12.2019               | 23.01.2019        | 01.01.2020        | Nei  |