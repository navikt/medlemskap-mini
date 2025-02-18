# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 14

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |


  Scenariomal: Regelflyt regel 14: Bruker får "ja" hvis arbeidsgiver er offentlig sektor
    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte | Juridisk enhetstype   |
      | 1             | BEDR             | NOR      | 10             | <Juridisk enhetstype> |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 100              |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal regel "14" gi svaret "<Regel 14>"

    Eksempler:
      | Juridisk enhetstype | Regel 14 |
      | AS                  | Nei      |
      | SE                  | Nei      |
      | STAT                | Ja       |
      | KOMM                | Ja       |
      | FKF                 | Ja       |
      | FYLK                | Ja       |
      | KF                  | Ja       |
      | SF                  | Ja       |