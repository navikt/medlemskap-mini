package no.nav.medlemskap.cucumber.SpraakParserDomene

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.*
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.arbeidsforhold.*
import java.time.YearMonth

object ArbeidsforholdDomeneSpraakParser : BasisDomeneParser() {
    val ANSATTE_9 = listOf(Ansatte(9, null))
    val VANLIG_NORSK_ARBEIDSGIVER = Arbeidsgiver(navn = "null", organisasjonsnummer = "1", ansatte = ANSATTE_9, konkursStatus = null, juridiskeEnheter = null)

    fun mapArbeidsforhold(
        dataTable: DataTable,
        utenlandsopphold: List<Utenlandsopphold> = emptyList(),
        arbeidsgiver: Arbeidsgiver = VANLIG_NORSK_ARBEIDSGIVER
    ): List<Arbeidsforhold> {
        return dataTable.asMaps().map {
            ArbeidsforholdMapper().mapRad(it, utenlandsopphold, arbeidsgiver)
        }
    }

    fun mapArbeidsgivere(dataTable: DataTable): List<Arbeidsgiver> {
        return mapDataTable(dataTable, ArbeidsgiverMapper())
    }

    fun mapArbeidsavtaler(dataTable: DataTable): List<Arbeidsavtale> {
        return mapDataTable(dataTable, ArbeidsavtaleMapper())
    }

    fun mapPermisjonspermitteringer(dataTable: DataTable): List<PermisjonPermittering> {
        return mapDataTable(dataTable, PermisjonsPermitteringMapper())
    }

    fun mapUtenlandsopphold(dataTable: DataTable): List<Utenlandsopphold> {
        return mapDataTable(dataTable, UtenlandsoppholdMapper())
    }

    fun mapTypeIArbeidsforhold(dataTable: DataTable): String {
        return mapDataTable(dataTable, TypeMapper())[0]
    }

    fun mapAnsatte(dataTable: DataTable): List<Ansatte> {
        return mapDataTable(dataTable, AnsattMapper())
    }

    fun mapArbeidsgivertype(dataTable: DataTable): OpplysningspliktigArbeidsgiverType {
        return mapDataTable(dataTable, ArbeidsgivertypeMapper())[0]
    }

    fun mapArbeidsforholdstype(dataTable: DataTable): Arbeidsforholdstype {
        return mapDataTable(dataTable, ArbeidsforholdtypeMapper())[0]
    }

    fun mapBeregnetAntallTimerUke(dataTable: DataTable): Double {
        return mapDataTable(dataTable, BeregnetAntallTimerPerUkeMapper())[0]
    }

    fun mapFartsomraade(dataTable: DataTable): Fartsomraade {
        return mapDataTable(dataTable, FartsomraadeMapper())[0]
    }

    fun mapKonkurStatuser(dataTable: DataTable): List<String> {
        return mapDataTable(dataTable, StatusMapper())
    }

    fun mapLandkode(dataTable: DataTable): String {
        return mapDataTable(dataTable, LandkodeMapper())[0]
    }

