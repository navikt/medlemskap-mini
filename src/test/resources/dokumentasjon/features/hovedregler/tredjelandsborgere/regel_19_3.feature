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

    Så skal svaret være "<Svar>"

    Eksempler:
      | Gyldig fra og med | Gyldig til og med | Har tillatelse | Svar | OppholdstillaelsePaSammeVilkarType |
      | 01.01.2018        |                   | Ja             | Ja   | PERMANENT                          |
      | 23.01.2019        |                   | Ja             | Nei  | PERMANENT                          |
      | 22.01.2019        |                   | Ja             | Ja   | PERMANENT                          |
      | 22.01.2019        | 22.03.2020        | Ja             | Ja   | PERMANENT                          |
      | 22.01.2019        | 22.03.2020        | Ja             | Ja   | MIDLERTIDIG                        |
      | 22.01.2019        | 21.03.2020        | Ja             | Nei  | MIDLERTIDIG                        |
