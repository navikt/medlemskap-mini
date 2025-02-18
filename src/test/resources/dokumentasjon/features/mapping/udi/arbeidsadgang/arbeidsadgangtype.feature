# language: no
# encoding: UTF-8

Egenskap: Mapping av Arbeidsadgang

  Scenariomal: Bruker har ArbeidsadgangType fra Arbeidsadgang
    Gitt følgende arbeidsadgangType fra Arbeidsadgang
      | ArbeidsadgangType   |
      | <Arbeidsadgangtype> |

    Og foresporselsfodselsnummer fra HentPersonstatusResultat
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når arbeidsadgang mappes

    Så skal mappede arbeidsadgangtype i medlemskap være
      | ArbeidsadgangType              |
      | <MedlemskapArbeidsadgangtype>  |

    Og foresporselsfodselsnummer i mappet Oppholdsstatus
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt i mappet Oppholdsstatus
      | Uttrekkstidspunkt        |
      | 2018-11-15T21:37:40.835  |

    Eksempler:
      | Arbeidsadgangtype                                                     | MedlemskapArbeidsadgangtype                                           |
      | BESTEMT_ARBEIDSGIVER_ELLER_OPPDRAGSGIVER                              | BESTEMT_ARBEIDSGIVER_ELLER_OPPDRAGSGIVER                              |
      | BESTEMT_ARBEID_ELLER_OPPDRAG                                          | BESTEMT_ARBEID_ELLER_OPPDRAG                                          |
      | BESTEMT_ARBEIDSGIVER_OG_ARBEID_ELLER_BESTEMT_OPPDRAGSGIVER_OG_OPPDRAG | BESTEMT_ARBEIDSGIVER_OG_ARBEID_ELLER_BESTEMT_OPPDRAGSGIVER_OG_OPPDRAG |
      | GENERELL                                                              | GENERELL                                                              |
      | UAVKLART                                                              | UAVKLART                                                              |





