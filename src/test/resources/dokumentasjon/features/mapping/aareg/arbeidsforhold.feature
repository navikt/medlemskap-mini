# language: no
# encoding: UTF-8

Egenskap: Mapping av arbeidsforhold

  Scenario: Bruker har flere arbeidsforhold

    Gitt følgende om AaRegPeriode i fra AaRegAnsettelsesperiode fra AaRegArbeidsforhold
      | Gyldig fra og med dato   | Gyldig til og med dato|
      | 2015-03-25               | 2016-02-03            |

    Og følgende om arbeidgivertype fra AaRegOpplysningspliktigArbeidsgiver fra AaRegArbeidsforhold
      | Arbeidsgivertype |
      | Organisasjon     |

    Og følgende om type fra AaRegArbeidsforhold
      | Type                      |
      | ordinaertArbeidsforhold   |

    Når arbeidsforholdene mappes

    Så skal mappede periode i arbeidsforhold være
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype|
      | 2015-03-25      |  2016-02-03     | Organisasjon     | NORMALT            |

    Og skal mappet arbeidsgivertype i arbeidsforholdet være
      | Arbeidsgivertype | Arbeidsforholdstype|
      | Organisasjon     | NORMALT            |

    Og skal mappet arbeidsforholdstype i arbeidsforholdet være
      | Arbeidsforholdstype|
      | NORMALT            |








