# language: no
# encoding: UTF-8

Egenskap: Mapping fra PDL HentPerson.Person til Datagrunnlag.personhistorikk

  Scenario: EØS-borger

    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato | Utenlandsk adresse landkode |
      | BEL  |                        |                        |                             |

    Og følgende bostedsadresser fra PDL:
      | Gyldig fra og med | Gyldig til og med |
      |                   |                   |

    Og følgende kontaktadresser fra PDL:
      | Utenlandsk adresse landkode | Gyldig fra og med   | Gyldig til og med   |
      |                             |                     | 2015-03-25 10:03:03 |
      |                             | 2016-04-25 14:03:03 |                     |

    Og følgende oppholdsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med   | Utenlandsk adresse landkode |
      | 2015-03-25 10:03:03 | 2017-04-26 14:05:05 | BEL                         |

    Og følgende sivilstander fra PDL:
      | Type | Relatert ved sivilstand | Gyldig fra og med |
      | GIFT | 10108000398             | 1995-02-04        |

    Og følgende familierelasjoner fra PDL:
      | Relatert persons ident | Relatert persons rolle | Min rolle for person | Folkeregister metadata ajourholdstidspunkt | Folkeregistermetadata gyldighetstidspunkt | Folkeregistermetadata opphoerstidspunkt |
      | 20041276216            | BARN                   | FAR                  | 2012-04-22 11:03:02                        | 2012-04-20 12:00:00                       |                                         |

    Når PDL Person mappes til personhistorikk i datagrunnlaget

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      |                 |                 |

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      |                 |                 |

    Og skal mappede oppholdsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 2015-03-25      | 2017-04-26      |

    Og skal mappede sivilstander være
      | Sivilstandstype | Relatert ved sivilstand | Gyldig fra og med dato | Gyldig til og med dato |
      | GIFT            | 10108000398             | 1995-02-04             |                        |

    Og skal mappede familierelasjoner være
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 20041276216            | BARN                   | FAR                  |
