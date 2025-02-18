# language: no
# encoding: UTF-8

Egenskap: Mapping av periodestatus i Medlemskap

  Scenariomal: Bruker har lovvalg fra Medl
    Gitt følgende om status fra MedlMedlemskapsunntak
      | Status   |
      | <Status> |

    Når medlemskapsuntak mappes

    Så skal mappet periodeStatus i Medlemskap være
      | Periodestatus   |
      | <Periodestatus> |

    Eksempler:
      | Status  | Periodestatus |
      | GYLD    | GYLD          |
      | AVST    | AVST          |
      | UAVK    | UAVK          |


