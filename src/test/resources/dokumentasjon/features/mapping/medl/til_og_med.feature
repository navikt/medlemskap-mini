# language: no
# encoding: UTF-8

Egenskap: Mapping av tilOgMed

  Scenario: Bruker har tilOgMed fra Medl
    Gitt følgende tilOgMed fra MedlMedlemskapsunntak
      | Gyldig til og med dato   |
      | 2020-03-25               |

    Når medlemskapsuntak mappes

    Så skal mappede til og med i medlemskap domene være
      | Gyldig til og med dato |
      | 2020-03-25              |









