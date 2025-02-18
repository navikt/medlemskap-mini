# language: no
# encoding: UTF-8

Egenskap: Regel 0.5: Skal regler overstyres?

  Scenariomal: Regel 0.5 - Overstyring av regler 3, 5 og 12 hvis følgende kriterier er oppfylt:
  - Ytelse sykepenger
  - Norsk statsborger
  - Og bruker har arbeidsforhold i AaReg som ikke er frilanser (eller selvstendig næringsdrivende)
  - Bruker har svart nei på brukerspørsmålet "Har du jobbet utenfor Norge siste 12 måneder?"

    Gitt følgende statsborgerskap i personhistorikken
      | Landkode          | Fra og med dato | Til og med dato |
      | <Statsborgerskap> | 01.01.2000      |                 |

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | 15.01.2020      |                 | Organisasjon     | NORMALT             | 1               |

    Og følgende arbeidsavtaler i arbeidsforholdet
      | Fra og med dato | Til og med dato | Yrkeskode | Stillingsprosent |
      | 15.01.2020      |                 | 001       | 10               |

    Og følgende arbeidsgiver i arbeidsforholdet
      | Identifikator | Arbeidsgivertype | Landkode | Antall ansatte |
      | 1             | BEDR             | NOR      | 2              |

    Når regel "0.5" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge | Ytelse   |
      | 30.01.2020      | 30.01.2021      | <Arbeid utenfor Norge?>       | <Ytelse> |

    Så skal svaret være "<Regel 0.5>"
    Og skal begrunnelsen være som definert i RegelId
    Og skal avklaringen være som definert i RegelId

    Eksempler:
      | Statsborgerskap | Ytelse     | Arbeid utenfor Norge? | Regel 0.5 | Begrunnelse                                                                   |
      | NOR             | DAGPENGER  | Nei                   | Nei       |                                                                               |
      | NOR             | DAGPENGER  | Ja                    | Nei       |                                                                               |
      | NOR             | SYKEPENGER | Nei                   | Ja        | Svar på brukerspørsmålet er Nei, og regler 3, 5 og 12 skal derfor overstyres. |
      | NOR             | SYKEPENGER | Ja                    | Nei       |                                                                               |
      | BEL             | DAGPENGER  | Nei                   | Nei       |                                                                               |
      | BEL             | DAGPENGER  | Ja                    | Nei       |                                                                               |
      | BEL             | SYKEPENGER | Nei                   | Nei       |                                                                               |
      | BEL             | SYKEPENGER | Ja                    | Nei       |                                                                               |