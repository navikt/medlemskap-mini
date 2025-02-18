# language: no
# encoding: UTF-8

Egenskap: Borgere som har flere statsborgerskap i perioden får "Nei"

  Scenariomal: Borgere som har flere statsborgerskap i perioden får "Nei"

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | <Land>   | 30.06.2020      | 02.12.2020      |
      | PHL      | 20.02.2000      | 29.06.2020      |

    Når regel "2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse     |
      | 30.01.2020      | 30.01.2021      | Nei                           | SYKEPENGER |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Land | Svar |
      | NOR  | Nei  |
      | FRA  | Nei  |
      | USA  | Nei  |
