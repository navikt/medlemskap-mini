package no.nav.medlemskap.cucumber

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.cucumber.Domenebegrep.*
import no.nav.medlemskap.cucumber.SpraakParserDomene.MedlemskapDomenebegrep
import no.nav.medlemskap.cucumber.SpraakParserDomene.OppgaveDomenebegrep
import no.nav.medlemskap.cucumber.SpraakParserDomene.PersonhistorikkDomenebegrep
import no.nav.medlemskap.domene.*
import no.nav.medlemskap.domene.arbeidsforhold.Ansatte
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforholdstype
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsgiver
import no.nav.medlemskap.domene.personhistorikk.ForelderBarnRelasjonRolle
import no.nav.medlemskap.domene.personhistorikk.Sivilstandstype
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Svar
import java.time.LocalDate
import java.time.YearMonth

object DomenespråkParser : BasisDomeneParser() {
    val ANSATTE_9 = listOf(Ansatte(9, null))
    val VANLIG_NORSK_ARBEIDSGIVER =
        Arbeidsgiver(navn = "null", organisasjonsnummer = "1", ansatte = ANSATTE_9, konkursStatus = null, juridiskeEnheter = null)

    fun parseValgfriYtelse(domenebegrep: Domenebegrep, rad: Map<String, String>): Ytelse? {
        val valgfriVerdi = valgfriVerdi(domenebegrep.nøkkel, rad)

        return if (valgfriVerdi == null) {
            null
        } else {
            Ytelse.valueOf(valgfriVerdi)
        }
    }

    fun parseValgfriPeriodeStatus(domenebegrep: Domenebegrep, rad: Map<String, String>): PeriodeStatus? {
        val valgfriVerdi = valgfriVerdi(domenebegrep.nøkkel, rad)

        return if (valgfriVerdi == null) {
            null
        } else {
            PeriodeStatus.valueOf(valgfriVerdi)
        }
    }

    fun parseValgfriLovvalg(domenebegrep: MedlemskapDomenebegrep, rad: Map<String, String>): Lovvalg? {
        val valgfriVerdi = valgfriVerdi(domenebegrep.nøkkel, rad)

        return if (valgfriVerdi == null) {
            null
        } else {
            Lovvalg.valueOf(valgfriVerdi)
        }
    }

    fun parseRegelId(domenebegrep: Domenebegrep, rad: Map<String, String>): RegelId {
        val verdi = verdi(domenebegrep.nøkkel, rad)

        return parseRegelId(verdi)
    }

    fun parseRegelId(regelIdStr: String): RegelId {
        val regelId = RegelId.fraRegelIdString(regelIdStr)
        if (regelId == null) {
            throw RuntimeException("Fant ikke regel med id = {regelIdStr}")
        }

        return regelId
    }

    fun parseSvar(domenebegrep: Domenenøkkel, rad: Map<String, String>): Svar {
        val verdi = verdi(domenebegrep.nøkkel(), rad)
        return parseSvar(verdi)
    }

    fun parseValgfrittSvar(domenebegrep: Domenenøkkel, rad: Map<String, String>): Svar? {
        val valgfriVerdi = valgfriVerdi(domenebegrep.nøkkel(), rad)

        if (valgfriVerdi == null) {
            return null
        }

        return parseSvar(domenebegrep, rad)
    }

    fun parseSvar(verdi: String): Svar {
        return when (verdi.toUpperCase()) {
            "JA" -> Svar.JA
            "NEI" -> Svar.NEI
            else -> Svar.UAVKLART
        }
    }

    fun parseAarMaaned(domenebegrep: Domenebegrep, rad: Map<String, String>): YearMonth {
        return YearMonth.parse(rad.get(domenebegrep.nøkkel)!!)
    }

    fun parseArbeidsforholdstype(rad: Map<String, String>): Arbeidsforholdstype {
        val verdi = verdi(ARBEIDSFORHOLDSTYPE.nøkkel, rad)

        return Arbeidsforholdstype.valueOf(verdi)
    }

    fun parseSivilstandstype(domenebegrep: PersonhistorikkDomenebegrep, rad: Map<String, String>): Sivilstandstype {
        val verdi = verdi(domenebegrep.nøkkel, rad)

        return Sivilstandstype.valueOf(verdi)
    }

    fun parsePrioritet(domenebegrep: OppgaveDomenebegrep, rad: Map<String, String>): Prioritet {
        val verdi = verdi(domenebegrep.nøkkel, rad)

        return Prioritet.valueOf(verdi)
    }

    fun parseStatus(domenebegrep: Domenebegrep, rad: Map<String, String>): Status {
        val verdi = verdi(domenebegrep.nøkkel, rad)

        return Status.valueOf(verdi)
    }

    fun parseRolle(domenebegrep: PersonhistorikkDomenebegrep, rad: Map<String, String>): ForelderBarnRelasjonRolle {
        val verdi = verdi(domenebegrep.nøkkel, rad)

        return ForelderBarnRelasjonRolle.valueOf(verdi)
    }

    fun parseValgfriRolle(
        domenebegrep: PersonhistorikkDomenebegrep,
        rad: Map<String, String>
    ): ForelderBarnRelasjonRolle? {
        val verdi = valgfriVerdi(domenebegrep.nøkkel, rad)

        if (verdi == null) {
            return null
        }

        return ForelderBarnRelasjonRolle.valueOf(verdi)
    }

    fun mapMedlemskap(dataTable: DataTable): List<Medlemskap> {
        return mapDataTable(dataTable, MedlemskapMapper())
    }

    fun mapMedlemskapsparametre(dataTable: DataTable): Medlemskapsparametre {
        return mapDataTable(dataTable, MedlemskapsparametreMapper()).get(0)
    }

    fun mapOverstyrteRegler(dataTable: DataTable): Map<RegelId, Svar> {
        val overstyrteRegler = mapDataTable(dataTable, OverstyrteReglerMapper())
        return overstyrteRegler
            .filter { it -> it.second != null }
            .map { Pair(it.first, it.second!!) }
            .toMap()
    }

    fun mapRegelId(dataTable: DataTable): List<RegelId> {
        return mapDataTable(dataTable, RegelIdMapper())
    }

    fun mapInputPeriode(dataTable: DataTable): InputPeriode? {
        return mapDataTable(dataTable, InputPeriodeMapper()).get(0)
    }

    class RegelIdMapper : RadMapper<RegelId> {
        override fun mapRad(rad: Map<String, String>): RegelId {

            return parseRegelId(REGEL, rad)
        }
    }

    class InputPeriodeMapper : RadMapper<InputPeriode> {
        override fun mapRad(rad: Map<String, String>): InputPeriode {
            return InputPeriode(
                parseDato(FRA_OG_MED_DATO, rad),
                parseDato(TIL_OG_MED_DATO, rad)
            )
        }
    }

    class MedlemskapsparametreMapper : RadMapper<Medlemskapsparametre> {
        override fun mapRad(rad: Map<String, String>): Medlemskapsparametre {
            return Medlemskapsparametre(
                fnr = parseValgfriString(FØDSELSNUMMER, rad),
                inputPeriode = InputPeriode(
                    parseDato(FRA_OG_MED_DATO, rad),
                    parseDato(TIL_OG_MED_DATO, rad)
                ),
                førsteDagForYtelse = parseValgfriDato(FØRSTE_DAG_FOR_YTELSE, rad),
                brukerinput = parseBrukerinput(rad),
                ytelse = parseValgfriYtelse(YTELSE, rad)
            )
        }
    }

    private fun parseBrukerinput(rad: Map<String, String>): Brukerinput {
        return Brukerinput(
            arbeidUtenforNorge = parseBoolean(HAR_HATT_ARBEID_UTENFOR_NORGE, rad),
            oppholdstilatelse = parseValgfriOppholdstillatelse(rad),
            utfortAarbeidUtenforNorge = parseValgfriUtfortArbeidUtenforNorge(rad),
            oppholdUtenforNorge = parseValgfriOppholdUtenforNorge(rad),
            oppholdUtenforEos = parseValgfriOppholdUtenforEOS(rad)
        )
    }

    private fun parseValgfriOppholdUtenforEOS(rad: Map<String, String>): OppholdUtenforEos? {
        val oppholdUtenforEOS = parseValgfriBoolean(HAR_OPPHOLD_UTENFOR_EOS.nøkkel, rad)
        return if (oppholdUtenforEOS == null) {
            null
        } else {
            OppholdUtenforEos(
                svar = oppholdUtenforEOS,
                id = "null",
                sporsmalstekst = null,
                oppholdUtenforEOS = listOf()
            )
        }
    }

    private fun parseValgfriOppholdUtenforNorge(rad: Map<String, String>): OppholdUtenforNorge? {
        val oppholdUtenforNorge = parseValgfriBoolean(HAR_OPPHOLD_UTENFOR_NORGE.nøkkel, rad)
        return if (oppholdUtenforNorge == null) {
            null
        } else {
            OppholdUtenforNorge(
                svar = oppholdUtenforNorge,
                id = "null",
                sporsmalstekst = null,
                oppholdUtenforNorge = listOf()
            )
        }
    }

