# language: no
# encoding: UTF-8

Egenskap: Regel 1.3.1: Er det periode uten medlemskap innenfor 12 mnd?

  Scenariomal: Regel 1.3.1: Er det periode uten medlemskap innenfor 12 mnd? Sjekk én periode fra MEDL

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato   | Er medlem   | Lovvalg | Lovvalgsland | Periodestatus |
      | 01.01.2019      | <Til og med dato> | <Er medlem> | ENDL    | BEL          | GYLD          |

    Når regel "1.3.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Er medlem | Til og med dato | Svar | Kommentar                                                                               |
      | Nei       | 01.06.2020      | Ja   | Avsluttet periode uten medlemskap etter inputperiode                                    |
      | Nei       | 02.02.2020      | Ja   | Avsluttet periode uten medlemskap i inputperiode                                        |
      | Nei       | 29.01.2020      | Ja   | Avsluttet periode uten medlemskap samtidig med singelinput (inputperiode, fom-dato - 1) |
      | Nei       | 28.01.2020      | Nei  | Avsluttet periode uten medlemskap før inputperiode                                      |
      | Ja        | 01.06.2020      | Nei  | Avsluttet periode med  medlemskap etter inputperiode                                    |

  Scenariomal: Regel 1.3.1: Er det periode uten medlemskap innenfor 12 mnd? Sjekk flere perioder fra MEDL

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato   | Til og med dato    | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | 01.01.2019        | <Til og med dato>  | Nei       | ENDL    | USA          | GYLD          |
      | <Fra og med dato> | <Til og med dato2> | Nei       | ENDL    | USA          | GYLD          |

    Når regel "1.3.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Til og med dato | Fra og med dato | Til og med dato2 | Svar | Kommentar                                                                                             |
      | 01.06.2019      | 02.06.2019      | 10.02.2020       | Ja   | Avsluttet sammenhengende perioder uten medlemskap etter inputperiode                                  |
      | 01.06.2019      | 02.06.2019      | 09.02.2020       | Ja   | Avsluttet sammenhengende perioder uten medlemskap i inputperiode                                      |
      | 01.06.2019      | 02.06.2019      | 29.01.2020       | Ja   | Avsluttet sammenhengende perioder uten medlemskap samtidig med inputdato (inputperiode, fom-dato - 1) |
      | 01.06.2019      | 02.06.2019      | 28.01.2020       | Nei  | Avsluttet sammenhengende perioder uten medlemskap før inputperiode                                    |
      | 01.06.2019      | 03.06.2019      | 01.06.2021       | Nei  | Hull i perioder                                                                                       |
