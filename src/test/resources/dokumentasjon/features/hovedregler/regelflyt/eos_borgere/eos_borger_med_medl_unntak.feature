# language: no
# encoding: UTF-8

Egenskap: EØS-borger med medl unntak.

  Bakgrunn:

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 01.01.2000      |                 |

    Og følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 10.10.1975      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 10.10.1975      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte | Antall ansatte i juridisk enhet | Juridisk enhetstype | Juridisk orgnr    | Konkursstatus |
      | organisasjonsnummer | STAT             | NOR      | 10             | 20                              | STAT                | juridiskOrgnummer | Konkursstatus |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til | Bruksperiode gyldig fra | Bruksperiode gyldig til |
      | 10             | 10.10.1975                   | 01.08.2021                   | 10.10.1975              | 01.08.2021              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister | Beregnet antall timer pr uke |
      | 10.10.1975      | 01.08.2020      | yrkeskode | 100              | NIS           | 37.5                         |

  Scenariomal: EØS-borger med medl unntak

    Gitt følgende medlemsunntak fra MEDL
      | Dekning             | Fra og med dato | Til og med dato | Er medlem   | Lovvalg | Lovvalgsland | Periodestatus |
      | FTL_2-9_2_ld_jfr_1c | 01.01.2018      | 01.06.2021      | <Er medlem> | ENDL    | NOR          | GYLD          |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 05.01.2021      | 12.02.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"

    Og skal resultat gi følgende delresultater:
      | Regel                       |
      #| REGEL_OVERSTYRING           |
      | REGEL_DOED                  |
      | REGEL_FELLES_ARBEIDSFORHOLD |
      | REGEL_YTELSER               |
      | REGEL_STATSBORGERSKAP       |
      | REGEL_MEDL                  |
      | REGEL_ARBEIDSFORHOLD        |
      | REGEL_BOSATT                |
      | REGEL_EØS_BOSATT            |

    Og skal regel "MEDL" gi svaret "<Regel MEDL>"

    Eksempler:
      | Er medlem | Regel MEDL | Medlemskap |
      | Ja        | Ja         | Ja         |
      | Nei       | Uavklart   | Uavklart   |
