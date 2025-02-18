# language: no
# encoding: UTF-8

Egenskap: Regel 1.1: Er alle perioder siste 12 mnd avklarte?

  Scenariomal: Regel 1.1: Er alle perioder siste 12 mnd avklarte? En periode fra MEDL

    Gitt følgende medlemsunntak fra MEDL
      | Dekning | Fra og med dato | Til og med dato   | Er medlem | Lovvalg   | Lovvalgsland | Periodestatus   |
      |         | 01.01.2019      | <Til og med dato> | Ja        | <Lovvalg> | NOR          | <Periodestatus> |

    Når regel "1.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Til og med dato | Lovvalg | Periodestatus | Svar | Kommentar                                                                            |
      | 01.06.2020      | ENDL    | GYLD          | Ja   | Avsluttet periode etter inputperiode, avklarte lovvalg og periodestatus              |
      | 28.01.2019      | UAVK    | GYLD          | Ja   | Avsluttet uavklart periode før 12 mnd tilbake, ingen perioder vil være med i sjekken |
      | 29.01.2019      | UAVK    | GYLD          | Nei  | Avsluttet periode innenfor 12 mnd tilbake med uavklart lovvalg                       |
      | 28.01.2020      | ENDL    | AVST          | Nei  | Avsluttet periode før inputperiode med avvist periodestatus                          |
      | 28.01.2020      |         | GYLD          | Ja   | Avsluttet periode før inputperiode tomt lovvalg og gyldig periodestatus              |
      | 01.06.2020      | ENDL    |               | Ja   | Avsluttet periode etter inputperiode, gyldig lovvalg og tom periodestatus            |
      | 01.06.2020      |         |               | Ja   | Avsluttet periode etter inputperiode, tom lovvalg og tom periodestatus               |
      | 01.06.2020      | FORL    |               | Nei  | Avsluttet periode etter inputperiode med foreløpig lovvalg og gyldig periodestatus   |
      | 01.06.2020      | ENDL    | UAVK          | Nei  | Avsluttet periode etter inputperiode med periodestatus under avklaring               |

  Scenariomal: Regel 1.1: Er alle perioder siste 12 mnd avklarte? Flere perioder fra MEDL

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg   | Lovvalgsland | Periodestatus   |
      | 01.01.2019      | 30.06.2019      | Ja        | ENDL      | NOR          | GYLD            |
      | 01.07.2019      | 01.07.2020      | Ja        | <Lovvalg> | NOR          | <Periodestatus> |

    Når regel "1.1" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"

    Eksempler:
      | Lovvalg | Periodestatus | Svar |
      | ENDL    | GYLD          | Ja   |
      | ENDL    |               | Ja   |
      | UAVK    | GYLD          | Nei  |
      | ENDL    | AVST          | Nei  |
      | FORL    | AVST          | Nei  |