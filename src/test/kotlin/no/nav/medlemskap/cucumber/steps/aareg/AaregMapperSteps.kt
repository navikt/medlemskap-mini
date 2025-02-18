package no.nav.medlemskap.cucumber.steps.aareg

import AaregBuilder
import io.cucumber.datatable.DataTable
import io.cucumber.java8.No
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import no.nav.medlemskap.cucumber.SpraakParserDomene.ArbeidsforholdDomeneSpraakParser
import no.nav.medlemskap.cucumber.mapping.pdl.aareg.AaregDomenespraakParser
import no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder.*
import no.nav.medlemskap.cucumber.steps.aareg.eregBuilder.EnhetstypeBuilder
import no.nav.medlemskap.cucumber.steps.aareg.eregBuilder.OrganisasjonBuilder
import no.nav.medlemskap.cucumber.steps.aareg.eregBuilder.OrganisasjonsDetaljerBuilder
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.services.aareg.mapArbeidsforhold

class AaregMapperSteps : No {
    private val aaregDomenespraakParser = AaregDomenespraakParser()
    private var arbeidsforhold = listOf<Arbeidsforhold>()
    private var periodeBuilder = PeriodeBuilder()
    private var aaregArbeidsgiverBuilder = AaregOpplysningspliktigArbeidsgiverBuilder()
    private var aaregArbeidsforholdBuilder = AaRegArbeidsforholdBuilder()
    private var organisasjonsDetaljerBuilder = OrganisasjonsDetaljerBuilder()
    private var enhetstypeBuilder = EnhetstypeBuilder()
    private var organisasjonBuilder = OrganisasjonBuilder()
    private var ansettelsesPeriodeBuilder = AaRegAnsettelsesPeriodeBuilder()
    private var arbeidsavtaleBuilder = AaRegArbeidsavtaleBuilder()
    private var utenlandoppholdBuiler = AaRegUtenlandsoppholdBuilder()
    private var permisjonsPermitteringerBuilder = AaRegPermisjonsPermitteringerBuilder()

    init {
        Gitt("følgende om AaRegPeriode i fra AaRegAnsettelsesperiode fra AaRegArbeidsforhold") { dataTable: DataTable ->
            periodeBuilder.fom = aaregDomenespraakParser.mapPeriode(dataTable).fom
            periodeBuilder.tom = aaregDomenespraakParser.mapPeriode(dataTable).tom
        }

        Gitt("følgende om arbeidgivertype fra AaRegOpplysningspliktigArbeidsgiver fra AaRegArbeidsforhold") { dataTable: DataTable ->
            aaregArbeidsgiverBuilder.type = aaregDomenespraakParser.mapArbeidsgiverType(dataTable)
        }

        Gitt("følgende om type fra AaRegArbeidsforhold") { dataTable: DataTable ->
            aaregArbeidsforholdBuilder.type = aaregDomenespraakParser.mapArbeidsforholdsType(dataTable)
        }

        Gitt("følgende om enhetstype fra enhetstyper fra organisasjonsdetaljer fra ereg") { dataTable: DataTable ->
            enhetstypeBuilder.enhetstype = aaregDomenespraakParser.mapEnhetstype(dataTable)
        }

        Gitt("følgende om organisasjonsnummer fra Organiasjon fra ereg") { dataTable: DataTable ->
            organisasjonBuilder.organisjonsnummer = aaregDomenespraakParser.mapOrganisasjonsnummer(dataTable)
        }

        Gitt("følgende om Ansatte fra  Organisasjonsdetaljer fra Organiasjon fra ereg") { dataTable: DataTable ->
            organisasjonsDetaljerBuilder.ansatte = aaregDomenespraakParser.mapAntallAnsatte(dataTable).toMutableList()
        }

        Gitt("følgende om konkursstatus fra statuser fra organisasjonDetaljer fra organisasjon") { dataTable: DataTable ->
            organisasjonsDetaljerBuilder.statuser = aaregDomenespraakParser.mapStatuser(dataTable).toMutableList()
        }

        Gitt("følgende om AaRegPeriode fra AaRegArbeidsavtale fra AaRegBruksperiode") { dataTable: DataTable ->
            arbeidsavtaleBuilder.bruksperiode = aaregDomenespraakParser.mapBruksPeriode(dataTable)
        }

        Gitt("følgende om gyldighetsperiode fra AaRegArbeidsavtale fra AaRegGyldighetsperiode") { dataTable: DataTable ->
            arbeidsavtaleBuilder.gyldighetsperiode = aaregDomenespraakParser.mapGyldighetsPeriode(dataTable)
        }

        Gitt("følgende om yrke fra AaRegArbeidsavtale") { dataTable: DataTable ->
            arbeidsavtaleBuilder.yrke = aaregDomenespraakParser.mapYrkeskode(dataTable)
        }

        Gitt("følgende om skipsregister fra AaRegArbeidsavtale") { dataTable: DataTable ->
            arbeidsavtaleBuilder.skipsregister = aaregDomenespraakParser.mapSkipsregister(dataTable)
        }

        Gitt("følgende om skipstype fra AaRegArbeidsavtale") { dataTable: DataTable ->
            arbeidsavtaleBuilder.skipstype = aaregDomenespraakParser.mapSkipstype(dataTable)
        }

        Gitt("følgende om fartsområde fra AaRegArbeidsavtale") { dataTable: DataTable ->
            arbeidsavtaleBuilder.fartsomraade = aaregDomenespraakParser.mapFartsomraade(dataTable)
        }

        Gitt("følgende om stillingsprosent fra AaRegArbeidsavtale") { dataTable: DataTable ->
            arbeidsavtaleBuilder.stillingsprosent = aaregDomenespraakParser.mapStillingsprosent(dataTable)
        }

        Gitt("følgende om beregnetAntallTimerPrUke") { dataTable: DataTable ->
            arbeidsavtaleBuilder.beregnetAntallTimerPrUke = aaregDomenespraakParser.mapBeregnetAntallTimerPerUke(dataTable)
        }

        Gitt("følgende om landkode fra AaRegUtenlandsopphold fra AaRegArbeidsforhold") { dataTable: DataTable ->
            utenlandoppholdBuiler.landkode = aaregDomenespraakParser.mapLandkode(dataTable)
        }

        Gitt("følgende om AaRegPeriode i fra AaRegUtenlandsopphold fra AaRegArbeidsforhold") { dataTable: DataTable ->
            utenlandoppholdBuiler.periode = aaregDomenespraakParser.mapPeriode(dataTable)
        }

        Gitt("følgende rapporteringsperiode fra AaRegUtenlandsopphold fra AaRegArbeidsforhold") { dataTable: DataTable ->
            utenlandoppholdBuiler.rapporteringsperiode = aaregDomenespraakParser.mapRapporteringsperiode(dataTable)
        }

        Gitt("følgende om AaRegPeriode fra AaRegPermisjonPermittering fra AaRegArbeidsforhold") { dataTable: DataTable ->
            permisjonsPermitteringerBuilder.periode = aaregDomenespraakParser.mapPeriode(dataTable)
        }

        Gitt("følgende om permisjonPermitteringId i fra AaRegPermisjonPermittering fra AaRegArbeidsforhold") { dataTable: DataTable ->
            permisjonsPermitteringerBuilder.permisjonsPermitteringId = aaregDomenespraakParser.mapPermitteringsId(dataTable)
        }

        Gitt("følgende prosent fra fra AaRegPermisjonPermittering fra AaRegArbeidsforhold") { dataTable: DataTable ->
            permisjonsPermitteringerBuilder.prosent = aaregDomenespraakParser.mapProsent(dataTable)
        }

        Gitt("følgende type fra fra AaRegPermisjonPermittering fra AaRegArbeidsforhold") { dataTable: DataTable ->
            permisjonsPermitteringerBuilder.type = aaregDomenespraakParser.mapType(dataTable)
        }

        Gitt("følgende varslingskode fra fra AaRegPermisjonPermittering fra AaRegArbeidsforhold") { dataTable: DataTable ->
            permisjonsPermitteringerBuilder.varslingkode = aaregDomenespraakParser.mapVarslingskode(dataTable)
        }

        Når("arbeidsforholdene mappes") {
            arbeidsforhold = mapTilArbeidsforhold()
        }

        Så("skal mappet landkode i utenlandsoppholdet være") { dataTable: DataTable ->
            val landkodeForventet = ArbeidsforholdDomeneSpraakParser.mapLandkode(dataTable)
            arbeidsforhold[0].utenlandsopphold?.get(0)?.landkode.shouldBe(landkodeForventet)
        }

        Så("mappet periode være i utenlandsoppholdet være") { dataTable: DataTable ->
            val periodeForventet = ArbeidsforholdDomeneSpraakParser.mapPeriodeIUtenlandsopphold(dataTable)
            arbeidsforhold[0].utenlandsopphold?.get(0)?.periode.shouldBe(periodeForventet)
        }

        Så("skal mappet periode i arbeidsforholdet være") { dataTable: DataTable ->
            val periodeForventet = ArbeidsforholdDomeneSpraakParser.mapPeriodeForPermittering(dataTable)
            arbeidsforhold[0].permisjonPermittering?.get(0)?.periode.shouldBe(periodeForventet)
        }

        Så("mappet permisjonPermitteringId skal være") { dataTable: DataTable ->
            val permisjonPermitteringIdForventet = ArbeidsforholdDomeneSpraakParser.mapPermitteringId(dataTable)
            arbeidsforhold[0].permisjonPermittering?.get(0)?.permisjonPermitteringId.shouldBe(permisjonPermitteringIdForventet)
        }

        Så("mappet rapporteringsperiode i utenlandsoppholdet være") { dataTable: DataTable ->
            val rapporteringsPeriodeForventet = ArbeidsforholdDomeneSpraakParser.mapRapporteringsperiode(dataTable)
            arbeidsforhold[0].utenlandsopphold?.get(0)?.rapporteringsperiode.shouldBe(rapporteringsPeriodeForventet)
        }

        Så("skal mappet periode i arbeidsavtale være") { dataTable: DataTable ->
            val periodeForventet = ArbeidsforholdDomeneSpraakParser.mapPeriodeIArbeidsavtale(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].periode.shouldBe(periodeForventet)
        }

        Så("mappet gyldighetsperiode i arbeidsavtale være") { dataTable: DataTable ->
            val periodeForventet = ArbeidsforholdDomeneSpraakParser.mapPeriodeIArbeidsavtale(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].periode.shouldBe(periodeForventet)
        }

        Så("mappet organisasjonsnummer til arbeidsgiver i arbeidsforholdet være") { dataTable: DataTable ->
            val organisasjonsnummerForventet = ArbeidsforholdDomeneSpraakParser.mapOrganisasjonsnummer(dataTable)
            arbeidsforhold[0].arbeidsgiver.organisasjonsnummer.shouldBe(organisasjonsnummerForventet)
        }

        Så("mappet ansatte til arbeidsgiver i arbeidsforholdet være") { dataTable: DataTable ->
            val ansatteForventet = ArbeidsforholdDomeneSpraakParser.mapAnsatte(dataTable)
            arbeidsforhold[0].arbeidsgiver.ansatte.shouldContainExactly(ansatteForventet)
        }

        Så("mappet konkursstatus til arbeidsgiver i arbeidsforholdet være") { dataTable: DataTable ->
            val statusForventet = ArbeidsforholdDomeneSpraakParser.mapKonkurStatuser(dataTable)
            arbeidsforhold[0].arbeidsgiver.konkursStatus.shouldContainExactly(statusForventet)
        }

        Så("mappet skipsregister være") { dataTable: DataTable ->
            val skipsregisterForventet = ArbeidsforholdDomeneSpraakParser.mapSkipsregister(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].skipsregister.shouldBe(skipsregisterForventet)
        }

        Så("mappet skipstype være") { dataTable: DataTable ->
            val forventetSkipstype = ArbeidsforholdDomeneSpraakParser.mapSkipstype(dataTable)
        }

        Så("mappet fartsområde skal være") { dataTable: DataTable ->
            val fartsomraadeForventet = ArbeidsforholdDomeneSpraakParser.mapFartsomraade(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].fartsomraade.shouldBe(fartsomraadeForventet)
        }

        Så("mappet prosent skal være") { dataTable: DataTable ->
            val prosentForventet = ArbeidsforholdDomeneSpraakParser.mapProsent(dataTable)
            arbeidsforhold[0].permisjonPermittering?.get(0)?.prosent.shouldBe(prosentForventet)
        }

        Så("mappet type skal være") { dataTable: DataTable ->
            val permisjonstypeForventet = ArbeidsforholdDomeneSpraakParser.mapType(dataTable)
            arbeidsforhold[0].permisjonPermittering?.get(0)?.type.shouldBe(permisjonstypeForventet)
        }

        Så("mappet varslingkode skal være") { dataTable: DataTable ->
            val varslingsKodeForventet = ArbeidsforholdDomeneSpraakParser.mapVarslingskode(dataTable)
            arbeidsforhold[0].permisjonPermittering?.get(0)?.varslingskode.shouldBe(varslingsKodeForventet)
        }

        Så("skal mappede periode i arbeidsforhold være") { dataTable: DataTable ->
            val periodeForventet = ArbeidsforholdDomeneSpraakParser.mapPeriodeIArbeidsforhold(dataTable)
            arbeidsforhold.get(0).periode.shouldBe(periodeForventet)
        }

        Så("mappet stillingsprosent være") { dataTable: DataTable ->
            val stillinprosentForventet = ArbeidsforholdDomeneSpraakParser.mapStillingsprosent(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].stillingsprosent.shouldBe(stillinprosentForventet)
        }

        Så("skal mappet arbeidsgivertype i arbeidsforholdet være") { dataTable: DataTable ->
            val arbeidsgiverTypeForventet = ArbeidsforholdDomeneSpraakParser.mapArbeidsgivertype(dataTable)
            arbeidsforhold.get(0).arbeidsgivertype.shouldBe(arbeidsgiverTypeForventet)
        }

        Så("skal mappet arbeidsforholdstype i arbeidsforholdet være") { dataTable: DataTable ->
            val arbeidforholdstypeForventet = ArbeidsforholdDomeneSpraakParser.mapArbeidsforholdstype(dataTable)
            arbeidsforhold[0].arbeidsforholdstype.shouldBe(arbeidforholdstypeForventet)
        }

        Så("mappet beregnetAntallTimerPrUke være") { dataTable: DataTable ->
            val beregnetAntallTimerPerUkeForventet = ArbeidsforholdDomeneSpraakParser.mapBeregnetAntallTimerUke(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].beregnetAntallTimerPrUke.shouldBe(beregnetAntallTimerPerUkeForventet)
        }

        Så("mappet yrkeskode være") { dataTable: DataTable ->
            val yrkeskodeForventet = ArbeidsforholdDomeneSpraakParser.mapYrkeskode(dataTable)
            arbeidsforhold[0].arbeidsavtaler[0].yrkeskode.shouldBe(yrkeskodeForventet)
        }
    }

