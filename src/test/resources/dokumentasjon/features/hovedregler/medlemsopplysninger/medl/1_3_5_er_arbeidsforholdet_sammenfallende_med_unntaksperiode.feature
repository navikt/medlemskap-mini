# language: no
# encoding: UTF-8

Egenskap: Regel 1.3.5: Er arbeidsforholdet sammenfallende med perioden uten medlemskap?

  Scenariomal: Regel 1.3.5: Er arbeidsforholdet sammenfallende med perioden uten medlemskap?

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | 07.08.2019      | 01.10.2021      | Nei       | ENDL    | BEL          | GYLD          |

    Og følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype | Arbeidsgiver Id |
      | <Fra og med>    | <Til og med>    | Organisasjon     | NORMALT             | 1               |

    Når regel "1.3.5" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Fra og med | Til og med | Svar | Kommentar
      | 07.08.2019 |            | Ja   | Arbeidsforhold starter samme dato som unntaket
      | 05.08.2019 |            | Ja   | Arbeidsforhold starter i uken før unntaket
      | 12.08.2019 |            | Ja   | Arbeidsforhold starter i uken etter unntaket
      | 01.01.2022 |            | Nei  | Arbeidsforholdsperiode etter unntaket er avsluttet
      | 21.08.2019 |            | Nei  | Arbeidsforhold starter mer enn 1 uke før unntaket
      | 24.07.2019 |            | Nei  | Arbeidsforhold starter mer enn 1 uke etter unntaket starter