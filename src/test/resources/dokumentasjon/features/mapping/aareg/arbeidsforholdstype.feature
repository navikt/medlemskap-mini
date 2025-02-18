# language: no
# encoding: UTF-8

Egenskap: Mapping av arbeidsforholdstype i arbeidsforhold

  Scenariomal: Bruker har arbeidsforholdstype i arbeidsforholdet
    Gitt følgende om type fra AaRegArbeidsforhold
      | Type    |
      | <Type>  |

    Når arbeidsforholdene mappes

    Så skal mappet arbeidsforholdstype i arbeidsforholdet være
      | Arbeidsforholdstype  |
      | <Arbeidsforholdstype>|

    Eksempler:
      | Type                                              | Arbeidsforholdstype |
      | maritimtArbeidsforhold                            | MARITIMT            |
      | forenkletOppgjoersordning                         | FORENKLET           |
      | frilanserOppdragstakerHonorarPersonerMm           | FRILANSER           |
      | ordinaertArbeidsforhold                           | NORMALT             |
      | pensjonOgAndreTyperYtelserUtenAnsettelsesforhold  | ANDRE               |







