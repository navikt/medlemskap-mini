# language: no
# encoding: UTF-8

Egenskap: Første sykedag dagen før første dag i sykemeldingsperioden

  Scenario: Utlede første sykedag fra sykemeldingsperioden

  Gitt følgende sykemeldingsperiode:
    | Fra og med dato | Til og med dato |
    | 05.05.2018      | 14.05.2018      |

  Når første sykedag beregnes fra sykemeldingsperiode

  Så skal første sykedag være "04.05.2018"


