# language: no
# encoding: UTF-8

Egenskap: Mapping av erMedlem

  Scenariomal: Bruker har medlem fra Medl
    Gitt følgende om medlem fra MedlMedlemskapsunntak
      | Medlem    |
      | <Medlem>  |

    Når medlemskapsuntak mappes

    Så skal mappet erMedlem i Medlemskap domene være
      | Er medlem  |
      | <erMedlem> |

    Eksempler:
      | Medlem    | erMedlem |
      | true      | true     |
      | false     | false    |









