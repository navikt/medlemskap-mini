# language: no
# encoding: UTF-8

Egenskap: Mapping av Arbeidsadgang

  Scenariomal: Bruker har JaNeiUavklart fra Arbeidsadgang
    Gitt følgende harArbeidsadgang fra Arbeidsadgang
      | Arbeidsadgang      |
      | <HarArbeidsadgang> |

    Og foresporselsfodselsnummer fra HentPersonstatusResultat
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt fra HentPersonstatusResultat
      | Uttrekkstidspunkt       |
      | 2018-11-15T21:37:40.835 |

    Når arbeidsadgang mappes

    Så skal mappede harArbeidsgang i medlemskap være
      | Arbeidsadgang              |
      | <MedlemskapHarArbeidsgang> |

    Og foresporselsfodselsnummer i mappet Oppholdsstatus
      | Foresporselsfodselsnummer  |
      | 20041276216                |

    Og uttrekkstidspunkt i mappet Oppholdsstatus
      | Uttrekkstidspunkt        |
      | 2018-11-15T21:37:40.835  |

    Eksempler:
      | HarArbeidsadgang | MedlemskapHarArbeidsgang |
      | JA               | true                     |
      | NEI              | false                    |
      | UAVKLART         | false                    |