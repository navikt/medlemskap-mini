# language: no
# encoding: UTF-8

Egenskap: Mapping av journalpostId

  Scenario: Bruker har dokument i dokumenter fra journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende dokumentInfoId fra dokument
      | DokumentInfoId |
      | 439560100      |

    Og følgende tittel fra dokument
      | Tittel       |
      | MASKERT_FELT |

    Når journalposter mappes

    Så skal mappede dokumentInfoId være
      | DokumentInfoId |
      | 439560100      |

    Og mappede tittel være
      | Tittel       |
      | MASKERT_FELT |









