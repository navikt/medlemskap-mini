# language: no
# encoding: UTF-8

Egenskap: Regel "Finnes det registrerte opplysninger på bruker?"

  Scenario: Det finnes opplysninger i MEDL (og ikke i Gosys)
    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland |
      |         | 01.01.2019      | 28.02.2020      | Nei       | ENDL    | USA          |

    Når regel "OPPLYSNINGER" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "Ja"

  Scenariomal: Det finnes opplysninger i Joark
    Gitt følgende journalposter fra Joark
      | JournalpostId | Tittel | Journalposttype | Journalstatus | Tema   |
      | 123           | Test   | T               | AAPEN         | <Tema> |

    Når regel "OPPLYSNINGER" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Tema              | Svar     |
      | MED               | UAVKLART |
      | Ikke tillatt tema | Nei      |


  Scenario: Ser bort fra journalposter med MEL fagsak id
    Gitt følgende journalposter fra Joark
      | JournalpostId | Tittel | Journalposttype | Journalstatus | Tema              | fagsakid |
      | 123           | Test   | T               | AAPEN         | Ikke tillatt tema | MEL-     |

    Når regel "OPPLYSNINGER" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "Nei"


  Scenariomal: Det finnes opplysninger i Gosys
    Gitt følgende oppgaver fra Gosys
      | Aktiv dato | Prioritet | Status   | Tema   |
      | 15.01.2020 | NORM      | <Status> | <Tema> |

    Når regel "OPPLYSNINGER" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Status      | Tema          | Svar     |
      | AAPNET      | MED           | UAVKLART |
      | FERDIGSTILT | MED           | Nei      |
      | AAPNET      | Ikke godkjent | Nei      |


  Scenariomal: Det finnes opplysninger i Gosys og MEDL
    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato   | Er medlem | Lovvalg | Lovvalgsland |
      |         | 01.01.2018      | <Til og med dato> | Nei       | ENDL    | USA          |
    Gitt følgende oppgaver fra Gosys
      | Aktiv dato | Prioritet | Status   | Tema   |
      | 15.01.2020 | NORM      | <Status> | <Tema> |

    Når regel "OPPLYSNINGER" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 01.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Til og med dato | Status      | Tema          | Svar     |
      | 28.02.2020      | AAPNET      | MED           | UAVKLART |
      | 28.02.2020      | FERDIGSTILT | MED           | Ja       |
      | 30.12.2018      | FERDIGSTILT | MED           | Nei      |
      | 30.12.2018      | AAPNET      | MED           | UAVKLART |
      | 28.02.2020      | AAPNET      | Ikke godkjent | Ja       |

  Scenario: Det finnes ikke opplysninger på bruker i MEDL, Joark eller Gosys
    Når regel "OPPLYSNINGER" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15.01.2020      | 30.01.2020      | Nei                           |

    Så skal svaret være "Nei"
