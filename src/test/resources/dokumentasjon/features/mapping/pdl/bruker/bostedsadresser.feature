# language: no
# encoding: UTF-8

Egenskap: Mapping av bostedsadresser fra PDL HentPerson.Bostedsadresse

  Scenario: En person som har flyttet flere ganger i Norge
    Gitt følgende bostedsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med | Utenlandsk adresse landkode |
      |                     |                   |                             |
      | 2015-03-25 10:03:03 |                   |                             |
      | 2018-05-20 12:03:05 |                   |                             |

    Når bostedsadresser mappes

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      |                 | 2015-03-24      |
      | NOR      | 2015-03-25      | 2018-05-19      |
      | NOR      | 2018-05-20      |                 |

  Scenario: En person som har flyttet utenlands
    Gitt følgende bostedsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med   | Utenlandsk adresse landkode |
      | 2015-03-25 10:03:03 |                     |                             |
      | 2018-05-20 12:03:05 | 2020-05-20 13:09:09 |                             |

    Når bostedsadresser mappes

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2015-03-25      | 2018-05-19      |
      | NOR      | 2018-05-20      | 2020-05-20      |


  Scenario: En person som har flyttet utenlands, og som senere har flyttet tilbake til Norge
    Gitt følgende bostedsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med   | Utenlandsk adresse landkode |
      | 2008-05-20 10:03:03 | 2010-05-20 13:09:09 |                             |
      | 2018-05-20 12:03:05 |                     |                             |

    Når bostedsadresser mappes

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2008-05-20      | 2010-05-20      |
      | NOR      | 2018-05-20      |                 |

  Scenario: Bostedsadresser skal sorteres på "Fra og med dato", stigende
    Gitt følgende bostedsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med   | Utenlandsk adresse landkode |
      | 2018-05-20 10:03:03 | 2019-05-20 13:03:03 |                             |
      |                     |                     |                             |
      | 2008-05-20 12:03:05 |                     |                             |

    Når bostedsadresser mappes

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      |                 | 2008-05-19      |
      | NOR      | 2008-05-20      | 2018-05-19      |
      | NOR      | 2018-05-20      | 2019-05-20      |

  Scenario: En person har utenlandsk bostedsadresse
    Gitt følgende bostedsadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med | Utenlandsk adresse landkode |
      |                     |                   |                             |
      | 2015-03-25 10:03:03 |                   |                             |
      | 2018-05-20 12:03:05 |                   | BEL                         |

    Når bostedsadresser mappes

    Så skal mappede bostedsadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      |                 | 2015-03-24      |
      | NOR      | 2015-03-25      | 2018-05-19      |
      | BEL      | 2018-05-20      |                 |