# language: no
# encoding: UTF-8
# Mest sannsynlig vil kun ett felt fra IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum forekomme

Egenskap: Mapping av OppholdstillatelseEllerOppholdsPaSammeVilkar

  Scenario: Bruker har følgende fra Uavklart
    Gitt følgende Uavklart
      | Uavklart   |
      |            |

    Når GjeldendeOppholdsstatus med Uavklart mappes

    Så skal mappede Uavklart være
      | Uavklart |
      | true     |
