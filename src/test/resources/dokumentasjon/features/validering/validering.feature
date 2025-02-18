# language: no
# encoding: UTF-8

Egenskap: Validering av input-dato

  Scenariomal: Første gyldige input-dag er 01.01.2017 for sveitsiske borgere, 01.01.2016 for andre borgere

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode   | Fra og med dato | Til og med dato |
      | <Landkode> | 01.01.2000      |                 |

    Når regel "0.1" kjøres med følgende parametre
      | Første dag for ytelse   | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | <Første dag for ytelse> | 01.01.2016      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Regel 0.1>"
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Landkode | Første dag for ytelse | Regel 0.1 |
      | NOR      | 31.12.2015            | Nei       |
      | NOR      | 01.01.2016            | Ja        |
      | NOR      | 31.12.2016            | Ja        |
      | CHE      | 31.12.2016            | Nei       |
      | CHE      | 01.01.2017            | Ja        |
      | BEL      | 31.12.2015            | Nei       |
      | BEL      | 01.01.2016            | Ja        |


  Scenariomal: Hvis "første dag for ytelse" er angitt, er det den som brukes i stedet for "fra og med dato"

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode   | Fra og med dato | Til og med dato |
      | <Landkode> | 01.01.2000      |                 |

    Når regel "0.1" kjøres med følgende parametre
      | Fra og med dato | Første dag for ytelse   | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2017      | <Første dag for ytelse> | 30.01.2021      | Nei                           |

    Så skal svaret være "<Regel 0.1>"
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Landkode | Første dag for ytelse | Regel 0.1 |
      | NOR      | 31.12.2015            | Nei       |
      | NOR      | 01.01.2016            | Ja        |
      | NOR      | 31.12.2016            | Ja        |
      | CHE      | 31.12.2016            | Nei       |
      | CHE      | 01.01.2017            | Ja        |


  Scenariomal: Bruker har annet EØS statsborgerskap i tillegg til sveitsisk

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode   | Fra og med dato | Til og med dato |
      | CHE        | 01.01.2000      |                 |
      | <Landkode> | 01.01.2000      |                 |


    Når regel "0.1" kjøres med følgende parametre
      | Første dag for ytelse   | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | <Første dag for ytelse> | 31.12.2015      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Regel 0.1>"
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Landkode | Første dag for ytelse | Regel 0.1 |
      | NOR      | 31.12.2015            | Nei       |
      | NOR      | 01.01.2016            | Ja        |
      | NOR      | 31.12.2016            | Ja        |
      | SWE      | 31.12.2015            | Nei       |
      | SWE      | 01.01.2016            | Ja        |
      | SWE      | 01.01.2017            | Ja        |
      | USA      | 31.12.2015            | Nei       |
      | USA      | 01.01.2016            | Nei       |
      | USA      | 01.01.2017            | Ja        |