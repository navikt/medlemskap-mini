# language: no
# encoding: UTF-8

Egenskap: Mapping av tema

  Scenariomal: Bruker har tema i journalpost fra journalposter fra dokumentoversiktBruker
    Gitt følgende tema fra journalpost
      | Tema   |
      | <Tema> |

    Når journalposter mappes

    Så skal mappede tema være
      | Tema             |
      | <MedlemskapTema> |

    Eksempler:
      | Tema | MedlemskapTema |
      | AAP  | AAP            |
      | AAR  | AAR            |


























