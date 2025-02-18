# language: no
# encoding: UTF-8

Egenskap: Bakoverkompatibel test

  Bakgrunn:

    Gitt følgende oppgaver fra Gosys
      | Aktiv dato | Prioritet | Status | Tema |
      | 10.10.1975 | NORM      | AAPNET | Tema |

    Og følgende journalposter fra Joark
      | JournalpostId | Tittel | JournalpostType | Journalstatus | Tema |
      | Id            | Tittel | Posttype        | Status        | Tema |

    Og følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | dekning | 10.10.1975      | 01.08.2020      | Ja        | ENDL    | NOR          | GYLD          |

    Og følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      | 01.08.2020      |

    Og følgende navn i personhistorikken
      | Fornavn | Etternavn |
      | Test    | Person    |

    Og følgende kontaktadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      | 01.08.2020      |

    Og følgende oppholdsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      | 01.08.2020      |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      | 01.08.2020      |

    Og følgende sivilstand i personhistorikk fra PDL
      | Sivilstandstype | Gyldig fra og med dato | Gyldig til og med dato | Relatert ved sivilstand |
      | GIFT            | 10.10.1975             | 01.08.2020             | 0101197512345           |

    Og følgende personhistorikk for ektefelle fra PDL
      | Ident         | Bosted | Fra og med dato | Til og med dato |
      | 0101197512345 | NOR    | 10.10.1975      | 01.08.2020      |

    Og følgende personhistorikk for barn fra PDL
      | Ident         | Bosted | Kontaktadresse | Fra og med dato |
      | 0101201012345 | NOR    | NOR            | 18.07.2010      |

    Og følgende arbeidsforhold til ektefelle fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 10.10.1975      | 01.08.2020      | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsavtaler til ektefelle i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister | Beregnet antall timer pr uke |
      | 10.10.1975      | 01.08.2020      | yrkeskode | 100              | NIS           | 37.5                         |

    Og følgende barn i personhistorikk for ektefelle fra PDL
      | Ident         |
      | 0101201012345 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 10.10.1975      | 01.08.2020      | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte | Antall ansatte i juridisk enhet | Juridisk enhetstype | Juridisk orgnr    | Konkursstatus |
      | organisasjonsnummer | STAT             | NOR      | 10             | 20                              | STAT                | juridiskOrgnummer | Konkursstatus |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til | Bruksperiode gyldig fra | Bruksperiode gyldig til |
      | 10             | 10.10.1975                   | 01.08.2020                   | 10.10.1975              | 01.08.2020              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister | Beregnet antall timer pr uke |
      | 10.10.1975      | 01.08.2020      | yrkeskode | 100              | NIS           | 37.5                         |

    Og følgende permisjonspermitteringer i arbeidsforholdet
      | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent | Type      | Varslingkode  |
      | 10.10.2019      | 01.08.2020      | permisjonPermitteringId | 100     | PERMISJON | varslingskode |

    Og følgende utenlandsopphold i arbeidsforholdet
      | Landkode | Fra og med dato | Til og med dato | Rapporteringsperiode |
      | SWE      | 10.10.1975      | 01.08.2020      | 2010-01              |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Avgjørelse | Har flyktningstatus | Uavklart flyktningstatus | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 12.02.2019        | 12.02.2022        | Ja             |            |                     |                          | PERMANENT | Nei                                      |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | ArbeidsadgangType            | ArbeidomfangKategori | Arbeidsadgang |
      | 12.02.2019        | 12.02.2022        | BESTEMT_ARBEID_ELLER_OPPDRAG | KUN_ARBEID_HELTID    | Ja            |

  Scenario: Bakoverkompatibel test uten ytelse i request
    Når tjenestekall med følgende parametere behandles
      | Fødselsnummer | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Første dag for ytelse |
      | 15076500565   | 01.01.2019      | 31.12.2019      | Nei                           | 01.01.2019            |

    Så skal forventet json respons være "forventetRespons"


  Scenario: Kontraktstest for tjenestekall
    Når tjenestekall med følgende parametere behandles
      | Fødselsnummer | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse     |
      | 15076500565   | 01.01.2019      | 31.12.2019      | Nei                           | SYKEPENGER |

    Så Skal kontrakt være OK


  Scenario: Kontraktstest for kjøring av regler fra datagrunnlag
    Når tjenestekall for regler med følgende parametere behandles
      | Fødselsnummer | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15076500565   | 01.01.2019      | 31.12.2019      | Nei                           |

    Så Skal kontrakt for Regler fra datagrunnlag være OK