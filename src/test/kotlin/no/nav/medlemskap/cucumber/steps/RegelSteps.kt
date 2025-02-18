package no.nav.medlemskap.cucumber.steps

import io.cucumber.datatable.DataTable
import io.cucumber.java8.No
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.ktor.server.plugins.*
import no.nav.medlemskap.common.LokalWebServer
import no.nav.medlemskap.cucumber.DomenespråkParser
import no.nav.medlemskap.cucumber.Medlemskapsparametre
import no.nav.medlemskap.cucumber.SpraakParserDomene.*
import no.nav.medlemskap.cucumber.steps.BakoverkompabilitetHjelper.medlemskapRequest
import no.nav.medlemskap.cucumber.steps.BakoverkompabilitetHjelper.validerDatagrunnlagMotKontrakt
import no.nav.medlemskap.cucumber.steps.BakoverkompabilitetHjelper.validerMedlemRequestMotKontrakt
import no.nav.medlemskap.cucumber.steps.pdl.DataOmEktefelleBuilder
import no.nav.medlemskap.cucumber.steps.pdl.PersonhistorikkBuilder
import no.nav.medlemskap.cucumber.steps.pdl.PersonhistorikkEktefelleBuilder
import no.nav.medlemskap.cucumber.steps.udi.EOSellerEFTAOppholdBuilder
import no.nav.medlemskap.cucumber.steps.udi.GjeldendeOppholdsstatusBuilder
import no.nav.medlemskap.cucumber.steps.udi.OppholdstillatelseBuilder
import no.nav.medlemskap.cucumber.steps.udi.OppholdstillatelsePåSammeVilkårBuilder
import no.nav.medlemskap.domene.*
import no.nav.medlemskap.domene.arbeidsforhold.*
import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.regler.assertBegrunnelse
import no.nav.medlemskap.regler.assertDelresultat
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.v1.RegelFactory
import no.nav.medlemskap.regler.v1.ReglerService
import org.junit.jupiter.api.Assertions.*
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.comparator.CustomComparator
import java.io.File

class RegelSteps : No {
    private val ANSATTE_9 = listOf(Ansatte(9, null))
    private val VANLIG_NORSK_ARBEIDSGIVER = Arbeidsgiver(
        navn = "null",
        organisasjonsnummer = "1",
        ansatte = ANSATTE_9,
        konkursStatus = null,
        juridiskeEnheter = null
    )

    private val pdlPersonhistorikkBuilder = PersonhistorikkBuilder()

    private var personhistorikkEktefelleBuilder = PersonhistorikkEktefelleBuilder()
    private var dataOmEktefelleBuilder = DataOmEktefelleBuilder()
    private var oppholdstillatelseBuilder = OppholdstillatelseBuilder()
    private var gjeldendeOppholdsstatusBuilder = GjeldendeOppholdsstatusBuilder()
    private var oppholdstillatelsePaSammeVilkarBuilder = OppholdstillatelsePåSammeVilkårBuilder()
    private var medlemskap: List<Medlemskap> = emptyList()
    private var eosEellerEFTAOppholdBuilder = EOSellerEFTAOppholdBuilder()

    private var dataOmBarn: List<DataOmBarn> = emptyList()

    private var arbeidsforhold: List<Arbeidsforhold> = emptyList()
    private var arbeidsavtaleMap = hashMapOf<Int, List<Arbeidsavtale>>()
    private var utenlandsoppholdMap = hashMapOf<Int, List<Utenlandsopphold>>()

    private val arbeidsgiverMap = mutableMapOf<Int, Arbeidsgiver>()
    private val ansatteMap = hashMapOf<String?, List<Ansatte>>()
    private val permisjonPermitteringMap = hashMapOf<Int, List<PermisjonPermittering>>()
    private var arbeidsavtaleEktefelleMap = hashMapOf<Int, List<Arbeidsavtale>>()

    private var resultat: Resultat? = null
    private var oppgaverFraGosys: List<Oppgave> = emptyList()
    private var journalPosterFraJoArk: List<Journalpost> = emptyList()
    private val domenespråkParser = DomenespråkParser

    private var medlemskapsparametre: Medlemskapsparametre? = null
    private var datagrunnlag: Datagrunnlag? = null

    var overstyrteRegler: Map<RegelId, Svar> = mapOf()

    init {
        Gitt("følgende statsborgerskap i personhistorikken") { dataTable: DataTable ->
            val statsborgerskap = PersonhistorikkDomeneSpraakParser.mapStatsborgerskap(dataTable)
            pdlPersonhistorikkBuilder.statsborgerskap.addAll(statsborgerskap)
        }

        Gitt("følgende navn i personhistorikken") { dataTable: DataTable ->
            val navn = PersonhistorikkDomeneSpraakParser.mapNavn(dataTable)
            pdlPersonhistorikkBuilder.navn.addAll(navn)
        }

        Gitt("følgende bostedsadresser i personhistorikken") { dataTable: DataTable ->
            val bostedsadresser = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            pdlPersonhistorikkBuilder.bostedsadresser.addAll(bostedsadresser)
        }

        Gitt("følgende opplysninger om dødsfall i personhistorikken:") { dataTable: DataTable ->
            val doedsfall = PersonhistorikkDomeneSpraakParser.mapDoedsfall(dataTable)
            pdlPersonhistorikkBuilder.doedsfall.addAll(doedsfall)
        }

        Gitt("følgende kontaktadresser i personhistorikken") { dataTable: DataTable ->
            val kontaktadresser = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            pdlPersonhistorikkBuilder.kontaktadresse.addAll(kontaktadresser)
        }

        Gitt("følgende oppholdsadresser i personhistorikken") { dataTable: DataTable ->
            val oppholdsadresse = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            pdlPersonhistorikkBuilder.oppholdsadresse.addAll(oppholdsadresse)
        }

        Gitt("følgende sivilstand i personhistorikk fra PDL") { dataTable: DataTable ->
            val sivilstand = PersonhistorikkDomeneSpraakParser.mapSivilstander(dataTable)
            pdlPersonhistorikkBuilder.sivilstand.addAll(sivilstand)
        }

        Gitt("følgende familerelasjoner i personhistorikk fra PDL") { dataTable: DataTable ->
            val familierelasjoner = PersonhistorikkDomeneSpraakParser.mapFamilierelasjoner(dataTable)
            pdlPersonhistorikkBuilder.familierelasjoner.addAll(familierelasjoner)
        }

        Gitt("følgende personhistorikk for ektefelle fra PDL") { dataTable: DataTable ->
            val ektefelle = PersonhistorikkDomeneSpraakParser.mapPersonhistorikkEktefelle(dataTable)
            dataOmEktefelleBuilder.personhistorikkEktefelle = ektefelle[0]
        }

        Gitt("følgende statsborgerskap for ektefelle fra PDL") { dataTable: DataTable ->
            val statsborgerskap = PersonhistorikkDomeneSpraakParser.mapStatsborgerskap(dataTable)
            personhistorikkEktefelleBuilder.statsborgerskap.addAll(statsborgerskap)
        }

        Gitt("følgende barn i personhistorikk for ektefelle fra PDL") { dataTable: DataTable ->
            val barnTilEktefelle = PersonhistorikkDomeneSpraakParser.mapBarnTilEktefelle(dataTable)
            personhistorikkEktefelleBuilder.barn.addAll(barnTilEktefelle)
        }

        Gitt("følgende personhistorikk for barn fra PDL") { dataTable: DataTable ->
            val barnTilBruker = PersonhistorikkDomeneSpraakParser.mapPersonhistorikkBarn(dataTable)
            dataOmBarn = barnTilBruker
        }

        Gitt("følgende medlemsunntak fra MEDL") { dataTable: DataTable ->
            medlemskap = domenespråkParser.mapMedlemskap(dataTable)
        }

        Gitt("følgende arbeidsforhold fra AAReg") { dataTable: DataTable ->
            arbeidsforhold = ArbeidsforholdDomeneSpraakParser.mapArbeidsforhold(dataTable)
        }

        Gitt("følgende arbeidsgiver i arbeidsforholdet") { dataTable: DataTable ->
            val arbeidsgivere = ArbeidsforholdDomeneSpraakParser.mapArbeidsgivere(dataTable)
            arbeidsgiverMap[0] = arbeidsgivere[0]
        }

        Gitt("følgende arbeidsgiver i arbeidsforhold {int}") { arbeidsforholdRad: Int, dataTable: DataTable ->
            val arbeidsgivere = ArbeidsforholdDomeneSpraakParser.mapArbeidsgivere(dataTable)
            val arbeidsforholdIndeks = arbeidsforholdIndeks(arbeidsforholdRad)
            arbeidsgiverMap[arbeidsforholdIndeks] = arbeidsgivere[0]
        }

        Gitt("følgende detaljer om ansatte for arbeidsgiver med org.nr {string}") { orgnr: String, dataTable: DataTable ->
            val ansatte = ArbeidsforholdDomeneSpraakParser.mapAnsatte(dataTable)
            if (ansatte.isNotEmpty()) {
                ansatteMap.put(orgnr, ansatte)
            }
        }

        Gitt("følgende detaljer om ansatte for arbeidsgiver") { dataTable: DataTable ->
            val ansatte = ArbeidsforholdDomeneSpraakParser.mapAnsatte(dataTable)
            if (ansatte.isNotEmpty()) {
                ansatteMap.put(null, ansatte)
            }
        }

        Gitt("følgende permisjonspermitteringer i arbeidsforholdet") { dataTable: DataTable ->
            permisjonPermitteringMap[0] = ArbeidsforholdDomeneSpraakParser.mapPermisjonspermitteringer(dataTable)
        }

        Gitt("følgende arbeidsavtaler i arbeidsforholdet") { dataTable: DataTable ->
            arbeidsavtaleMap[0] = ArbeidsforholdDomeneSpraakParser.mapArbeidsavtaler(dataTable)
        }

        Gitt("følgende arbeidsavtaler i arbeidsforhold {int}") { arbeidsforholdRad: Int, dataTable: DataTable ->
            val arbeidsavtaler = ArbeidsforholdDomeneSpraakParser.mapArbeidsavtaler(dataTable)
            val arbeidsforholdIndeks = arbeidsforholdIndeks(arbeidsforholdRad)
            arbeidsavtaleMap[arbeidsforholdIndeks] = arbeidsavtaler
        }

        Gitt("følgende utenlandsopphold i arbeidsforholdet") { dataTable: DataTable ->
            utenlandsoppholdMap[0] = ArbeidsforholdDomeneSpraakParser.mapUtenlandsopphold(dataTable)
        }

        Gitt("følgende utenlandsopphold i arbeidsforhold {int}") { arbeidsforholdRad: Int, dataTable: DataTable ->
            val arbeidsforholdIndeks = arbeidsforholdIndeks(arbeidsforholdRad)
            utenlandsoppholdMap[arbeidsforholdIndeks] = ArbeidsforholdDomeneSpraakParser.mapUtenlandsopphold(dataTable)
        }

        Gitt("følgende oppgaver fra Gosys") { dataTable: DataTable ->
            oppgaverFraGosys = OppgaverDomeneSpraakParser.mapOppgaverFraGosys(dataTable)
        }

        Gitt("følgende journalposter fra Joark") { dataTable: DataTable ->
            journalPosterFraJoArk = DokumentDomeneSpraakParser.mapJournalposter(dataTable)
        }

        Gitt("følgende arbeidsforhold til ektefelle fra AAReg") { dataTable: DataTable ->
            val arbeidsforholdEktefelle = ArbeidsforholdDomeneSpraakParser.mapArbeidsforhold(dataTable)
            dataOmEktefelleBuilder.arbeidsforholdEktefelle = arbeidsforholdEktefelle
        }

        Gitt("følgende arbeidsavtaler til ektefelle i arbeidsforholdet") { dataTable: DataTable ->
            arbeidsavtaleEktefelleMap[0] = ArbeidsforholdDomeneSpraakParser.mapArbeidsavtaler(dataTable)
            dataOmEktefelleBuilder.arbeidsforholdEktefelle.get(0).arbeidsavtaler = arbeidsavtaleEktefelleMap[0]!!
        }

        Gitt("med følgende regeloverstyringer") { dataTable: DataTable ->
            overstyrteRegler = domenespråkParser.mapOverstyrteRegler(dataTable)
        }

        Gitt("følgende oppholdstillatelse") { dataTable: DataTable ->
            oppholdstillatelseBuilder.fromOppholdstillatelse(
                OppholdstillatelseDomeneSpraakParser.mapOppholdstillatelse(
                    dataTable
                )
            )
        }

        Gitt("følgende oppholdstillatelse med oppholdstillatelse på samme vilkår") { dataTable: DataTable ->
            oppholdstillatelsePaSammeVilkarBuilder.harTillatelse =
                OppholdstillatelseDomeneSpraakParser.mapHarTillatelse(dataTable)
            oppholdstillatelsePaSammeVilkarBuilder.periode = OppholdstillatelseDomeneSpraakParser.mapPeriode(dataTable)
            oppholdstillatelsePaSammeVilkarBuilder.soknadIkkeAvgjort =
                OppholdstillatelseDomeneSpraakParser.mapSoknadIkkeAvgjort(dataTable)
            oppholdstillatelsePaSammeVilkarBuilder.type =
                OppholdstillatelseDomeneSpraakParser.mapOppholdstillatelsePaSammeVilkarType(dataTable)
            gjeldendeOppholdsstatusBuilder.oppholdstillatelsePaSammeVilkar =
                oppholdstillatelsePaSammeVilkarBuilder.build()
        }

        Gitt("følgende i EØSellerEFTAOpphold") { dataTable: DataTable ->
            eosEellerEFTAOppholdBuilder.periode = OppholdstillatelseDomeneSpraakParser.mapPeriode(dataTable)
            eosEellerEFTAOppholdBuilder.EOSellerEFTAGrunnlagskategoriOppholdsrettType =
                OppholdstillatelseDomeneSpraakParser.mapOppholdsrettType(dataTable)
            eosEellerEFTAOppholdBuilder.EOSellerEFTAOppholdType =
                OppholdstillatelseDomeneSpraakParser.mapOppholdType(dataTable)
            gjeldendeOppholdsstatusBuilder.eosellerEFTAOpphold = eosEellerEFTAOppholdBuilder.build()
            oppholdstillatelseBuilder.gjeldendeOppholdsstatus = gjeldendeOppholdsstatusBuilder.build()
            oppholdstillatelseBuilder.build()
        }

        Gitt("følgende arbeidsadgang") { dataTable: DataTable ->
            oppholdstillatelseBuilder.arbeidsadgang = OppholdstillatelseDomeneSpraakParser.mapArbeidstilgang(dataTable)
        }

        Når("medlemskap beregnes med følgende parametre") { dataTable: DataTable ->
            medlemskapsparametre = domenespråkParser.mapMedlemskapsparametre(dataTable)

            datagrunnlag = byggDatagrunnlag(medlemskapsparametre)
            resultat = ReglerService.kjørRegler(hentDatagrunnlag())
        }

        Når("regel {string} kjøres med følgende parametre, skal valideringsfeil være {string}") { regelId: String, forventetValideringsfeil: String, dataTable: DataTable ->
            medlemskapsparametre = domenespråkParser.mapMedlemskapsparametre(dataTable)
            datagrunnlag = byggDatagrunnlag(medlemskapsparametre)

            val regelFactory = RegelFactory(hentDatagrunnlag())
            val regel = regelFactory.create(regelId)

            val exception = shouldThrow<BadRequestException> {
                regel.utfør()
            }

            exception.message shouldBe forventetValideringsfeil
        }

        Når("regel {string} kjøres med følgende parametre, skal svaret være {string}") { regelId: String, forventetSvar: String, dataTable: DataTable ->
            medlemskapsparametre = domenespråkParser.mapMedlemskapsparametre(dataTable)
            datagrunnlag = byggDatagrunnlag(medlemskapsparametre)

            val regelFactory = RegelFactory(hentDatagrunnlag())
            val regel = regelFactory.create(regelId)
            val faktiskSvar = regel.utfør()

            assertEquals(forventetSvar, faktiskSvar.svar.name)
        }

        Når("regel {string} kjøres med følgende parametre") { regelId: String, dataTable: DataTable ->
            medlemskapsparametre = domenespråkParser.mapMedlemskapsparametre(dataTable)
            datagrunnlag = byggDatagrunnlag(medlemskapsparametre)

            val regelFactory = RegelFactory(hentDatagrunnlag())
            val regel = regelFactory.create(regelId)

            resultat = regel.utfør()
        }

        Når("tjenestekall med følgende parametere behandles") { dataTable: DataTable ->
            medlemskapsparametre = domenespråkParser.mapMedlemskapsparametre(dataTable)
            datagrunnlag = byggDatagrunnlag(medlemskapsparametre)

            LokalWebServer.testDatagrunnlag = datagrunnlag
            LokalWebServer.startServer()
        }

        Når("tjenestekall for regler med følgende parametere behandles") { dataTable: DataTable ->
            medlemskapsparametre = domenespråkParser.mapMedlemskapsparametre(dataTable)
            datagrunnlag = byggDatagrunnlag(medlemskapsparametre)

            LokalWebServer.startServer()
        }

        Så("skal forventet json respons være {string}") { filnavn: String ->
            val forventetJsonResponse = RegelSteps::class.java
                .getResource("/testpersoner/bakoverkompatibeltest/$filnavn.json").readText()

            val jsonResponse = medlemskapRequest(medlemskapsparametre)

            resultat = Response.fraJson(jsonResponse).resultat

            JSONAssert.assertEquals(
                forventetJsonResponse,
                jsonResponse,
                CustomComparator(
                    JSONCompareMode.STRICT,
                    Customization("tidspunkt") { _, _ -> true }
                )
            )
        }

        Så("Skal kontrakt være OK") {
            validerMedlemRequestMotKontrakt(medlemskapsparametre!!)
        }

        Så("Skal kontrakt for Regler fra datagrunnlag være OK") {
            validerDatagrunnlagMotKontrakt(datagrunnlag)
        }

        Så("Skal input {string} gi statuskoden {int}") { filnavn: String, statusKode: Int ->
            val input = RegelSteps::class.java.getResource("/testpersoner/testinput/$filnavn.json").readText()
            LokalWebServer.testResponsKode(input, statusKode)
        }

        Så("Skal feilmeldingen med inputen {string} inneholde konsumenten {string}") { filnavn: String, konsument: String ->
            val input = RegelSteps::class.java.getResource("/testpersoner/testinput/$filnavn.json").readText()
            val respons = LokalWebServer.responsUtenStatuskodeSjekk(input)

            assertTrue(respons.contains("for konsument $konsument"))
        }

        Så("skal svaret være {string}") { forventetVerdi: String ->
            val forventetSvar = domenespråkParser.parseSvar(forventetVerdi)

            assertEquals(forventetSvar, hentResultat().svar)
        }

        Så("skal avklaringen være som definert i RegelId") {
            assertEquals(hentResultat().regelId.avklaring, hentResultat().avklaring)
        }

        Så("skal begrunnelsen være som definert i RegelId") {
            if (hentResultat().svar == Svar.JA) {
                assertEquals(hentResultat().regelId.jaBegrunnelse, hentResultat().begrunnelse)
            } else {
                assertEquals(hentResultat().regelId.neiBegrunnelse, hentResultat().begrunnelse)
            }
        }

        Så("skal regel-årsaker være {string}") { forventedeÅrsaker: String ->
            assertEquals(
                forventedeÅrsaker.trim(),
                hentResultat().årsaker.map { it.regelId.identifikator }.toString().filter { it != '[' && it != ']' }
                    .trim()
            )
        }

        Så("skal svaret være Ja på medlemskap og {string} på harDekning") { forventetVerdi: String ->
            val forventetSvar = domenespråkParser.parseSvar(forventetVerdi)
            assertEquals(Svar.JA, hentResultat().svar)
            assertEquals(forventetSvar, hentResultat().harDekning)
        }

        Så("skal svaret være {string} på medlemskap og {string} på harDekning") { forventetMedlemskap: String, forventetVerdi: String ->
            val forventetSvarMedlemskap = domenespråkParser.parseSvar(forventetMedlemskap)
            assertEquals(forventetSvarMedlemskap, hentResultat().svar)
            val forventetSvar = domenespråkParser.parseSvar(forventetVerdi)
            assertEquals(forventetSvar, hentResultat().harDekning)
        }

        Så("skal regel {string} gi svaret {string}") { regelIdStr: String?, forventetSvar: String? ->
            val regelId = domenespråkParser.parseRegelId(regelIdStr!!)

            assertDelresultat(regelId, domenespråkParser.parseSvar(forventetSvar!!), hentResultat())
        }

        Så("skal regel {string} gi begrunnelse {string}") {
            fun regelGiBegrunnelse(regelIdStr:String, forventetBegrunnelse:String) {
                val regelId = domenespråkParser.parseRegelId(regelIdStr!!)

                assertBegrunnelse(regelId, forventetBegrunnelse, hentResultat())
            }

        }

        Så("skal regel {string} ikke finnes i resultatet") { regelIdStr: String ->
            val regelId = domenespråkParser.parseRegelId(regelIdStr)
            val regelResultat = hentResultat().finnRegelResultat(regelId)

            assertNull(regelResultat)
        }

        Så("skal regel {string} inneholde følgende delresultater:") { regelIdStr: String, dataTable: DataTable ->
            val regelIdListe = domenespråkParser.mapRegelId(dataTable)
            val regelId = domenespråkParser.parseRegelId(regelIdStr)

            val regelResultat = hentResultat().finnRegelResultat(regelId)
                ?: throw java.lang.RuntimeException("Fant ikke regelresultat for regel {regelIdStr}")

            assertTrue(regelResultat.delresultat.map { it.regelId }.containsAll(regelIdListe))
        }

        Så("skal resultat gi følgende delresultater:") { dataTable: DataTable ->
            val forventedeRegler = domenespråkParser.mapRegelId(dataTable)
            val faktiskeRegler = hentResultat().delresultat.map { it.regelId }

            faktiskeRegler.shouldContainExactly(forventedeRegler)
        }

        Så("skal JSON datagrunnlag og resultat genereres i filen {string}") { filnavn: String ->
            val datagrunnlagJson: String = hentDatagrunnlag().tilJson()
            val resultatJson = hentResultat().tilJson()
            val responseJson =
                datagrunnlagJson.substring(0, datagrunnlagJson.length - 2) + ",\n \"resultat\" : " + resultatJson + "}"

            lagreJson(filnavn, responseJson)
        }

        Så("skal JSON resultat genereres i filen {string}") { filnavn: String ->
            lagreJsonResultat(filnavn)
        }

        Så("skal JSON datagrunnlag genereres i filen {string}") { filnavn: String ->
            lagreJsonDatagrunnlag(filnavn)
        }

        Så("skal statsborgerskapskategorien være {string}") { statsborgerskapskategori: String ->
            assertEquals(statsborgerskapskategori, resultat?.bestemStatsborgerskapskategori()?.name)
        }
    }

