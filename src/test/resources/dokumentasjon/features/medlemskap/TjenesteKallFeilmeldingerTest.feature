# language: no
# encoding: UTF-8

Egenskap: Tjeneste kall feilmeldinger test

  Scenariomal: Ugyldige tjeneste kall får 400 status kode

    Når tjenestekall med følgende parametere behandles
      | Fødselsnummer | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15076500565   | 01.01.2019      | 31.12.2019      | Nei                           |

    Så Skal input <input> gi statuskoden 400

    Eksempler:
      | input                       |
      | "InputMedFomLikNull"        |
      | "InputMedTomDatoFørFomDato" |
      | "InputMedTomFørFomIJson"    |
      | "InputMedUgyldigFnr"        |
      | "UgyldigInput"              |
      | "InputMedFomFør2016"        |

  Scenariomal:
    Når tjenestekall med følgende parametere behandles
      | Fødselsnummer | Fra og med dato | Til og med dato | Har hatt arbeid utenfor Norge |
      | 15076500565   | 01.01.2019      | 31.12.2019      | Nei                           |

    Så Skal feilmeldingen med inputen <input> inneholde konsumenten <konsument>

    Eksempler:
      | input                       | konsument    |
      | "InputMedTomDatoFørFomDato" | "LOVME"      |
      | "InputMedUgyldigFnr"        | "LOVME"      |
      | "InputMedFomFør2016"        | "SYKEPENGER" |
