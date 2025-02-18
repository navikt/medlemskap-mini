# language: no
# encoding: UTF-8

Egenskap: Regel 19.4: Har bruker EOS eller EFTA opphold og britisk ektefelle?

  Scenariomal: Regel 19.4 - Har bruker EOS eller EFTA opphold og britisk ektefelle?
    Gitt følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Kontaktadresse | Oppholdsadresse | Statsborgerskap   | Fra og med dato |
      | 10108000398 | NOR    | NOR            | NOR             | <Statsborgerskap> | 18.07.2010      |

    Og følgende oppholdstillatelse
      | Klasse              | Gyldig fra og med | Gyldig til og med | EOSEllerEFTAOpphold                       | EOSellerEFTAGrunnlagskategoriOppholdstillatelse |
      | EOSellerEFTAOpphold | 01.12.2019        | 01.12.2021        | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | ARBEID                                          |


    Når regel "19.4" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Statsborgerskap | Svar |
      | NOR             | Nei  |
      | GBR             | Ja   |
