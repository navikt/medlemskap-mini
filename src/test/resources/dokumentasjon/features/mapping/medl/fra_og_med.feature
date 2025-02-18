# language: no
# encoding: UTF-8

Egenskap: Mapping av fraOgMed

  Scenario: Bruker har fraOgMed fra Medl
    Gitt følgende fraOgMed fra MedlMedlemskapsunntak
      | Gyldig fra og med dato   |
      | 2015-03-25               |

    Når medlemskapsuntak mappes

    Så skal mappede fra og med i medlemskap domene være
      | Gyldig fra og med dato |
      | 2015-03-25              |









