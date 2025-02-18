# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 15

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |


  Scenariomal: Regelflyt regel 15: Bruker får "Uavklart" på spørsmålet om medlemskap hvis bruker er permittert

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende permisjonspermitteringer i arbeidsforholdet
      | Fra og med dato | Til og med dato | PermisjonPermitteringId | Prosent | Type             | Varslingkode  |
      | 10.10.2019      | 01.08.2020      | permisjonPermitteringId | 100     | <permisjonstype> | varslingskode |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 01.01.2018      |                 | 001       | 100              |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "15" gi svaret "<Regel 15>"
    Og skal regel-årsaker være "<Årsak>"

    Eksempler:
      | permisjonstype                     | Regel 15 | Medlemskap | Årsak |
      | PERMITTERING                       | Ja       | Uavklart   | 15    |
      | PERMISJON                          | Nei      | Uavklart   | 33    |
      | PERMISJON_MED_FORELDREPENGER       | Nei      | Uavklart   | 33    |
      | PERMISJON_VED_MILITAERTJENESTE     | Nei      | Uavklart   | 33    |
      | UTDANNINGSPERMISJON                | Nei      | Uavklart   | 33    |
      | VELFERDSPERMISJON                  | Nei      | Uavklart   | 33    |
      | ANDRE_IKKE_LOVFESTEDE_PERMISJONER  | Nei      | Uavklart   | 33    |
      | ANDRE_LOVFESTEDE_PERMISJONER       | Nei      | Uavklart   | 33    |
      | UTDANNINGSPERMISJON_IKKE_LOVFESTET | Nei      | Uavklart   | 33    |
      | UTDANNINGSPERMISJON_LOVFESTET      | Nei      | Uavklart   | 33    |
      | ANNET                              | Nei      | Uavklart   | 33    |