    private fun parseValgfriUtfortArbeidUtenforNorge(rad: Map<String, String>): UtfortAarbeidUtenforNorge? {
        val utfortArbeidUtenforNorge = parseValgfriBoolean(HAR_UTFORT_ARBEID_UTENFOR_NORGE.nøkkel, rad)
        return if (utfortArbeidUtenforNorge == null) {
            null
        } else {
            UtfortAarbeidUtenforNorge(
                svar = utfortArbeidUtenforNorge,
                id = "null",
                sporsmalstekst = null,
                arbeidUtenforNorge = listOf()
            )
        }
    }

    private fun parseValgfriOppholdstillatelse(rad: Map<String, String>): Oppholdstilatelse? {
        val oppholdstillatelse = parseValgfriBoolean(HAR_BRUKER_OPPHOLDSTILLATELSE.nøkkel, rad)
        return if (oppholdstillatelse == null) {
            null
        } else {
            Oppholdstilatelse(
                svar = oppholdstillatelse,
                id = "null",
                sporsmalstekst = null,
                vedtaksTypePermanent = false,
                vedtaksdato = LocalDate.MIN

            )
        }
    }

    class OverstyrteReglerMapper : RadMapper<Pair<RegelId, Svar?>> {
        override fun mapRad(rad: Map<String, String>): Pair<RegelId, Svar?> {
            return Pair(parseRegelId(REGEL, rad), parseValgfrittSvar(SVAR, rad))
        }
    }

    class MedlemskapMapper : RadMapper<Medlemskap> {
        override fun mapRad(rad: Map<String, String>): Medlemskap {
            return Medlemskap(
                parseValgfriString(DEKNING, rad),
                parseDato(FRA_OG_MED_DATO, rad),
                parseDato(TIL_OG_MED_DATO, rad),
                parseBoolean(ER_MEDLEM, rad),
                parseValgfriLovvalg(MedlemskapDomenebegrep.LOVVALG, rad),
                parseValgfriString(LOVVALGSLAND, rad),
                parseValgfriPeriodeStatus(PERIODESTATUS, rad),
                "FTL_2-8_1_ledd_a"
            )
        }
    }
}

enum class Domenebegrep(val nøkkel: String) : Domenenøkkel {
    BOSTED("Bosted"),
    ARBEIDSFORHOLDSTYPE("Arbeidsforholdstype"),
    DEKNING("Dekning"),
    ER_MEDLEM("Er medlem"),
    FRA_OG_MED_DATO("Fra og med dato"),
    FØRSTE_DAG_FOR_YTELSE("Første dag for ytelse"),
    FØDSELSNUMMER("Fødselsnummer"),
    GYLDIGHETSPERIODE_FRA_OG_MED("Gyldighetsperiode gyldig fra"),
    GYLDIGHETSPERIODE_TIL_OG_MED("Gyldighetsperiode gyldig til"),
    HAR_HATT_ARBEID_UTENFOR_NORGE("Har hatt arbeid utenfor Norge"),
    HAR_BRUKER_OPPHOLDSTILLATELSE("Har oppholdstillatelse"),
    HAR_UTFORT_ARBEID_UTENFOR_NORGE("Utført Arbeid Utenfor Norge"),
    HAR_OPPHOLD_UTENFOR_NORGE("Opphold utenfor Norge"),
    HAR_OPPHOLD_UTENFOR_EOS("Opphold utenfor EØS"),
    IDENT("Ident"),
    JURIDISK_ANTALL_ANSATTE("Antall ansatte i juridisk enhet"),
    JURIDISK_ENHETSTYPE("Juridisk enhetstype"),
    LANDKODE("Landkode"),
    LOVVALGSLAND("Lovvalgsland"),
    PERIODESTATUS("Periodestatus"),
    PROSENT("Prosent"),
    REGEL("Regel"),
    RELATERT_VED_SIVILSTAND("Relatert ved sivilstand"),
    RAPPORTERINGSPERIODE("Rapporteringsperiode"),
    STATUS("Status"),
    SVAR("Svar"),
    TIL_OG_MED_DATO("Til og med dato"),
    YTELSE("Ytelse");

    override fun nøkkel(): String {
        return nøkkel
    }
}

data class Medlemskapsparametre(
    val fnr: String?,
    val inputPeriode: InputPeriode,
    val førsteDagForYtelse: LocalDate?,
    val brukerinput: Brukerinput,
    val ytelse: Ytelse?
) {

    fun tilJson(): String {
        return objectMapper.writeValueAsString(
            Request(
                fnr = fnr!!,
                periode = inputPeriode,
                brukerinput = brukerinput,
                førsteDagForYtelse = førsteDagForYtelse,
                ytelse = ytelse
            )
        )
    }
}
