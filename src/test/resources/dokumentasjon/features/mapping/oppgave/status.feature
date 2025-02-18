# language: no
# encoding: UTF-8

Egenskap: Mapping av oppgave

  Scenariomal: Bruker har oppgavestatus fra OppgOppgave
    Gitt følgende Status fra OppgOppgave
      | Status    |
      | <Status> |

    Når oppgaver mappes

    Så skal status i Oppgave domene være
      | Status             |
      | <MedlemskapStatus> |

    Eksempler:
      | Status            | MedlemskapStatus |
      | OPPRETTET         | OPPRETTET        |
      | AAPNET            | AAPNET           |
      | UNDER_BEHANDLING  | UNDER_BEHANDLING |
      | FERDIGSTILT       | FERDIGSTILT      |
      | FEILREGISTRERT    | FEILREGISTRERT   |





























