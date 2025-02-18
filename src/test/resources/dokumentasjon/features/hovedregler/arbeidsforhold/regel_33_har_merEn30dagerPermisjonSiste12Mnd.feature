# language: no
# encoding: UTF-8

Egenskap: Regel 33: Har bruker permisjoner med mer en 30 dager totalt?

  Regel: Regel 33: Har bruker mer en 30 dager i permisjon siste 12 mnd

    Scenariomal: Person som har ett arbeidsforhold uten permisjoner får Nei
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |


      Når regel "33" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar |
        | 01.01.2018 |            | Nei  |

    Scenariomal: Person som har mer en 30 dager i permisjon siste 12 mnd får ja
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |

      Og følgende permisjonspermitteringer i arbeidsforholdet
        | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent   | Type      | Varslingkode  |
        | 30.01.2020      | 30.01.2021      | permisjonPermitteringId | 10        | PERMISJON | varslingskode |

      Når regel "33" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar |
        | 01.01.2018 |            | Ja  |

    Scenariomal: Person som har mindre en 30 dager i permisjon siste 12 mnd får nei
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |

      Og følgende permisjonspermitteringer i arbeidsforholdet
        | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent   | Type      | Varslingkode  |
        | 30.01.2020      | 10.02.2020      | permisjonPermitteringId | 10        | PERMISJON | varslingskode |

      Når regel "33" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar |
        | 01.01.2018 |            | Nei  |