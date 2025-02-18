# language: no
# encoding: UTF-8

Egenskap: Er arbeidsforholdet sammenfallende med perioden uten medlemskap?

  Bakgrunn:

    Gitt følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2020      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | BEL      | 01.01.1975      |                 |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte |
      | organisasjonsnummer | STAT             | NOR      | 10             |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til |
      | 10             | 01.02.2020                   | 01.08.2021                   |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2020        | 20.01.2023        | Ja             | PERMANENT | Nei                                      |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | 01.01.2020        | 20.01.2023        | Ja            | GENERELL          | KUN_ARBEID_HELTID    |


  Scenariomal: Bruker med arbeidsforhold innenfor medl unntak får "Ja" og skal få uavklart medlemskap.

    Gitt følgende medlemsunntak fra MEDL
      | Dekning             | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | FTL_2-9_2_ld_jfr_1c | 09.01.2019      | 09.02.2020      | NEI       | ENDL    | BEL          | GYLD          |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | <Fra og med>    | <Til og med>    | yrkeskode | 100              |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 10.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "1.3.5" gi svaret "<Regel 1.3.5>"

    Eksempler:
      | Fra og med | Til og med | Medlemskap | Regel 1.3.5 | Kommentar
      | 09.01.2019 |            | Nei        | JA          | Arbeidsforhold starter samme dato som unntaket
      | 05.01.2019 |            | Nei        | Ja          | Arbeidsforhold starter i uken før unntaket
      | 13.01.2019 |            | Nei        | Ja          | Arbeidsforhold starter i uken etter unntaket
      | 15.02.2019 |            | Uavklart   | Nei         | Arbeidsforhold starter innenfor unntaksperiode
