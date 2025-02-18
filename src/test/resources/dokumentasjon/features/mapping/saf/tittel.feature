# language: no
# encoding: UTF-8

Egenskap: Mapping av tittel

  Scenario: Bruker har tittel i journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende tittel fra journalpost
      | Tittel       |
      | MASKERT_FELT |

    Når journalposter mappes

    Så skal mappede tittel i journalpost være
      | Tittel       |
      | MASKERT_FELT |









