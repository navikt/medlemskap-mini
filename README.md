# medlemskap-mini.
Oppslagstjeneste for lovvalg og medlemskap i Folketrygden

Denne tjenesten gjør REST-kall mot følgende tjenester/registre:

* AAREG: Arbeidstaker og arbeidsgiver registeret
* EREG: Enhetsregisteret
* MEDL: Medlemskapsregisteret, inneholder medlemskapsperioder som avviker fra normalen
* GSAK: Saksbehandlingsløsning
* JOARK: Journalarkiv
* PDL: Ny persondataløsning, mangler foreløpig utenlandsopphold 
* UDI: Oppholds- og arbeidstillatelse fra Utenlendingsdirektoratet

# OpenAPI spesifikasjon for tjenesten
[OpenAPI spesifikasjon](src/main/resources/lovme.yaml)

# Funksjonell dokumentasjon
* [Overordnet funksjonell dokumentasjon](https://confluence.adeo.no/display/TLM/3.+Dokumentasjon+av+LovMe-tjenesten)
* [Funksjonell dokumentasjon av regler](https://confluence.adeo.no/pages/viewpage.action?pageId=441067001)

## URL til tjeneste
* preprod: https://medlemskap-mini.intern.dev.nav.no
* prod: https://medlemskap-mini.intern.nav.no

## Autentisering
Forventer et AzureAD-token utstedt til servicebruker, satt Authorization-header (Bearer)

Hvordan hente ut et AzureAD-token for servicebruker:
`curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'client_id=<client_id>&scope=api://<client_id>/.default&client_secret=<client_secret>&grant_type=client_credentials' 'https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/oauth2/v2.0/token'`

## Headere
I tillegg til Authorization-headeren kreves det at Content-Type er satt til application/json

## Eksempel på kall

Kallet er en POST på `/`
```
{
    "fnr": "123456789",
    "periode": {
        "fom": "2019-01-01", 
        "tom": "2019-12-31"
    },
    førsteDagForYtelse = "2019-01-01" 
    "brukerinput": {
        "arbeidUtenforNorge": false
    }
}
```

### Inn-parametre
* fnr: Fødselsnummer, identifiserer brukeren
* periode: 
    * For ytelsen sykepenger er dette perioden brukeren søker sykepenger for
    * For ytelsen sykepenger brukes dagen før start på perioden som første sykemeldingsdag
* førsteDagForYtelse: Første sykedag for sykepenger, eller første dagpengedag for dagpenger    
* brukerinput: Input fra bruker som må fylles ut i søknadsdialogen og er nødvendig for å avgjøre medlemskap
    * arbeidUtenforNorge: Har brukeren jobbet utenfor Norge siste 12 måneder?
* ytelse
    * Utledes fra request, ved å se på callerId
* overstyrteRegler
    * Map med RegelId, Svar, kan brukes til å overstyre svar på enkeltregler.

### Ut-parametere
        val tidspunkt: LocalDateTime,
        val versjonTjeneste: String,
        val versjonRegler: String,
        val datagrunnlag: Datagrunnlag,
        val resultat: Resultat
Resultat inneholder:

        val regelId: RegelId? = null,
        val avklaring: String = "",
        val begrunnelse: String = "",
        val svar: Svar,
        var harDekning: Svar? = null,
        var dekning: String = "",
        val delresultat: List<Resultat> = listOf(),
        val årsaker: List<Årsak>

Feltene harDekning og dekning er ikke i bruk inntil konsumenter kan håndtere dekning funksjonelt. 
Personer som er medlemmer i folketrygden kan ha et medlemskap som er begrenset.
I feltet «dekning» kommer det fram hvilken medlemskapstype personen har, dvs. om det omfatter hele folketrygden eller om det er begrenset til noen deler av folketrygden.
I feltet «harDekning» kommer det fram om medlemskapstypen omfatter konsumentens ytelse.
Svaralternativene i «harDekning» er Ja, Nei eller Uavklart. Ja betyr at medlemskapet omfatter konsumentens ytelse.

## Eksempel på kall med CURL, gitt at port-forwarding er satt opp på port 8080:
```
curl -X POST -H "Authorization: Bearer <AAD_TOKEN>" -H "Content-Type: application/json" -d '{ "fnr": "123456789", "periode": { "fom": "2019-01-01", "tom": "2019-12-31" }, "brukerinput": { "arbeidUtenforNorge": false } }' localhost:8080
```

## Eksempel på svar
* [norsk borger](src/test/resources/testpersoner/autogenerert/norsk_borger_response.json)
* [eøs-borger som er uavklart](src/test/resources/testpersoner/autogenerert/eøs_borger_uavklart_response.json)

## Hvordan skaffe token i preprod
```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'client_id=<clientid>>&scope=api://<clientid>/.default&client_secret=<clientsecret>&grant_type=client_credentials' 'https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/oauth2/v2.0/token'
```
Der `clientid` og `clientsecret` kan hentes fra kubernetes instansens miljøvariabler

## Kalle tjenesten fra laptop, for eksempel med Postman:
* Angi POST
* Angi URL https://medlemskap-mini.intern.dev.nav.no/
* Authorization:
  ** Type = Bearer Token
  * Paste inn Token-verdi   
    ** Body:
* Request som angitt i LOVME.yaml

# Autentisere klienter
Klienter som ønsker å kalle oss må generere et token med vår klientid som scope

