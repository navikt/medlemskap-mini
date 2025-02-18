# language: no
# encoding: UTF-8

Egenskap: Regel 30: Har britisk bruker EOS eller EFTA opphold?

  Scenariomal: Regel 30 - Har britisk bruker EOS eller EFTA opphold?
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.2000      |                 |

    Og følgende oppholdstillatelse
      | Klasse              | Gyldig fra og med | Gyldig til og med | EOSEllerEFTAOpphold                       | EOSellerEFTAGrunnlagskategoriOppholdstillatelse |
      | EOSellerEFTAOpphold | 01.12.2019        | 01.12.2021        | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | ARBEID                                          |


    Når regel "30" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Statsborgerskap | Svar |
      | GER             | Nei  |
      | GBR             | Ja   |

  Scenario: Regel 30 - Har britisk bruker EOS eller EFTA opphold?
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | GBR      | 01.01.2000      |                 |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2019        | 31.12.2030        | Ja             | PERMANENT | Nei                                      |


    Når regel "30" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId