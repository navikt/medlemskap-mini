# language: no
# encoding: UTF-8

Egenskap: Regel 17: Har bruker arbeidsforhold?

  Scenario: Bruker uten arbeidsforhold får "Nei"

    Når regel "17" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Nei"

  Scenario: Bruker med arbeidsforhold i kontrollperioden får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      |                 | Organisasjon     | NORMALT             |

    Når regel "17" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"

  Scenario: Bruker med arbeidsforhold som starter i kontrollperiode får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 30.03.2019      |                 | Organisasjon     | NORMALT             |

    Når regel "17" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"

  Scenario: Bruker med arbeidsforhold som starter før og slutter i kontrollperiode får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.01.2018      | 30.10.2019      | Organisasjon     | NORMALT             |

    Når regel "17" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"


  Scenario: Bruker med arbeidsforhold som starter og slutter i kontrollperiode får "Ja"

    Gitt følgende arbeidsforhold fra AAReg
      | Fra og med dato | Til og med dato | Arbeidsgivertype | Arbeidsforholdstype |
      | 01.02.2019      | 30.10.2019      | Organisasjon     | NORMALT             |

    Når regel "17" kjøres med følgende parametre
      | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 30.01.2020      | 30.01.2021      | Nei                           |

    Så skal svaret være "Ja"