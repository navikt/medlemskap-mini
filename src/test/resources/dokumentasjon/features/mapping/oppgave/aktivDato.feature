# language: no
# encoding: UTF-8

Egenskap: Mapping av oppgave

  Scenario: Bruker har aktivDato i oppgave
    Gitt følgende om aktivDato i OppgOppgave
      | AktivDato   |
      | 2015-03-25  |

    Når oppgaver mappes

    Så skal mappede  aktivDato i medlemskap domene være
      | Aktiv dato |
      | 2015-03-25 |










