# language: no
# encoding: UTF-8

Egenskap: Beregne kontrollperiode for oppholdstillatelse fra inputperiode og første dag for ytelse

Scenariomal:  Beregne kontrollperiode for oppholdstillatelse for forskjellige ytelse

Når kontrollperiode for oppholdstillatelse beregnes med følgende parametre:
| Ytelse   | Fra og med dato | Til og med dato | Første dag for ytelse   |
| <Ytelse> | 05.05.2020      | 15.05.2020      | <Første dag for ytelse> |

Så skal startdato for ytelse være "<Startdato for ytelse>"

Og skal kontrollperioden være:
| Fra og med dato                   | Til og med dato                   |
| <Kontrollperiode fra og med dato> | <Kontrollperiode til og med dato> |

Eksempler:
| Ytelse           | Første dag for ytelse | Startdato for ytelse | Kontrollperiode fra og med dato | Kontrollperiode til og med dato |
| SYKEPENGER       |                       | 04.05.2020           | 04.05.2019                      | 04.07.2020                      |
| SYKEPENGER       | 01.04.2020            | 01.04.2020           | 01.04.2019                      | 01.06.2020                      |
| DAGPENGER        |                       | 04.05.2020           | 04.05.2019                      | 04.07.2020                      |
| DAGPENGER        | 01.04.2020            | 01.04.2020           | 01.04.2019                      | 01.06.2020                      |
| ENSLIG_FORSORGER |                       | 04.05.2020           | 04.05.2019                      | 04.07.2020                      |
| ENSLIG_FORSORGER | 01.04.2020            | 01.04.2020           | 01.04.2019                      | 01.06.2020                      |
