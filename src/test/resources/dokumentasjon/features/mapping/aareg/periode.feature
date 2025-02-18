# language: no
# encoding: UTF-8

Egenskap: Mapping av periode i arbeidsforhold

  Scenario: Bruker har flere arbeidsfohrold
    Gitt følgende om AaRegPeriode i fra AaRegAnsettelsesperiode fra AaRegArbeidsforhold
      | Gyldig fra og med dato   | Gyldig til og med dato|
      | 2015-03-25               | 2016-02-03            |

    Når arbeidsforholdene mappes

    Så skal mappede periode i arbeidsforhold være
      | Fra og med dato | Til og med dato |
      | 2015-03-25      |  2016-02-03     |









