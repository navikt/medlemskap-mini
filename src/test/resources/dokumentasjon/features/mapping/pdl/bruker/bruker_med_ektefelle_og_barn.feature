# language: no
# encoding: UTF-8

Egenskap: Mapping av bruker med ektefelle og barn

  Scenario: En bruker med barn og ektefelle
    Gitt følgende bostedsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med | Utenlandsk adresse landkode |
      | 2015-03-25 10:03:03 |                   |                             |

    Og følgende familierelasjoner fra PDL:
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 20041276216            | BARN                   | FAR                  |

    Og følgende sivilstander fra PDL:
      | Type | Relatert ved sivilstand | Gyldig fra og med |
      | GIFT | 10108000398             | 1995-02-04        |

    Og følgende bostedsadresse i barnets personhistorikk
      | Gyldig fra og med   | Gyldig til og med | Utenlandsk adresse landkode |
      | 2015-03-25 10:03:03 |                   |                             |

    Og følgende bostedsadresse til ektefelles personhistorikk
      | Gyldig fra og med   | Gyldig til og med | Utenlandsk adresse landkode |
      | 2015-03-25 10:03:03 |                   |                             |

    Når personhistorikken til bruker, ektefelle og barn mappes med følgende parametre
      | Første dato for ytelse |
      | 2020-12-12             |

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2015-03-25      |                 |

    Og mappede bostedadresse til barnet være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2015-03-25      |                 |

    Og følgende bostedsadresse til ektefelle være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2015-03-25      |                 |



