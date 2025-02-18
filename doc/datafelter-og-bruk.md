# Datafelter og bruk
Hensikten med dette dokumentet er å vise hvilke data-felter fra hvilke systemer som er i bruk og hvilke regler dette brukes i.

| System | Datafelt                                                                        | Merknader                                                                                     | Internt navn      | Brukes i regel |
|--------|---------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|-------------------|----------------|
| MEDL   | dekning, fraOgMed, tilOgMed,  erMedlem, lovvalg, lovvalgsland                   | Per i dag så sjekker vi kun om det finnes data i MEDL, så individuelle felter er ikke i bruk. | Medlemskapsunntak | OPP-1          |
| SAF    | dokumentId, tittel, journalpostId, journalposttype, journalstatus, tema, tittel | Per i dag så sjekker vi kun om det finnes data i SAF, så individuelle felter er ikke i bruk.  | Journalpost       | OPP-2          |
| OPPG   | aktivDato, prioritet, status, tema                                              | Per i dag så sjekker vi kun om det finnes data i OPPG, så individuelle felter er ikke i bruk. | Oppgave           | OPP-3          |
| PDL    | statsborgerskap                                                                 |                                                                                               | Statsborgerskap   | EØS-1          |
| AAREG  | arbeidsforhold                                                                  | Per i dag sjekker vi kun om det finnes arbeidsforhold, ikke noe spesifikt                     | Arbeidsforhold    | ARB-1          |
| AAREG  | arbeidsforhold.arbeidsavtale.yrkeskode                                          |                                                                                               | Yrkeskode         | ARB-3          |
| AAREG  | arbeidsforhold.arbeidsforholdType                                               |                                                                                               | Arbeidsforholdtype| ARB-4          |
| AAREG  | arbeidsforhold.arbeidsavtale.skipsregister                                      |                                                                                               | Skipsregister     | ARB-5          |
