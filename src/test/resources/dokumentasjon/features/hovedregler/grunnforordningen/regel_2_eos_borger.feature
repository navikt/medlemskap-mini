# language: no
# encoding: UTF-8

Egenskap: Eøs-land

  Scenariomal: Regel 2: EØS-borgere får "Ja"

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato |
      | <Land>   | 30.01.2000      |

    Når regel "2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Land | Beskrivelse   | Svar |
      | BEL  | BELGIA        | Ja   |
      | BGR  | BULGARIA      | Ja   |
      | DNK  | DANMARK       | Ja   |
      | EST  | ESTLAND       | Ja   |
      | FIN  | FINLAND       | Ja   |
      | FRA  | FRANKRIKE     | Ja   |
      | GRC  | HELLAS        | Ja   |
      | IRL  | IRLAND        | Ja   |
      | ISL  | ISLAND        | Ja   |
      | ITA  | ITALIA        | Ja   |
      | HRV  | KROATIA       | Ja   |
      | CYP  | KYPROS        | Ja   |
      | LVA  | LATVIA        | Ja   |
      | LIE  | LIECHTENSTEIN | Ja   |
      | LTU  | LITAUEN       | Ja   |
      | LUX  | LUXEMBOURG    | Ja   |
      | MLT  | MALTA         | Ja   |
      | NLD  | NEDERLAND     | Ja   |
      | NOR  | NORGE         | Ja   |
      | POL  | POLEN         | Ja   |
      | PRT  | PORTUGAL      | Ja   |
      | ROU  | ROMANIA       | Ja   |
      | SVK  | SLOVAKIA      | Ja   |
      | SVN  | SLOVENIA      | Ja   |
      | ESP  | SPANIA        | Ja   |
      | SWE  | SVERIGE       | Ja   |
      | CZE  | TSJEKKIA      | Ja   |
      | DEU  | TYSKLAND      | Ja   |
      | HUN  | UNGARN        | Ja   |
      | AUT  | ØSTERRIKE     | Ja   |
      | CHE  | SVEITS        | Ja   |
      | GBR  | STORBRITANNIA | Nei  |
      | USA  | USA           | Nei  |


  Scenario: Regel 2: Norsk statsborger med historisk statsborgerskap utenfor EØS får ja

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Historisk |
      | USA      | 30.01.2000      | Ja        |
      | NOR      | 30.01.2010      | Nei       |

    Når regel "2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId


  Scenario: Regel 2: Amerikansk statsborger med historisk norsk statsborgerskap får nei

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Historisk |
      | USA      | 30.01.2010      | Nei       |
      | NOR      | 30.01.2000      | Ja        |

    Når regel "2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId