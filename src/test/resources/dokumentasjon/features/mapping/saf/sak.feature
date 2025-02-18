# language: no
# encoding: UTF-8

Egenskap: Mapping av journalpostId

  Scenario: Bruker har fagsakId i sak fra journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende fagsakId fra dokument
      | fagsakId  |
      | 439560100 |

    Når journalposter mappes

    Så skal mappede fagsakId være
      | fagsakId  |
      | 439560100 |


