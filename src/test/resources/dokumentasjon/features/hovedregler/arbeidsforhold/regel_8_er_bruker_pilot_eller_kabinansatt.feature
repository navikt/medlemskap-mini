# language: no
# encoding: UTF-8


Egenskap: Regel 8: Er bruker pilot eller kabinansatt?

  Scenariomal: Bruker som er pilot eller kabinansatt får "Ja"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte |
      | 1             | BEDR             | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Stillingsprosent |
      | 01.01.2018      |                 | <Yrkeskode> | 100              |


    Når regel "8" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Yrkeskode | Svar | Utfylt begrunnelse |
      | 001       | Nei  | Nei                |
      | 5111105   | Ja   | Ja                 |

  Scenario: Bruker som ikke har noe arbeidsforhold får "Nei"

    Når regel "8" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"