    private fun mapTilArbeidsforhold(): List<Arbeidsforhold> {
        return mapArbeidsforhold(byggAaregBuilder().build())
    }

    private fun byggAaregBuilder(): AaregBuilder {
        val aaregBuilder = AaregBuilder()
        organisasjonsDetaljerBuilder.enhetstyper = mutableListOf(enhetstypeBuilder.build())
        organisasjonBuilder.originasjonsDetaljer = organisasjonsDetaljerBuilder.build()
        ansettelsesPeriodeBuilder.periode = periodeBuilder.buildAaregPeriode()
        aaregArbeidsforholdBuilder.ansettelsesperiode = ansettelsesPeriodeBuilder.build()
        aaregArbeidsforholdBuilder.utenlandsopphold = mutableListOf(utenlandoppholdBuiler.build())
        aaregArbeidsforholdBuilder.arbeidsgiver = aaregArbeidsgiverBuilder.build()
        aaregArbeidsforholdBuilder.arbeidsavtaler = mutableListOf(arbeidsavtaleBuilder.build())
        aaregArbeidsforholdBuilder.permisjonPermitteringer = mutableListOf(permisjonsPermitteringerBuilder.build())
        aaregBuilder.arbeidsforhold = aaregArbeidsforholdBuilder.build()
        aaregBuilder.organisasjon = organisasjonBuilder.build()
        return aaregBuilder
    }
}
