# language: no
# encoding: UTF-8

Egenskap: Regel 32: Har bruker permisjoner?

  Regel: Regel 32: Har bruker bare ett arbeidsforhold?

    Scenariomal: Person som har ett arbeidsforhold uten permisjoner får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |


      Når regel "32" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar |
        | 01.01.2018 |            | Nei  |



    Scenariomal: Person med ett arbeidsforhold og permisjon med 60% får "Nei"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |

      Og følgende permisjonspermitteringer i arbeidsforholdet
        | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent   | Type      | Varslingkode  |
        | 10.10.2019      | 01.08.2020      | permisjonPermitteringId | <PROSENT> | PERMISJON | varslingskode |

      Når regel "32" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar | PROSENT |
        | 01.01.2018 |            | Nei  | 60      |
        | 01.01.2018 |            | Ja   | 100     |
        | 01.01.2018 |            | Nei  | 99      |


    Scenariomal: Person med to arbeidsforhold og permisjon 100% langt tilbake i tid får "Ja"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |

      Og følgende permisjonspermitteringer i arbeidsforholdet
        | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent   | Type      | Varslingkode  |
        | 10.10.1970      | 01.08.1971      | permisjonPermitteringId | <PROSENT> | PERMISJON | varslingskode |

      Når regel "32" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar | PROSENT |
        | 01.01.2018 |            | Nei   | 100     |


    Scenariomal: Person med flere arbeidsforhold og permisjoner får "Nei"
      Gitt følgende arbeidsforhold fra AAReg
        | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |
        | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             |


      Og følgende permisjonspermitteringer i arbeidsforholdet
        | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent   | Type      | Varslingkode  |
        | 10.10.2019      | 01.08.2020      | permisjonPermitteringId | <PROSENT> | PERMISJON | varslingskode |

      Når regel "32" kjøres med følgende parametre
        | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
        | 30.01.2020      | 30.01.2021      | Nei                           |

      Så skal svaret være "<Svar>"

      Eksempler:
        | Fra og med | Til og med | Svar | PROSENT |
        | 01.01.2018 |            | Ja   | 60      |
        | 01.01.2018 |            | Ja   | 100     |