# language: no
# encoding: UTF-8

Egenskap: Mapping av familierelasjoner fra PDL HentPerson.Familierelasjon

  Scenario: En person som har flere familierelasjoner
    Gitt følgende familierelasjoner fra PDL:
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 20041276216            | BARN                   | FAR                  |

    Når familierelasjoner mappes

    Så skal mappede familierelasjoner være
      | Relatert persons ident | Relatert persons rolle | Min rolle for person |
      | 20041276216            | BARN                   | FAR                  |


