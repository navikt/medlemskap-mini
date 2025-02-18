# language: no
# encoding: UTF-8

Egenskap: Mapping av lovvalgsland i Medlemskap

  Scenariomal: Bruker har lovvalgsland fra Medl
    Gitt følgende om lovvalgsland fra MedlMedlemskapsunntak
      | Lovvalgsland    |
      | <Lovvalgsland>  |

    Når medlemskapsuntak mappes

    Så skal mappet lovvalgsland i Medlemskap være
      | Lovvalgsland             |
      | <MedlemskapLovvalgland>  |

    Eksempler:
      | Lovvalgsland | MedlemskapLovvalgland |
      | BEL          | BEL                   |
      | USA          | USA                   |
      | ALA          | ALA                   |



