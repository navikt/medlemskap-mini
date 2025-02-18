# language: no
# encoding: UTF-8


Egenskap: Regel 6: Er foretaket aktivt?

  Scenariomal: Bruker med arbeidsgiver som har aktivt foretak får "Ja"
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Antall ansatte | Konkursstatus   |
      | 1             | BEDR             | 9              | <Konkursstatus> |

    Når regel "6" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Konkursstatus | Svar |
      |               | Ja   |
      | OSKP          | Nei  |

  Scenario: Regel 6: Bruker uten arbeidsforhold skal få "Nei"
    Når regel "6" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"