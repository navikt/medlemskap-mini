# language: no
# encoding: UTF-8

Egenskap: Mapping av statsborgerskap fra PDL hentPerson

  Scenario: Navn uten mellomnavn fra PDL
    Gitt følgende navn fra PDL
      | Fornavn | Etternavn |
      | Test    | Person    |

    Når navn mappes

    Så skal mappet navn være
      | Fornavn | Etternavn |
      | Test    | Person    |


  Scenario: Fult navn fra PDL
    Gitt følgende navn fra PDL
      | Fornavn | Etternavn |
      | Test    | Person    |

    Når navn mappes

    Så skal mappet navn være
      | Fornavn | Etternavn |
      | Test    | Person    |

