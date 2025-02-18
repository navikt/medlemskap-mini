package no.nav.medlemskap.cucumber.ytelse

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import no.nav.medlemskap.cucumber.ytelse.YtelseDomenespråkParser.Domenebegrep.YTELSE
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Ytelse
import java.time.LocalDate

object YtelseDomenespråkParser : BasisDomeneParser() {

    fun parseYtelse(rad: Map<String, String>): Ytelse {
        return Ytelse.valueOf(verdi(YTELSE.nøkkel(), rad))
    }

    fun mapKontrollperiode(dataTable: DataTable): Kontrollperiode {
        return mapDataTable(dataTable, KontrollperiodeMapper()).get(0)
    }

    fun mapParametreKontrollperiode(dataTable: DataTable): ParametreKontrollperiode {
        return mapDataTable(dataTable, ParametreKontrollperiodeMapper()).get(0)
    }

    class KontrollperiodeMapper : RadMapper<Kontrollperiode> {
        override fun mapRad(rad: Map<String, String>): Kontrollperiode {
            return Kontrollperiode(
                parseDato(Domenebegrep.FRA_OG_MED_DATO, rad),
                parseDato(Domenebegrep.TIL_OG_MED_DATO, rad)
            )
        }
    }

    class ParametreKontrollperiodeMapper : RadMapper<ParametreKontrollperiode> {
        override fun mapRad(rad: Map<String, String>): ParametreKontrollperiode {
            return ParametreKontrollperiode(
                parseYtelse(rad),
                InputPeriode(
                    parseDato(Domenebegrep.FRA_OG_MED_DATO, rad),
                    parseDato(Domenebegrep.TIL_OG_MED_DATO, rad)
                ),
                parseValgfriDato(Domenebegrep.FØRSTE_DAG_FOR_YTELSE, rad)
            )
        }
    }

    data class ParametreKontrollperiode(
        val ytelse: Ytelse,
        val iputPeriode: InputPeriode,
        val førsteDagForYtelse: LocalDate?
    )

    enum class Domenebegrep(val nøkkel: String) : Domenenøkkel {
        FRA_OG_MED_DATO("Fra og med dato"),
        FØRSTE_DAG_FOR_YTELSE("Første dag for ytelse"),
        START_DATO_FOR_YTELSE("Start dato for ytelse"),
        TIL_OG_MED_DATO("Til og med dato"),
        YTELSE("Ytelse");

        override fun nøkkel(): String {
            return nøkkel
        }
    }
}
