# language: no
# encoding: UTF-8
# Mest sannsynlig vil kun ett felt fra IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum forekomme

# Gruppen Personer uten oppholdstillatelse eller opphold på samme vilkår omfatter personer
# som ikke har oppholdstillatelse eller opphold på samme vilkår.
# Oppholdsstatustjenesten vil plassere vedkommende inn i én av undergruppene Avslag på søknad om
# oppholdstillatelse eller oppholdsrett, Bortfall av PO eller BOS,
# Tilbakekall eller Formelt vedtak eller Øvrig ikke opphold.
# Personer som er Utvist med innreiseforbud vil p.t. plasseres i kategorien Uavklart, se pkt. 5.4 under.
# Personer som ikke faller inn under kategoriene over,
# vil plasseres overordnet under Ikke oppholdstillatelse, ikke opphold på samme vilkår.

Egenskap: Mapping av OppholdstillatelseEllerOppholdsPaSammeVilkar

  Scenariomal: Bruker har følgende fra IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
    Gitt følgende JaNeiUavklart fra UtvistMedInnreiseForbud
      | JaNeiUavklart   |
      | <JaNeiUavklart> |

    Og  avgjorelsesDato fra AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak
      | Avgjørelsesdato   |
      | <Avgjørelsesdato> |

    Og følgende OvrigIkkeOppholdsKategori fra OvrigIkkeOpphold
      | OvrigIkkeOppholdsKategori   |
      | <OvrigIkkeOppholdsKategori> |

    Og foresporselsfodselsnummer fra HentPersonstatusResultat
      | Foresporselsfodselsnummer |
      | 20041276216               |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når GjeldendeOppholdsstatus med IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum mappes

    Så skal mappede UtvistMedInnreiseForbud i IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum i Oppholdstillatelse være
      | JaNeiUavklart   |
      | <JaNeiUavklart> |

    Og mappede AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak i IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum i Oppholdstillatelse være
      | Avgjorelsesdato   |
      | <Avgjørelsesdato> |

    Og mappede OvrigIkkeOppholdsKategori i IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum i Oppholdstillatelse være
      | OvrigIkkeOppholdsKategori   |
      | <OvrigIkkeOppholdsKategori> |

    Eksempler:
      | JaNeiUavklart | Avgjørelsesdato | OvrigIkkeOppholdsKategori                                                  |
      | JA            | 2016-02-03      | OPPHEVET_INNREISEFORBUD                                                    |
      | NEI           | 2016-02-03      | ANNULERING_AV_VISUM                                                        |
      | UAVKLART      | 2016-02-03      | UTLOPT_OPPHOLDSTILLATELSE                                                  |
      | JA            | 2016-02-03      | UTLOPT_EO_SELLER_EFTA_OPPHOLDSRETT_ELLER_EO_SELLER_EFTA_OPPHOLDSTILLATELSE |