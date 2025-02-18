# language: no
# encoding: UTF-8

Egenskap: Regel 1.4: Er det periode med medlemskap innenfor 12 mnd?

  Scenariomal: Regel 1.4: Er det periode med medlemskap innenfor 12 mnd? En periode fra MEDL

    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato   | Er medlem   | Lovvalg | Lovvalgsland | Periodestatus |
      |         | 01.01.2019      | <Til og med dato> | <Er medlem> | ENDL    | NOR          | GYLD          |

    Når regel "1.4" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Er medlem | Til og med dato | Svar | Kommentar                                                                               |
      | Ja        | 01.06.2020      | Ja   | Avsluttet periode uten medlemskap etter inputperiode                                    |
      | Ja        | 02.02.2020      | Ja   | Avsluttet periode uten medlemskap i inputperiode                                        |
      | Ja        | 29.01.2020      | Ja   | Avsluttet periode uten medlemskap samtidig med singelinput (inputperiode, fom-dato - 1) |
      | Ja        | 28.01.2020      | Nei  | Avsluttet periode uten medlemskap før inputperiode                                      |
      | Nei       | 01.06.2020      | Nei  | Avsluttet periode med  medlemskap etter inputperiode                                    |

  Scenariomal: Regel 1.4: Er det periode med medlemskap innenfor 12 mnd? Flere perioder fra MEDL

    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato   | Til og med dato    | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      |         | 01.01.2019        | <Til og med dato>  | Ja        | ENDL    | NOR          | GYLD          |
      |         | <Fra og med dato> | <Til og med dato2> | Ja        | ENDL    | NOR          | GYLD          |

    Når regel "1.4" kjøres med følgende parametre
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