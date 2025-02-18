# language: no
# encoding: UTF-8

Egenskap: Mapping av statsborgerskap fra PDL hentPerson

  Scenario: En person som er norsk statsborger
    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | NOR  | 2015-03-25             |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2015-03-25      |                 |


  Scenario: En person som først er norsk statsborger, og som så blir utenlandsk statsborger
    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | NOR  | 2005-01-30             | 2018-08-06             |
      | BEL  | 2018-06-07             |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 2005-01-30      | 2018-08-06      |
      | BEL      | 2018-06-07      |                 |


  Scenario: En person som først er belgisk statsborger, og så norsk statsborger
    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | BEL  | 2005-01-30             | 2018-03-26             |
      | NOR  | 2015-03-25             |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 2005-01-30      | 2018-03-26      |
      | NOR      | 2015-03-25      |                 |


  Scenario: En person som er svensk statsborger

    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | SWE  |                        |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | SWE      |                 |                 |


  Scenario: En person med flere samtidige statsborgerskap
    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | PAK  |                        |                        |
      | NOR  | 1994-09-20             |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | PAK      |                 |                 |
      | NOR      | 1994-09-20      |                 |


  Scenario: Landkode med to tegn
    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | GB   |                        |                        |
      | NO   | 1994-09-20             |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | GB       |                 |                 |
      | NO       | 1994-09-20      |                 |


  Scenario: Statsborgerskap skal sorteres på "Fra og med dato", stigende
    Gitt følgende statsborgerskap fra PDL:
      | Land | Gyldig fra og med dato | Gyldig til og med dato |
      | NOR  | 1994-09-20             |                        |
      | BEL  |                        |                        |
      | FRA  | 1984-08-08             |                        |

    Når statsborgerskap mappes

    Så skal mappet statsborgerskap være
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      |                 |                 |
      | FRA      | 1984-08-08      |                 |
      | NOR      | 1994-09-20      |                 |