    fun mapPeriodeIArbeidsavtale(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, PeriodeMapper())[0]
    }

    fun mapOrganisasjonsnummer(dataTable: DataTable): String {
        return mapDataTable(dataTable, OrganisasjonsnummerMapper())[0]
    }

    fun mapPeriodeIUtenlandsopphold(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, PeriodeMapper())[0]
    }

    fun mapPeriodeForPermittering(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, PeriodeMapper())[0]
    }

    fun mapPeriodeIArbeidsforhold(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, PeriodeMapper())[0]
    }

    fun mapPermitteringId(dataTable: DataTable): String {
        return mapDataTable(dataTable, PermitteringsIdMapper())[0]
    }

    fun mapSkipsregister(dataTable: DataTable): Skipsregister {
        return mapDataTable(dataTable, SkipsregisterMapper())[0]
    }

    fun mapSkipstype(dataTable: DataTable): Skipstype {
        return mapDataTable(dataTable, SkipstypeMapper())[0]
    }

    fun mapRapporteringsperiode(dataTable: DataTable): YearMonth {
        return mapDataTable(dataTable, RapporteringsperiodeMapper())[0]
    }

    fun mapStillingsprosent(dataTable: DataTable): Double {
        return mapDataTable(dataTable, StillingsprosentMapper())[0]
    }

    fun mapVarslingskode(dataTable: DataTable): String {
        return mapDataTable(dataTable, VarslingskodeMapper())[0]
    }

    fun mapYrkeskode(dataTable: DataTable): String {
        return mapDataTable(dataTable, YrkeskodeMapper())[0]
    }

    fun mapProsent(dataTable: DataTable): Double {
        return mapDataTable(dataTable, ProsentMapper())[0]
    }

    fun mapType(dataTable: DataTable): PermisjonPermitteringType {
        return mapDataTable(dataTable, PermitteringsTypeMapper())[0]
    }

    class ArbeidsgivertypeMapper : RadMapper<OpplysningspliktigArbeidsgiverType> {
        override fun mapRad(rad: Map<String, String>): OpplysningspliktigArbeidsgiverType {
            return OpplysningspliktigArbeidsgiverType.valueOf(parseString(ArbeidDomenebegrep.ARBEIDSGIVERTYPE, rad))
        }
    }

    class SkipsregisterMapper : RadMapper<Skipsregister> {
        override fun mapRad(rad: Map<String, String>): Skipsregister {
            return Skipsregister.valueOf(parseString(ArbeidDomenebegrep.SKIPSREGISTER, rad))
        }
    }

    class SkipstypeMapper : RadMapper<Skipstype> {
        override fun mapRad(rad: Map<String, String>): Skipstype {
            return Skipstype.valueOf(parseString(ArbeidDomenebegrep.SKIPSTYPE, rad))
        }
    }

    class StatusMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.STATUS, rad)
        }
    }

    class AnsattMapper : RadMapper<Ansatte> {
        override fun mapRad(rad: Map<String, String>): Ansatte {
            return Ansatte(
                antall = parseInt(ArbeidDomenebegrep.ANTALL_ANSATTE, rad),
                gyldighetsperiode = Periode(
                    parseDato(ArbeidDomenebegrep.GYLDIGHETSPERIODE_FRA_OG_MED, rad),
                    parseDato(ArbeidDomenebegrep.GYLDIGHETSPERIODE_TIL_OG_MED, rad)
                )
            )
        }
    }

    class ArbeidsforholdtypeMapper : RadMapper<Arbeidsforholdstype> {
        override fun mapRad(rad: Map<String, String>): Arbeidsforholdstype {
            return Arbeidsforholdstype.valueOf(parseString(ArbeidDomenebegrep.ARBEIDSFORHOLDSTYPE, rad))
        }
    }

    class LandkodeMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.LANDKODE, rad)
        }
    }

    class OrganisasjonsnummerMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.ORGANISASJONSNUMMER, rad)
        }
    }

    class PermitteringsIdMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.PERMISJONPERMITTERINGSID, rad)
        }
    }

    class FartsomraadeMapper : RadMapper<Fartsomraade> {
        override fun mapRad(rad: Map<String, String>): Fartsomraade {
            return Fartsomraade.valueOf(parseString(ArbeidDomenebegrep.FARTSOMRÅDE, rad))
        }
    }

    class PeriodeMapper : RadMapper<Periode> {
        override fun mapRad(rad: Map<String, String>): Periode {
            return Periode(
                parseValgfriDato(ArbeidDomenebegrep.FRA_OG_MED_DATO, rad),
                parseValgfriDato(ArbeidDomenebegrep.TIL_OG_MED_DATO, rad)
            )
        }
    }

    class PermitteringsTypeMapper : RadMapper<PermisjonPermitteringType> {
        override fun mapRad(rad: Map<String, String>): PermisjonPermitteringType {
            return PermisjonPermitteringType.valueOf(parseString(ArbeidDomenebegrep.PERMITTERINGSTYPE, rad))
        }
    }

    class ProsentMapper : RadMapper<Double> {
        override fun mapRad(rad: Map<String, String>): Double {
            return parseDouble(ArbeidDomenebegrep.PROSENT, rad)
        }
    }

    class RapporteringsperiodeMapper : RadMapper<YearMonth> {
        override fun mapRad(rad: Map<String, String>): YearMonth {
            return YearMonth.parse(parseString(ArbeidDomenebegrep.RAPPORTERINGSPERIODE, rad))
        }
    }

    class BeregnetAntallTimerPerUkeMapper : RadMapper<Double> {
        override fun mapRad(rad: Map<String, String>): Double {
            return parseDouble(ArbeidDomenebegrep.BEREGNET_ANTALL_TIMER_PR_UKE, rad)
        }
    }

    class TypeMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.ARBEIDSGIVERTYPE, rad)
        }
    }

    class StillingsprosentMapper : RadMapper<Double> {
        override fun mapRad(rad: Map<String, String>): Double {
            return parseDouble(ArbeidDomenebegrep.STILLINGSPROSENT, rad)
        }
    }

    class VarslingskodeMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.VARSLINGSKODE, rad)
        }
    }

    class YrkeskodeMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(ArbeidDomenebegrep.YRKESKODE, rad)
        }
    }

    class ArbeidsforholdMapper {
        fun mapRad(
            rad: Map<String, String>,
            utenlandsopphold: List<Utenlandsopphold> = emptyList(),
            arbeidsgiver: Arbeidsgiver?
        ): Arbeidsforhold {
            val periode = Periode(
                parseValgfriDato(ArbeidDomenebegrep.FRA_OG_MED_DATO, rad),
                parseValgfriDato(ArbeidDomenebegrep.TIL_OG_MED_DATO, rad)
            )

            return Arbeidsforhold(
                periode = periode,
                utenlandsopphold = utenlandsopphold,
                arbeidsgivertype = OpplysningspliktigArbeidsgiverType.valueOf(parseString(ArbeidDomenebegrep.ARBEIDSGIVERTYPE, rad)),
                arbeidsgiver = arbeidsgiver ?: VANLIG_NORSK_ARBEIDSGIVER,
                arbeidsforholdstype = parseArbeidsforholdstype(rad),
                arbeidsavtaler = emptyList(),
                permisjonPermittering = emptyList()
            )
        }
    }

    class ArbeidsavtaleMapper : RadMapper<Arbeidsavtale> {
        override fun mapRad(rad: Map<String, String>): Arbeidsavtale {
            return Arbeidsavtale(
                Periode(
                    parseDato(ArbeidDomenebegrep.FRA_OG_MED_DATO, rad),
                    parseValgfriDato(ArbeidDomenebegrep.TIL_OG_MED_DATO, rad)
                ),
                Periode(
                    parseDato(ArbeidDomenebegrep.FRA_OG_MED_DATO, rad),
                    parseValgfriDato(ArbeidDomenebegrep.TIL_OG_MED_DATO, rad)
                ),
                parseString(ArbeidDomenebegrep.YRKESKODE, rad),
                parseSkipsregister(rad),
                parseFartsområde(rad),
                parseDouble(ArbeidDomenebegrep.STILLINGSPROSENT, rad),
                parseValgfriDouble(ArbeidDomenebegrep.BEREGNET_ANTALL_TIMER_PR_UKE, rad),
                parseSkipstype(rad)
            )
        }
    }

    class ArbeidsgiverMapper : RadMapper<Arbeidsgiver> {
        override fun mapRad(rad: Map<String, String>): Arbeidsgiver {
            val konkursStatus = parseValgfriString(ArbeidDomenebegrep.KONKURSSTATUS, rad)
            val konkursStatuser = if (konkursStatus == null) {
                null
            } else {
                listOf(konkursStatus)
            }

            return Arbeidsgiver(
                navn = parseValgfriString(ArbeidDomenebegrep.Navn, rad),
                organisasjonsnummer = parseValgfriString(ArbeidDomenebegrep.IDENTIFIKATOR, rad),
                ansatte = listOf(Ansatte(parseValgfriInt(ArbeidDomenebegrep.ANTALL_ANSATTE, rad), null)),
                konkursStatus = konkursStatuser,
                juridiskeEnheter = listOf(JuridiskEnhet(parseValgfriString(ArbeidDomenebegrep.JURIDISK_ORG_NR, rad), parseValgfriString(Domenebegrep.JURIDISK_ENHETSTYPE, rad), parseValgfriInt(Domenebegrep.JURIDISK_ANTALL_ANSATTE, rad)))
            )
        }
    }

    class UtenlandsoppholdMapper : RadMapper<Utenlandsopphold> {
        override fun mapRad(rad: Map<String, String>): Utenlandsopphold {
            return Utenlandsopphold(
                landkode = parseString(Domenebegrep.LANDKODE, rad),
                periode = Periode(
                    parseValgfriDato(Domenebegrep.FRA_OG_MED_DATO, rad),
                    parseValgfriDato(Domenebegrep.TIL_OG_MED_DATO, rad)
                ),
                rapporteringsperiode = DomenespråkParser.parseAarMaaned(Domenebegrep.RAPPORTERINGSPERIODE, rad)
            )
        }
    }

    class PermisjonsPermitteringMapper : RadMapper<PermisjonPermittering> {
        override fun mapRad(rad: Map<String, String>): PermisjonPermittering {
            return PermisjonPermittering(
                periode = Periode(
                    parseDato(Domenebegrep.FRA_OG_MED_DATO, rad),
                    parseDato(Domenebegrep.TIL_OG_MED_DATO, rad)
                ),
                permisjonPermitteringId = parseString(ArbeidDomenebegrep.PERMISJONPERMITTERINGSID, rad),
                prosent = parseDouble(Domenebegrep.PROSENT, rad),
                type = parsePermisjonPermitteringType(rad),
                varslingskode = parseValgfriString(ArbeidDomenebegrep.VARSLINGSKODE, rad)
            )
        }
    }
}

