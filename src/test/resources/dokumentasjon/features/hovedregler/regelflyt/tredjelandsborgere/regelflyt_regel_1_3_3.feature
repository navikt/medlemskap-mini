# language: no
# encoding: UTF-8

Egenskap: Tredjelandsborger med medl unntak.

  Bakgrunn:

    Gitt følgende bostedsadresser i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2020      |                 |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2020      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator       | Arbeidsgivertype | Landkode | Antall ansatte |
      | organisasjonsnummer | STAT             | NOR      | 10             |

    Og følgende detaljer om ansatte for arbeidsgiver
      | Antall ansatte | Gyldighetsperiode gyldig fra | Gyldighetsperiode gyldig til |
      | 10             | 01.01.2020                   | 01.08.2021                   |

    Og følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg |
      | 01.01.2020        | 20.01.2023        | Ja             | PERMANENT | Nei                                      |

    Og følgende arbeidsadgang
      | Gyldig fra og med | Gyldig til og med | Arbeidsadgang | ArbeidsadgangType | ArbeidomfangKategori |
      | 01.01.2020        | 20.01.2023        | Ja            | GENERELL          | KUN_ARBEID_HELTID    |


  Scenariomal: Tredjelandsborger med medl unntak "Ja" skal få uavklart medlemskap hvis oppholdstillatelse ikke er i orden.

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.1975      |                 |

    Og følgende medlemsunntak fra MEDL
      | Dekning             | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland   | Periodestatus |
      | FTL_2-9_2_ld_jfr_1c | 01.01.2020      | 01.06.2021      | NEI       | ENDL    | <lovvalgsland> | GYLD          |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 10.10.1975      |                 | yrkeskode | 100              |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 05.01.2021      | 12.02.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "1.3.3" gi svaret "<Regel 1.3.3>"
    Og skal regel-årsaker være "<Årsak>"

    Eksempler:
      | Statsborgerskap | lovvalgsland | Medlemskap | Regel 1.3.3 | Årsak |
      | USA             | USA          | Uavklart   | JA          | 1.3.3 |
      | CAN             | CAN          | Uavklart   | JA          | 1.3.3 |
