# language: no
# encoding: UTF-8

Egenskap: Regelflyt for regel 21

  Bakgrunn:
    Gitt følgende bostedsadresser i personhistorikken
      | Adresse | Landkode | Fra og med dato | Til og med dato |
      | Oslo    | NOR      | 01.01.2000      |                 |

    Og følgende statsborgerskap i personhistorikken
      | Landkode | Fra og med dato | Til og med dato |
      | NOR      | 01.01.2000      |                 |



    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 9              |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode   | Stillingsprosent |
      | 01.01.2018      |                 | <Yrkeskode> | 100              |

  Scenariomal: Regelflyt regel 21: Bruker får "uavklart" på spørsmålet om medlemskap hvis arbeidsperioden ikke dekker referanse perioden
    Gitt  følgende arbeidsforhold fra AAReg
      | Fra og med dato     | Til og med dato     | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | <Første fra og med> | <Første til og med> | Organisasjon     | NORMALT             | 1               |

    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 10.06.2020      | 10.06.2020      | Nei                           |

    Så skal svaret være "<Medlemskap>"
    Og skal regel "21" gi svaret "<Svar>"

    Eksempler:
      | Første fra og med | Første til og med | Svar | Medlemskap |
      | 01.01.2018        |                   | Ja   | Ja         |
      | 01.01.2018        | 11.06.2020        | Ja   | Ja         |
      | 01.01.2018        | 01.06.2020        | Nei  | UAVKLART   |

  Scenario: Regelflyt regel 21: bruker får "ja" på spørsmålet om medlemskap hvis det er sammengengende arbeid i kontroll perioden

    Gitt  følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             | 1               |


    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse     |
      | 30.01.2020      | 30.01.2021      | Nei                           | SYKEPENGER |


    Så skal svaret være "Ja"
    Og skal regel "21" gi svaret "Ja"

  Scenario: Regelflyt regel 21: bruker får "Ja" for regel 21, hvis bruker har hatt kontinuerlig arbeidsforhold i minst 2 dager for barnebriller.

    Gitt  følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 29.01.2020      |                 | Organisasjon     | NORMALT             | 1               |


    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse        |
      | 30.01.2020      | 30.01.2021      | Nei                           | BARNE_BRILLER |


    Så skal svaret være "UAVKLART"
    Og skal regel "21" gi svaret "Ja"

  Scenario: Regelflyt regel 21: bruker får "Nei" for regel 21, hvis bruker har hatt kontinuerlig arbeidsforhold i minst 2 dager for barnebriller.

    Gitt  følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 30.01.2020      |                 | Organisasjon     | NORMALT             | 1               |


    Når medlemskap beregnes med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse        |
      | 30.01.2020      | 30.01.2021      | Nei                           | BARNE_BRILLER |


    Så skal svaret være "UAVKLART"
    Og skal regel "21" gi svaret "Nei"
