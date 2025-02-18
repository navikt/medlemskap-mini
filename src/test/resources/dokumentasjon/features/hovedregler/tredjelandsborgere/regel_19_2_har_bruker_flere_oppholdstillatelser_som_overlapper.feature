# language: no
# encoding: UTF-8

Egenskap: Regel 19.2: Har bruker flere oppholdstillatelser som overlapper?

  Scenariomal: Regel 19.2: Har bruker flere oppholdstillatelser som overlapper?

    Gitt følgende oppholdstillatelse med oppholdstillatelse på samme vilkår
      | Gyldig fra og med   | Gyldig til og med   | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | <Gyldig fra og med> | <Gyldig til og med> | Ja             | PERMANENT | Nei                                      |

    Og følgende i EØSellerEFTAOpphold
      | Gyldig fra og med                  | Gyldig til og med                  | EOSEllerEFTAOpphold                       | EOSellerEFTAGrunnlagskategoriOppholdsrett | Klasse              |
      | <EØS eller EFTA Gyldig fra og med> | <EØS eller EFTA Gyldig til og med> | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | VARIG                                     | EØSellerEFTAOpphold |


    Når regel "19.2" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 23.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Gyldig fra og med | Gyldig til og med | EØS eller EFTA Gyldig fra og med | EØS eller EFTA Gyldig til og med | Svar |
      | 22.01.2019        | 22.03.2020        | 22.01.2018                       | 01.01.2020                       | Ja   |
      | 22.01.2019        | 22.03.2020        | 01.01.2010                       | 01.01.2015                       | Nei  |
