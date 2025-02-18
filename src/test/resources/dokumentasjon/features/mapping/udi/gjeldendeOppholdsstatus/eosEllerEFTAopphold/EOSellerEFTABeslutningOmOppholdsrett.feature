# language: no
# encoding: UTF-8

# Undergruppen EØS eller EFTA – beslutning om oppholdsrett omfatter personer som har
# registrert seg i EØS-registreringsordningen og fått beslutning om oppholdsrett.
# Personer omfattet av EØS-avtalen kan videre ha oppholdsrett i Norge
# selv om de ikke er registrert i EØS-registreringsordningen.
# Oppholdsretten følger av EØS-avtalen, og utlendingsforvaltningen
# kan ikke gi eller fjerne en persons oppholdsrett. UDI ved
# Oppholdsstatustjenesten svarer derfor kun om en person har fått en beslutning om oppholdsrett fra
# utlendingsmyndighetene, det vil si om han eller hun er registrert i EØS-registreringsordningen eller ikke,
# og ikke om en person har oppholdsrett eller faktisk utøver oppholds retten i Norge.
#
# For tredjelandsborgere med beslutning om oppholdsrett, vil tjenesten gi ut
# oppholdsrettsperioden – fradato og tildato – på beslutningen om oppholdsrett,
# mens den for EØS-borgere ikke vil gi ut oppholdsrettsperioden.
# Videre vil tjenesten utlevere oppholdsgrunnlaget for oppholdsretten,
# se beskrivelse i tabellen EØS eller EFTA: Oppholdsrett: Oppholdsgrunnlag nedenfor.



Egenskap: Mapping av EOSEllerEFTAOpphold

  Scenariomal: Bruker har EOSellerEFTABeslutningOmOppholdsrett fra EOSEllerEFTAOpphold fra EOSellerEFTABeslutningOmOppholdsrett
    Gitt følgende periode fra EOSellerEFTABeslutningOmOppholdsrett fra EOSEllerEFTAOpphold med følgende type
      | Gyldig fra og med   | Gyldig til og med   |
      | <Gyldig fra og med> | <Gyldig til og med> |

    Og følgende EOSellerEFTAGrunnlagskategoriOppholdsrett fra EOSellerEFTABeslutningOmOppholdsrett
      | EOSellerEFTAGrunnlagskategoriOppholdsrett   |
      | <EOSellerEFTAGrunnlagskategoriOppholdsrett> |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når GjeldendeOppholdsstatus med EOSEllerEFTAOpphold med EOSellerEFTABeslutningOmOppholdsrett mappes

    Så skal mappede oppholdstillatelse med EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT
      | Gyldig fra og med   | Gyldig til og med   |EOSEllerEFTAOpphold   |EOSellerEFTAGrunnlagskategoriOppholdsrett   |
      | <Gyldig fra og med> | <Gyldig til og med> |<EOSEllerEFTAOpphold> |<EOSellerEFTAGrunnlagskategoriOppholdsrett> |

    Eksempler:
      | Gyldig fra og med | Gyldig til og med  | EOSEllerEFTAOpphold                       | EOSellerEFTAGrunnlagskategoriOppholdsrett  |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | VARIG                                      |
      | 2019-03-25        |                    | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | INGEN_INFORMASJON                          |
      | 2019-03-25        |                    | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | FAMILIE                                    |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | TJENESTEYTING_ELLER_ETABLERING             |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | UAVKLART                                   |