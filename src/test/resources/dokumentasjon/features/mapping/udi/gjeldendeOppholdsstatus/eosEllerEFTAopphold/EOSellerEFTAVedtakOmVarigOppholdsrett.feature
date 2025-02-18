# language: no
# encoding: UTF-8

# 5.1.2 EØS eller EFTA – vedtak om varig oppholdsrett
# Undergruppen EØS eller EFTA – vedtak om varig oppholdsrett
# omfatter personer der utlendingsforvaltningen har gitt vedtak om varig oppholdsrett.


Egenskap: Mapping av OppholdstillatelseEllerOppholdsPaSammeVilkar

  Scenariomal: Bruker har EOSEllerEFTAOpphold fra EOSellerEFTABeslutningOmOppholdsrett
    Gitt følgende oppholdsrettsPeriode fra EOSellerEFTAVedtakOmVarigOppholdsrett fra EOSEllerEFTAOpphold med følgende type
      | Gyldig fra og med   | Gyldig til og med   |
      | <Gyldig fra og med> | <Gyldig til og med> |

    Og følgende EOSellerEFTAGrunnlagskategoriOppholdsrett fra EOSellerEFTAVedtakOmVarigOppholdsrett
      | EOSellerEFTAGrunnlagskategoriOppholdsrett   |
      | <EOSellerEFTAGrunnlagskategoriOppholdsrett> |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når GjeldendeOppholdsstatus med EOSellerEFTAVedtakOmVarigOppholdsrett med EOSEllerEFTAOpphold mappes

    Så skal mappede EOSEllerEFTAOpphold være
      | Gyldig fra og med   | Gyldig til og med   |EOSEllerEFTAOpphold   | EOSellerEFTAGrunnlagskategoriOppholdsrett    |
      | <Gyldig fra og med> | <Gyldig til og med> |<EOSEllerEFTAOpphold> | <EOSellerEFTAGrunnlagskategoriOppholdsrett>  |

    Eksempler:
      | Gyldig fra og med | Gyldig til og med  | EOSEllerEFTAOpphold                           | EOSellerEFTAGrunnlagskategoriOppholdsrett  |
      | 2019-03-25        | 2021-02-03         | EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT   | VARIG                                      |
      | 2019-03-25        | 2021-03-25         | EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT   | INGEN_INFORMASJON                          |
      | 2019-03-25        | 2021-03-25         | EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT   | FAMILIE                                    |
      | 2019-03-25        |                    | EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT   | TJENESTEYTING_ELLER_ETABLERING             |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT   | UAVKLART                                   |