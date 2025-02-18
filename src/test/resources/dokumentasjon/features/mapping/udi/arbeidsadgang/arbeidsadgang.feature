# language: no
# encoding: UTF-8

Egenskap: Mapping av Arbeidsadgang

  Scenario: Bruker har Arbeidsadgang
    Gitt følgende harArbeidsadgang fra Arbeidsadgang
      | Arbeidsadgang  |
      | JA             |

    Og følgende arbeidsadgangType fra Arbeidsadgang
      | ArbeidsadgangType   |
      | GENERELL            |

    Og følgende arbeidomfangKategori fra Arbeidsadgang
      | ArbeidomfangKategori |
      | KUN_ARBEID_HELTID    |

    Og foresporselsfodselsnummer fra HentPersonstatusResultat
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Gitt følgende om periode i Arbeidsadgang
      | Gyldig fra og med   | Gyldig til og med |
      | 2015-03-25          | 2016-02-03        |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når arbeidsadgang mappes

    Så skal mappede arbeidsadgangtype i medlemskap være
      | ArbeidsadgangType |
      | GENERELL          |

    Og skal mappede ArbeidomfangKategori i medlemskap være
      | ArbeidomfangKategori  |
      | KUN_ARBEID_HELTID     |

    Og skal mappede harArbeidsgang i medlemskap være
      | Arbeidsadgang   |
      | true            |

    Og mappet periode i medlemskap være
      | Gyldig fra og med | Gyldig til og med |
      | 2015-03-25        | 2016-02-03        |

    Og foresporselsfodselsnummer i mappet Oppholdsstatus
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt i mappet Oppholdsstatus
      | Uttrekkstidspunkt        |
      | 2018-11-15T21:37:40.835  |













