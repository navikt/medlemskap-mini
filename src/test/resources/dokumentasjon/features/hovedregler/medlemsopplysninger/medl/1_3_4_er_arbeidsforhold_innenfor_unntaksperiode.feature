# language: no
# encoding: UTF-8

Egenskap: Regel 1.3.4: Finnes det arbeidsforhold i Aareg i samme periode som unntaket?

  Scenariomal: Regel 1.3.4: Finnes det arbeidsforhold i Aareg i samme periode som unntaket?

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | 01.08.2019      | 01.10.2019      | Nei       | ENDL    | BEL          | GYLD          |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             | 1               |

    Når regel "1.3.4" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Fra og med | Til og med | Svar | Kommentar
      | 01.01.2019 | 16.09.2019 | Ja   | Arbeidsforhold slutter i unntaksperiode
      | 15.09.2019 |            | Ja   | Arbeidsforhold starter i unntaksperiode
      | 01.01.2019 | 01.05.2019 | Nei  | Arbeidsforhold starter og slutter før unntaksperiode
      | 01.01.2020 | 01.05.2020 | Nei  | Arbeidsforhold starter og slutter etter unntaksperiode