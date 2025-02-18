# language: no
# encoding: UTF-8

Egenskap: Mapping av kontaktadresser fra PDL HentPerson.Doedsfall


  Scenario: En person som har registrert doedsfall
    Gitt følgende opplysninger om doedsfall fra PDL:
      | Dødsdato       |
      | 2015-03-25     |

    Når doedsfall mappes

    Så skal mappede doedsfall være
      | Dødsdato  |
      | 2015-03-25 |


    Scenario: En person som har registrert doedsfall
      Gitt følgende opplysninger om doedsfall fra PDL:
        | Dødsdato       |
        | 2015-03-25      |
        | 2015-03-25      |

      Når doedsfall mappes

      Så skal mappede doedsfall være
        | Dødsdato  |
        | 2015-03-25 |
        | 2015-03-25 |


