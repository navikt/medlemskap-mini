# language: no
# encoding: UTF-8

Egenskap: Regel 19.3: Har bruker arbeids- og oppholdstillatelse i kontrollperiode?

  Scenariomal: Regel 19.3: Har bruker arbeids- og oppholdstillatelse i kontrollperiode?

    Gitt følgende oppholdstillatelse
      | Gyldig fra og med   | Gyldig til og med   | Har tillatelse   | Type                                 | Oppholdstillatelse på samme vilkår flagg |
      | <Gyldig fra og med> | <Gyldig til og med> | <Har tillatelse> | <OppholdstillaelsePaSammeVilkarType> | Nei                                      |

    Når regel "19.3" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 23.01.2020      | 30.01.2020      | Nei                           |

    Og kontrollperiode for oppholdstillatelse beregnes med følgende parametre:
      | Ytelse   | Fra og med dato | Til og med dato | Første dag for ytelse   |
      | <Ytelse> | 23.01.2020      | 30.01.2020      | <Første dag for ytelse> |

    Så skal startdato for ytelse være "<Startdato for ytelse>"

    Og skal kontrollperioden være:
      | Fra og med dato                   | Til og med dato                   |
      | <Kontrollperiode fra og med dato> | <Kontrollperiode til og med dato> |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Svar | OppholdstillaelsePaSammeVilkarType | Ytelse     | Første dag for ytelse | Startdato for ytelse | Kontrollperiode fra og med dato | Kontrollperiode til og med dato |
      | 01.01.2018        |                   | Ja             | Ja   | PERMANENT                          | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      | 23.01.2019        |                   | Ja             | Nei  | PERMANENT                          | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      |                   |                   | Ja             | Nei  | PERMANENT                          | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      | 22.01.2019        |                   | Ja             | Ja   | PERMANENT                          | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      | 22.01.2019        | 22.03.2020        | Ja             | Ja   | PERMANENT                          | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      | 01.01.2018        | 22.03.2020        | Ja             | Ja   | MIDLERTIDIG                        | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      |                   | 21.03.2020        | Ja             | Nei  | MIDLERTIDIG                        | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |
      |                   | 22.03.2020        | Ja             | Nei  | MIDLERTIDIG                        | SYKEPENGER | 22.01.2020            | 22.01.2020           | 22.01.2019                      | 22.03.2020                      |