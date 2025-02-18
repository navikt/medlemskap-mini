# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 19.4

  Bakgrunn:

    Gitt følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      |                 |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype |
      | organisasjonsnummer | STAT             | NOR      | 10             | STAT                |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til |
      | 10             | 10.10.1975                   | 01.08.2021                   |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | yrkeskode | 100              |

  Scenariomal: Regelflyt for regel 19.4
    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | USA      | 10.10.1975      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2019      | 01.01.2021      | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | 01.01.2017        | 01.12.2019        | Ja            | GENERELL          | KUN_ARBEID_HELTID    |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Relatert ved sivilstand |
      | GIFT            | 29.06.2015             | 10108000398             |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident       | Bosted | Kontaktadresse | Oppholdsadresse | Statsborgerskap   | Fra og med dato |
      | 10108000398 | NOR    | NOR            | NOR             | <Statsborgerskap> | 18.07.2010      |

    Og følgende oppholdstillatelse
      | Klasse              | Gyldig fra og med | Gyldig til og med | EOSEllerEFTAOpphold                       | EOSellerEFTAGrunnlagskategoriOppholdstillatelse |
      | EOSellerEFTAOpphold | 01.01.2019        |                   | EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT | ARBEID                                          |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 23.01.2020      | 30.01.2020      | Nei                           |

    Så skal regel "19.4" gi svaret "<Regel 19.4>"
    Og skal regel-årsaker være "<Årsaker>"
    Og skal svaret være "<Medlemskap>"

    Eksempler:
      | Statsborgerskap | Regel 19.4 | Medlemskap | Årsaker |
      | GBR             | Ja         | Uavklart   | 19.4    |