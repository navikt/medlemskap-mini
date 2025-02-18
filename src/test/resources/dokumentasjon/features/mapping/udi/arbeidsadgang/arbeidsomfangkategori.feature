# language: no
# encoding: UTF-8

Egenskap: Mapping av Arbeidsadgang

  Scenariomal: Bruker har ArbeidomfangKategori fra Arbeidsadgang
    Gitt følgende arbeidomfangKategori fra Arbeidsadgang
      | ArbeidomfangKategori    |
      | <ArbeidomfangKategori>  |

    Og foresporselsfodselsnummer fra HentPersonstatusResultat
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når arbeidsadgang mappes

    Så skal mappede ArbeidomfangKategori i medlemskap være
      | ArbeidomfangKategori               |
      | <MedlemskapArbeidomfangKategori>  |

    Og foresporselsfodselsnummer i mappet Oppholdsstatus
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt i mappet Oppholdsstatus
      | Uttrekkstidspunkt        |
      | 2018-11-15T21:37:40.835  |

    Eksempler:
      | ArbeidomfangKategori                 | MedlemskapArbeidomfangKategori |
      | INGEN_KRAV_TIL_STILLINGSPROSENT       | INGEN_KRAV_TIL_STILLINGSPROSENT |
      | KUN_ARBEID_HELTID                     | KUN_ARBEID_HELTID               |
      | KUN_ARBEID_DELTID                     | KUN_ARBEID_DELTID               |
      | DELTID_SAMT_FERIER_HELTID             | DELTID_SAMT_FERIER_HELTID       |
      | UAVKLART                              | UAVKLART                        |














