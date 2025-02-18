# language: no
# encoding: UTF-8

Egenskap: Regel 1 konklusjon for brukere med medlemskap: Er det dekning for gjeldende ytelse?

  Bakgrunn:

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |

    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2019      | 01.06.2020      | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent | Skipsregister |
      | 01.01.2018      |                 | 001       | 100              |               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

  Scenariomal: Regel 1 konklusjon for brukere med medlemskap: Er det dekning for gjeldende ytelse?

    Gitt følgende medlemsunntak fra MEDL
      | Dekning   | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | <Dekning> | 01.01.2019      | 01.06.2020      | Ja        | ENDL    | NOR          | GYLD          |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse   |
      | 30.01.2020      | 10.02.2020      | Nei                           | <Ytelse> |

    Så skal svaret være "<medlemskap>" på medlemskap og "<harDekning>" på harDekning

    Eksempler:
      | Dekning           | Ytelse           | harDekning | medlemskap |
      | FTL_2-6           | SYKEPENGER       | Ja         | Ja         |
      | FTL_2-6           | ENSLIG_FORSORGER | Nei        | Uavklart   |
      | FTL_2-6           | DAGPENGER        | Nei        | Uavklart   |
      | FTL_2-7a_2_ledd_a | ENSLIG_FORSORGER | Ja         | Ja         |
      | FTL_2-7a_2_ledd_a | DAGPENGER        | Ja         | Ja         |
      | FTL_2-9_1_ledd_a  | SYKEPENGER       | Nei        | Uavklart   |
      | FTL_2-9_1_ledd_a  | DAGPENGER        | Nei        | Uavklart   |
      | FTL_2-9_1_ledd_a  | ENSLIG_FORSORGER | Nei        | Uavklart   |

  Scenariomal: Regel 1 konklusjon for brukere med medlemskap: Er det dekning for gjeldende ytelse med flere perioder?

    Gitt følgende medlemsunntak fra MEDL
      | Dekning          | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | FTL_2-9_1_ledd_a | 01.01.2019      | 01.06.2019      | Ja        | ENDL    | NOR          | GYLD          |
      | <Dekning>        | 02.06.2019      | 31.12.2022      | Ja        | ENDL    | NOR          | GYLD          |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2019      |                 | Organisasjon     | NORMALT             | 1               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse   |
      | 30.01.2020      | 10.02.2020      | Nei                           | <Ytelse> |

    Så skal svaret være "<medlemskap>" på medlemskap og "<harDekning>" på harDekning

    Eksempler:
      | Dekning             | Ytelse     | harDekning | medlemskap |
      | FTL_2-9_2_ld_jfr_1c | SYKEPENGER | Ja         | Ja         |
      | FTL_2-9_1_ledd_a    | SYKEPENGER | Nei        | Uavklart   |
