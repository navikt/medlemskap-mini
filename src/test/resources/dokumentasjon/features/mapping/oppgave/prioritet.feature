# language: no
# encoding: UTF-8

Egenskap: Mapping av oppgave

  Scenariomal: Bruker har oppgavestatus fra OppgOppgave
    Gitt følgende prioritet fra OppgOppgave
      | Prioritet   |
      | <Prioritet> |

    Når oppgaver mappes

    Så skal prioritet i Oppgave domene være
      | Prioritet             |
      | <MedlemskapPrioritet> |

    Eksempler:
      | Prioritet  | MedlemskapPrioritet |
      | HOY        | HOY                 |
      | NORM       | NORM                |
      | LAV        | LAV                 |





























