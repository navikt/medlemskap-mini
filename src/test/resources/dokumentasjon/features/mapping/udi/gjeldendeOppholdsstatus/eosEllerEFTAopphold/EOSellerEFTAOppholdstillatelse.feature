# language: no
# encoding: UTF-8

#Gruppen EØS eller EFTA gjelder personer som er omfattet av EØS-avtalen og oppholder seg i Norge
#på bakgrunn av oppholdsretten EØS-avtalen gir.
#Kategorien omfatter både EØS-borgere og personer som er omfattet av EØS-avtalen.
#Selv om en person er borger av et land utenfor EØS-området eller er statsløs,
#kan han eller hun i visse tilfeller likevel være omfattet av EØS-avtalen.
#Slike tilfeller inkluderer familiemedlemmer av EØS-borgere og personer som er i Norge for å
#yte tjenester på vegne av et foretak basert i et EØS-land.
#Gruppen omfatter de tre undergruppene EØS eller EFTA - beslutning om oppholdsrett,
#EØS eller EFTA - vedtak om varig opphold oppholdsrett og EØS eller EFTA – oppholdstillatelse (gammel lov).


Egenskap: Mapping av OppholdstillatelseEllerOppholdsPaSammeVilkar

  Scenariomal: Bruker har EOSEllerEFTAOpphold fra EOSellerEFTABeslutningOmOppholdsrett
    Gitt følgende periode fra EOSellerEFTAOppholdstillatelse fra EOSEllerEFTAOpphold med følgende type
      | Gyldig fra og med   | Gyldig til og med   |
      | <Gyldig fra og med> | <Gyldig til og med> |

    Og følgende EOSellerEFTAGrunnlagskategoriOppholdsrett fra EOSellerEFTAOppholdstillatelse
      | EOSellerEFTAGrunnlagskategoriOppholdstillatelse   |
      | <EOSellerEFTAGrunnlagskategoriOppholdstillatelse> |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når GjeldendeOppholdsstatus med EOSEllerEFTAOpphold med EOSellerEFTAOppholdstillatelse mappes

    Så skal mappede EOSEllerEFTAOpphold være
      | Gyldig fra og med   | Gyldig til og med   |EOSEllerEFTAOpphold   | EOSellerEFTAGrunnlagskategoriOppholdstillatelse   |
      | <Gyldig fra og med> | <Gyldig til og med> |<EOSEllerEFTAOpphold> | <EOSellerEFTAGrunnlagskategoriOppholdstillatelse> |

    Eksempler:
      | Gyldig fra og med | Gyldig til og med  | EOSEllerEFTAOpphold               | EOSellerEFTAGrunnlagskategoriOppholdstillatelse  |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_OPPHOLDSTILLATELSE | EGNE_MIDLER_ELLER_FASTE_PERIODISKE_YTELSER       |
      | 2019-03-25        |                    | EOS_ELLER_EFTA_OPPHOLDSTILLATELSE | ARBEID                                           |
      |                   |                    | EOS_ELLER_EFTA_OPPHOLDSTILLATELSE | UTDANNING                                        |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_OPPHOLDSTILLATELSE | TJENESTEYTING_ELLER_ETABLERING                   |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_OPPHOLDSTILLATELSE | FAMILIE                                          |
      | 2019-03-25        | 2022-02-03         | EOS_ELLER_EFTA_OPPHOLDSTILLATELSE | UAVKLART                                         |