# language: no
# encoding: UTF-8

Egenskap: Mapping av arbeidsgivertype i arbeidsforhold

  Scenariomal: Bruker har arbeidsforhold med permisjonPermittering

    Gitt følgende om AaRegPeriode fra AaRegPermisjonPermittering fra AaRegArbeidsforhold
      | Gyldig fra og med dato | Gyldig til og med dato |
      | 2015-03-25             | 2016-02-03             |


    Og følgende om permisjonPermitteringId i fra AaRegPermisjonPermittering fra AaRegArbeidsforhold
      | PermisjonPermitteringId |
      | 123-xyz                 |

    Og følgende prosent fra fra AaRegPermisjonPermittering fra AaRegArbeidsforhold
      | Prosent |
      | 100.0   |

    Og følgende type fra fra AaRegPermisjonPermittering fra AaRegArbeidsforhold
      | PermisjonPermitteringType |
      | <AaRegType>               |

    Og følgende varslingskode fra fra AaRegPermisjonPermittering fra AaRegArbeidsforhold
      | Varslingkode |
      | PPIDHI       |

    Når arbeidsforholdene mappes

    Så skal mappet periode i arbeidsforholdet være
      | Fra og med dato | Til og med dato |
      | 2015-03-25      | 2016-02-03      |

    Og mappet permisjonPermitteringId skal være
      | PermisjonPermitteringId |
      | 123-xyz                 |

    Og mappet prosent skal være
      | Prosent |
      | 100.0   |

    Og mappet type skal være
      | Type   |
      | <Type> |

    Og mappet varslingkode skal være
      | Varslingkode |
      | PPIDHI       |

    Eksempler:
      | AaRegType                        | Type                               |
      | permisjon                        | PERMISJON                          |
      | permisjonMedForeldrepenger       | PERMISJON_MED_FORELDREPENGER       |
      | permisjonVedMilitaertjeneste     | PERMISJON_VED_MILITAERTJENESTE     |
      | permittering                     | PERMITTERING                       |
      | andreIkkeLovfestedePermisjoner   | ANDRE_IKKE_LOVFESTEDE_PERMISJONER  |
      | andreLovfestedePermisjoner       | ANDRE_LOVFESTEDE_PERMISJONER       |
      | utdanningspermisjonIkkeLovfestet | UTDANNINGSPERMISJON_IKKE_LOVFESTET |
      | utdanningspermisjonLovfestet     | UTDANNINGSPERMISJON_LOVFESTET      |









