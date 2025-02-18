# language: no
# encoding: UTF-8

Egenskap: Mapping av lovvalg i Medlemskap

  Scenariomal: Bruker har lovvalg fra Medl
    Gitt følgende om lovvalg fra MedlMedlemskapsunntak
      | Lovvalg   |
      | <Lovvalg> |

    Når medlemskapsuntak mappes

    Så skal mappet lovvalg i Medlemskap være
      | Lovvalg              |
      | <MedlemskapLovvalg>  |

    Eksempler:
      | Lovvalg | MedlemskapLovvalg |
      | ENDL    | ENDL              |
      | FORL    | FORL              |
      | UAVK    | UAVK              |




