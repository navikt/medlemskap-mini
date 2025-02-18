# language: no
# encoding: UTF-8

Egenskap: Regel 19.8: Har bruker opphold på samme vilkår flagg

  Scenariomal: Regel 19.8: gir Ja hvis bruker har opphold på samme vilkår flagg

    Gitt følgende oppholdstillatelse
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Type      | Oppholdstillatelse på samme vilkår flagg   |
      | 01.01.2018        |                   | Ja             | PERMANENT | <Oppholdstillatelse på samme vilkår flagg> |

    Når regel "19.8" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 23.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Oppholdstillatelse på samme vilkår flagg | Svar |
      | Ja                                       | JA   |
      | Nei                                      | NEI  |