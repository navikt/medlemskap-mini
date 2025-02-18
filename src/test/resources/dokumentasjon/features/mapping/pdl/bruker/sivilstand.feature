# language: no
# encoding: UTF-8

Egenskap: Mapping av sivilstand fra PDL HentPerson.sivilstand

  Scenario: En person som har vært gift, separert og så skilt
    Gitt følgende sivilstander fra PDL:
      | Type     | Relatert ved sivilstand | Gyldig fra og med |
      | UOPPGITT |                         |                   |
      | UGIFT    |                         |                   |
      | GIFT     | 10108000398             | 1995-02-04        |
      | SEPARERT | 10108000398             | 1998-05-05        |
      | SKILT    | 10108000398             | 1999-05-05        |

    Når sivilstander mappes

    Så skal mappede sivilstander være
      | Sivilstandstype | Relatert ved sivilstand | Gyldig fra og med dato | Gyldig til og med dato |
      | GIFT            | 10108000398             | 1995-02-04             | 1998-05-04             |
      | SEPARERT        | 10108000398             | 1998-05-05             | 1999-05-04             |
      | SKILT           | 10108000398             | 1999-05-05             |                        |

  Scenario: En person som har vært registert partner, og så separert og skilt partner
    Gitt følgende sivilstander fra PDL:
      | Type               | Relatert ved sivilstand | Gyldig fra og med |
      | UOPPGITT           |                         |                   |
      | UGIFT              |                         |                   |
      | REGISTRERT_PARTNER | 10108000398             | 1995-02-04        |
      | SEPARERT_PARTNER   | 10108000398             | 1998-05-05        |
      | SKILT_PARTNER      | 10108000398             | 1999-05-05        |

    Når sivilstander mappes

    Så skal mappede sivilstander være
      | Sivilstandstype    | Relatert ved sivilstand | Gyldig fra og med dato | Gyldig til og med dato |
      | REGISTRERT_PARTNER | 10108000398             | 1995-02-04             | 1998-05-04             |
      | SEPARERT_PARTNER   | 10108000398             | 1998-05-05             | 1999-05-04             |
      | SKILT_PARTNER      | 10108000398             | 1999-05-05             |                        |

  Scenario: Hvis "Gyldig fra og med" ikke er angitt benyttes "bekreftelsesdato" istedet
    Gitt følgende sivilstander fra PDL:
      | Type     | Relatert ved sivilstand | Gyldig fra og med | Bekreftelsesdato |
      | SKILT    | 10108000398             |                   | 1993-05-05       |

    Når sivilstander mappes

    Så skal mappede sivilstander være
      | Sivilstandstype | Relatert ved sivilstand | Gyldig fra og med dato | Gyldig til og med dato |
      | SKILT           | 10108000398             | 1993-05-05             |                        |

  Scenario: Sivilstander skal sorteres på "Gyldig fra og med dato", stigende
    Gitt følgende sivilstander fra PDL:
      | Type     | Relatert ved sivilstand | Gyldig fra og med |
      | SKILT    | 10108000398             | 1999-05-05        |
      | SEPARERT | 10108000398             | 1998-05-05        |

    Når sivilstander mappes

    Så skal mappede sivilstander være
      | Sivilstandstype | Relatert ved sivilstand | Gyldig fra og med dato | Gyldig til og med dato |
      | SEPARERT        | 10108000398             | 1998-05-05             | 1999-05-04             |
      | SKILT           | 10108000398             | 1999-05-05             |                        |