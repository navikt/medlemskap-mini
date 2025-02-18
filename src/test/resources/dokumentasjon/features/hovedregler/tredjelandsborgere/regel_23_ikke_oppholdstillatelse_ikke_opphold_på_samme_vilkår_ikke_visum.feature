# language: no
# encoding: UTF-8

Egenskap: Regel 23: Returnerer UDI Ikke oppholdstillatelse eller ikke opphold på samme vilkår?

  Scenario: Regel 23 - En ikke oppholdstillatelse ikke opphold på samme vilkår ikke visum

    Gitt følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Klasse                                                   | Utvist med innreiseforbud | OvrigIkkeOppholdsKategori |
      | 27.01.2021        |                   | IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum | JA                        | UTLOPT_OPPHOLDSTILLATELSE |

    Når regel "23" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

  Scenario: Regel 23 - En avklart oppholdstillatelse av type OppholdstillaelsePaSammeVilkarType

    Gitt følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Klasse                          | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 27.01.2021        |                   | OppholdstillatelsePaSammeVilkar | PERMANENT | Nei                                      |

    Når regel "23" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2021      | 12.02.2021      | Nei                           |

    Så skal svaret være "Nei"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId