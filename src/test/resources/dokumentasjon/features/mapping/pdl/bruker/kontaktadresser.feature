# language: no
# encoding: UTF-8

Egenskap: Mapping av kontaktadresser fra PDL HentPerson.Kontaktadresse

  Scenario: En person som har flyttet flere ganger i Norge
    Gitt følgende kontaktadresser fra PDL:
      | Utenlandsk adresse landkode | Gyldig fra og med   | Gyldig til og med |
      |                             | 2015-03-25 10:03:03 |                   |
      |                             | 2016-04-25 14:03:03 |                   |

    Når kontaktadresser mappes

    Så skal mappede kontaktadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2015-03-25      | 2016-04-24      |
      | NOR      | 2016-04-25      |                 |

  Scenario: En person som først har norsk kontaktadresse, og så utenlandsk kontaktadresse
    Gitt følgende kontaktadresser fra PDL:
      | Utenlandsk adresse landkode | Gyldig fra og med   | Gyldig til og med   |
      |                             | 2012-03-25 10:03:03 | 2015-03-25 10:03:03 |
      | BEL                         | 2016-04-25 14:03:03 |                     |

    Når kontaktadresser mappes

    Så skal mappede kontaktadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2012-03-25      | 2015-03-25      |
      | BEL      | 2016-04-25      |                 |

  Scenario: En person som først har utenlandsk kontaktadresse, og så norsk kontaktadresse
    Gitt følgende kontaktadresser fra PDL:
      | Utenlandsk adresse landkode | Gyldig fra og med   | Gyldig til og med   |
      | BEL                         |                     | 2015-03-25 10:03:03 |
      |                             | 2016-04-25 14:03:03 |                     |

    Når kontaktadresser mappes

    Så skal mappede kontaktadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      |                 | 2015-03-25      |
      | NOR      | 2016-04-25      |                 |

  Scenario: En person som har utenlandsk kontaktadresse i fritt format
    Gitt følgende kontaktadresser fra PDL:
      | Utenlandsk adresse frittformat landkode | Gyldig fra og med   | Gyldig til og med |
      | BEL                                     | 2016-04-25 12:01:01 |                   |

    Når kontaktadresser mappes

    Så skal mappede kontaktadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 2016-04-25      |                 |


  Scenario: Kontaktadresse med to-tegns landkode
    Gitt følgende kontaktadresser fra PDL:
      | Utenlandsk adresse landkode | Gyldig fra og med | Gyldig til og med |
      | GB                          |                   |                   |

    Når kontaktadresser mappes

    Så skal mappede kontaktadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | GBR      |                 |                 |

  Scenario: Kontaktadresser skal sorteres på "Fra og med dato", stigende
    Gitt følgende kontaktadresser fra PDL:
      | Gyldig fra og med   | Gyldig til og med |
      | 2015-03-25 10:00:00 |                   |
      |                     |                   |
      | 2005-05-25 10:00:00 |                   |

    Når kontaktadresser mappes

    Så skal mappede kontaktadresser være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      |                 | 2005-05-24      |
      | NOR      | 2005-05-25      | 2015-03-24      |
      | NOR      | 2015-03-25      |                 |