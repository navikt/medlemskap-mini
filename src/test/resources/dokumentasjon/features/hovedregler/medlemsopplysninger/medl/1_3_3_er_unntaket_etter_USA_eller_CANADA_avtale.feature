# language: no
# encoding: UTF-8

Egenskap: Regel 1.3.3: Er unntaket etter USA- eller Canada-avtalen?

  Scenariomal: Regel 1.3.3:  Er unntaket etter USA- eller Canada-avtalen?

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland   | Periodestatus |
      | 01.01.2019      | 28.01.2020      | Nei       | ENDL    | <Lovvalgsland> | GYLD          |

    Når regel "1.3.3" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Lovvalgsland | Svar |
      | USA          | Ja   |
      | CAN          | Ja   |
      | BEL          | Nei  |

  Scenariomal: Regel 1.3.3:  Er unntaket etter USA- eller Canada-avtalen? Med ulike perioder

    Gitt følgende medlemsunntak fra MEDL
      | Fra og med dato | Til og med dato | Er medlem | Lovvalg | Lovvalgsland | Periodestatus |
      | <Fra og med>    | <Til og med>    | Nei       | ENDL    | USA          | GYLD          |

    Når regel "1.3.3" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 10.02.2020      | Nei                           |

    Så skal svaret være "<Svar>"
    Og skal avklaringen være som definert i RegelId
    Og skal begrunnelsen være som definert i RegelId

    Eksempler:
      | Fra og med | Til og med | Svar | Kommentar
      | 20.02.2019 | 28.12.2019 | Ja   | Unntaksperiode starter og slutter innenfor kontrollperiode
      | 01.12.2018 | 01.06.2019 | Ja   | Unntaksperiode starer utenfor og slutter innenfor kontrollperiode
      | 01.06.2019 | 01.06.2021 | Ja   | Unntaksperiode starter innenfor og slutter utenfor kontrollperiode
      | 01.12.2018 | 01.06.2021 | Ja   | Unntaksperiode starter utenfor, slutter utenfor og går over hele kontrollperioden
      | 01.01.2018 | 01.12.2018 | Nei  | Unntaksperiode starter og slutter før kontrollperiode
      | 01.01.2021 | 01.12.2021 | Nei  | Unntaksperiode starter og slutter etter kontrollperiode