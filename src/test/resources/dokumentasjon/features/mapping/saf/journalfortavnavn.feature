# language: no
# encoding: UTF-8

Egenskap: Mapping av journalpostId

  Scenario: Bruker har journalpostId i journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende journalført av navn fra journalpost
      | Journalført av   |
      | medlemskap-joark |

    Når journalposter mappes

    Så skal mappede journalført av navn være
      | Journalført av   |
      | medlemskap-joark |









