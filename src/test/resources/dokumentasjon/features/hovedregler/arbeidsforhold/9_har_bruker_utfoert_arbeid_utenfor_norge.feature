# language: no
# encoding: UTF-8

Egenskap: Regel 9: Har bruker utført arbeid utenfor Norge?

  Scenariomal: Regel 9: Har bruker utført arbeid utenfor Norge?

    Når regel "9" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | <Arbeid utenfor Norge>        |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Arbeid utenfor Norge | Svar |
      | Ja                   | Ja   |
      | Nei                  | Nei  |

  Scenariomal: Regel 9 blir overstyrt dersom brukerspørsmål finnes

    Når regel "9" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Utført Arbeid Utenfor Norge   |
      | 30.01.2020      | 30.01.2021      | Ja                            | <Utført arbeid utenfor Norge> |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Utført arbeid utenfor Norge | Svar |
      | Ja                          | Nei  |
      |                             | Ja   |

  Scenariomal: Regel 9 blir overstyrt dersom brukerspørsmål finnes

    Når regel "9" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Har oppholdstillatelse   |
      | 30.01.2020      | 30.01.2021      | Ja                            | <Har oppholdstillatelse> |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Har oppholdstillatelse | Svar |
      | Ja                     | Nei  |
      |                        | Ja   |

  Scenariomal: Regel 9 blir overstyrt dersom brukerspørsmål finnes

    Når regel "9" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Opphold utenfor Norge   |
      | 30.01.2020      | 30.01.2021      | Ja                            | <Opphold utenfor Norge> |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Opphold utenfor Norge | Svar |
      | Ja                    | Nei  |
      |                       | Ja   |

  Scenariomal: Regel 9 blir overstyrt dersom brukerspørsmål finnes

    Når regel "9" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Opphold utenfor EØS   |
      | 30.01.2020      | 30.01.2021      | Ja                            | <Opphold utenfor EØS> |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Opphold utenfor EØS | Svar |
      | Ja                  | Nei  |
      |                     | Ja   |