enum class ArbeidDomenebegrep(val nøkkel: String) : Domenenøkkel {
    Navn("Navn"),
    ANTALL_ANSATTE("Antall ansatte"),
    ARBEIDSFORHOLDSTYPE("Arbeidsforholdstype"),
    ARBEIDSGIVER_ID("Arbeidsgiver Id"),
    ARBEIDSGIVERTYPE("Arbeidsgivertype"),
    BEREGNET_ANTALL_TIMER_PR_UKE("Beregnet antall timer pr uke"),
    FRA_OG_MED_DATO("Fra og med dato"),
    FARTSOMRÅDE("Fartsområde"),
    GYLDIGHETSPERIODE_FRA_OG_MED("Gyldighetsperiode gyldig fra"),
    GYLDIGHETSPERIODE_TIL_OG_MED("Gyldighetsperiode gyldig til"),
    KONKURSSTATUS("Konkursstatus"),
    LANDKODE("Landkode"),
    ORGANISASJONSNUMMER("Organisasjonsnummer"),
    IDENTIFIKATOR("Identifikator"),
    JURIDISK_ORG_NR("Juridisk orgnr"),
    PERMITTERINGSTYPE("Type"),
    PERMISJONPERMITTERINGSID("PermisjonPermitteringId"),
    PROSENT("Prosent"),
    RELATERT_VED_SIVILSTAND("Relatert ved sivilstand"),
    RAPPORTERINGSPERIODE("Rapporteringsperiode"),
    TIL_OG_MED_DATO("Til og med dato"),
    SKIPSREGISTER("Skipsregister"),
    SKIPSTYPE("Skipstype"),
    STILLINGSPROSENT("Stillingsprosent"),
    STATUS("Status"),
    VARSLINGSKODE("Varslingkode"),
    YRKESKODE("Yrkeskode");

    override fun nøkkel(): String {
        return nøkkel
    }
}
