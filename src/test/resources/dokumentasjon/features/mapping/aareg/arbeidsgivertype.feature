# language: no
# encoding: UTF-8

Egenskap: Mapping av arbeidsgivertype i arbeidsforhold

  Scenariomal: Bruker har arbeidsforhold med arbeidsgivertype

    Gitt følgende om arbeidgivertype fra AaRegOpplysningspliktigArbeidsgiver fra AaRegArbeidsforhold
      | Arbeidsgivertype        |
      | <AaRegArbeidsgivertype> |

    Når arbeidsforholdene mappes

    Så skal mappet arbeidsgivertype i arbeidsforholdet være
      | Arbeidsgivertype  |
      | <Arbeidsgivertype> |

    Eksempler:
      | AaRegArbeidsgivertype | Arbeidsgivertype |
      | Person                | Person           |
      | Organisasjon          | Organisasjon     |












