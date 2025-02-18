# language: no
# encoding: UTF-8


Egenskap: Regel 17.1: Er bruker frilanser?

  Scenariomal: Bruker med frilanser arbeidsforholdtype får "Ja"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype   |
      | 01.01.2018      |                 | Organisasjon     | <Arbeidsforholdstype> |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte |
      | 1             | BEDR             | 9              |

    Når regel "17.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Arbeidsforholdstype | Svar |
      | NORMALT             | Nei  |
      | FRILANSER           | Ja   |


  Scenario: Regel 17.1: Bruker med flere arbeidsforhold der ett arbeidsforhold er frilanser får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |
      | 01.01.2018      |                 | Organisasjon     | FRILANSER           |

    Når regel "17.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
