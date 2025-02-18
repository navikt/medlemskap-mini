# language: no
# encoding: UTF-8

Egenskap: Mapping av journalpostId

  Scenariomal: Bruker har journalposttype i journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende journalposttype fra journalpost
      | Journalposttype   |
      | <Journalposttype> |

    Når journalposter mappes

    Så skal mappede journalposttype være
      | JournalpostType             |
      | <MedlemskapJournalposttype> |

    Eksempler:
      | Journalposttype | MedlemskapJournalposttype|
      | I               | I                        |
      | U               | U                        |
      | N               | N                        |












