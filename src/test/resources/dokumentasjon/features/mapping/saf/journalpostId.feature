# language: no
# encoding: UTF-8

Egenskap: Mapping av journalpostId

  Scenario: Bruker har journalpostId i journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende journalpostId fra journalpost
      | JournalpostId  |
      | 439560100      |

    Når journalposter mappes

    Så skal mappede journalpostid være
      | JournalpostId |
      | 439560100     |