    private fun lagreJsonResultat(filnavn: String) {
        lagreJson(filnavn, hentResultat().tilJson())
    }

    private fun lagreJsonDatagrunnlag(filnavn: String) {
        lagreJson(filnavn, hentDatagrunnlag().tilJson())
    }

    private fun lagreJson(filnavn: String, json: String) {
        val katalog = File("build", "cucumberjson")
        katalog.mkdir()

        val myfile = File(katalog, "$filnavn.json")
        myfile.writeText(json)
    }

    private fun arbeidsforholdIndeks(radnummer: Int?): Int {
        return (radnummer ?: 1) - 1
    }

    private fun byggDatagrunnlag(medlemskapsparametre: Medlemskapsparametre? = null): Datagrunnlag {
        if (medlemskapsparametre == null) {
            throw RuntimeException("medlemskapsparametre må være satt for å bygge Datagrunnlag")
        }

        return Datagrunnlag(
            fnr = medlemskapsparametre.fnr ?: "",
            periode = medlemskapsparametre.inputPeriode,
            førsteDagForYtelse = medlemskapsparametre.førsteDagForYtelse,
            brukerinput = medlemskapsparametre.brukerinput,
            pdlpersonhistorikk = pdlPersonhistorikkBuilder.build(),
            medlemskap = medlemskap,
            arbeidsforhold = byggArbeidsforhold(
                arbeidsforhold,
                arbeidsgiverMap,
                arbeidsavtaleMap,
                utenlandsoppholdMap,
                ansatteMap,
                permisjonPermitteringMap
            ),
            oppgaver = oppgaverFraGosys,
            dokument = journalPosterFraJoArk,
            ytelse = medlemskapsparametre.ytelse ?: Ytelse.SYKEPENGER,
            dataOmBarn = dataOmBarn,
            dataOmEktefelle = dataOmEktefelleBuilder.build(),
            overstyrteRegler = overstyrteRegler,
            oppholdstillatelse = oppholdstillatelseBuilder.build()
        )
    }

    private fun byggArbeidsforhold(
        arbeidsforholdListe: List<Arbeidsforhold>,
        arbeidsgiverMap: Map<Int, Arbeidsgiver>,
        arbeidsavtaleMap: Map<Int, List<Arbeidsavtale>>,
        utenlandsoppholdMap: Map<Int, List<Utenlandsopphold>>,
        ansatteMap: Map<String?, List<Ansatte>>,
        permisjonPermitteringMap: Map<Int, List<PermisjonPermittering>>
    ): List<Arbeidsforhold> {
        return arbeidsforholdListe
            .mapIndexed { index, arbeidsforhold ->
                arbeidsforhold.copy(
                    utenlandsopphold = utenlandsoppholdMap[index] ?: emptyList(),
                    arbeidsgiver = byggArbeidsgiver(arbeidsgiverMap[index], ansatteMap) ?: VANLIG_NORSK_ARBEIDSGIVER,
                    arbeidsavtaler = arbeidsavtaleMap[index] ?: emptyList(),
                    permisjonPermittering = permisjonPermitteringMap[index] ?: emptyList()
                )
            }
    }

    private fun byggArbeidsgiver(arbeidsgiver: Arbeidsgiver?, ansatteMap: Map<String?, List<Ansatte>>): Arbeidsgiver? {
        if (arbeidsgiver == null) {
            return null
        }

        val ansatte = ansatteMap[arbeidsgiver.organisasjonsnummer] ?: ansatteMap[null]
        if (ansatte != null) {
            return arbeidsgiver.copy(ansatte = ansatte)
        }

        return arbeidsgiver
    }

    private fun hentResultat(): Resultat {
        return resultat ?: throw RuntimeException("Resultat er null")
    }

    private fun hentDatagrunnlag(): Datagrunnlag {
        return datagrunnlag ?: throw RuntimeException("Datagrunnlag er null")
    }
}

private object BakoverkompabilitetHjelper {

    fun medlemskapRequest(medlemskapsparametre: Medlemskapsparametre?): String {
        if (medlemskapsparametre == null) {
            throw RuntimeException("Medlemskapsparametre er null")
        }

        val responseStr = LokalWebServer.medlemskapRequest(medlemskapsparametre)

        return responseStr
    }

    fun validerMedlemRequestMotKontrakt(medlemskapsparametre: Medlemskapsparametre?) {
        if (medlemskapsparametre == null) {
            throw RuntimeException("Medlemskapsparametre er null")
        }

        LokalWebServer.validerKontraktMedlemskap(medlemskapsparametre)
    }

    fun validerDatagrunnlagMotKontrakt(datagrunnlag: Datagrunnlag?) {
        if (datagrunnlag == null) {
            throw RuntimeException("Datagrunnlag er null")
        }

        LokalWebServer.validerKontraktRegler(datagrunnlag)
    }
}
