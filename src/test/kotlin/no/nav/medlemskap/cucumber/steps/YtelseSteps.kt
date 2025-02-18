package no.nav.medlemskap.cucumber.steps

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.cucumber.datatable.DataTable
import io.cucumber.java8.No
import junit.framework.TestCase.assertEquals
import no.nav.medlemskap.cucumber.DomenespråkParser
import no.nav.medlemskap.cucumber.ytelse.YtelseDomenespråkParser
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForArbeidsforhold
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForMedl
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForOppholdstillatelse
import no.nav.medlemskap.domene.Kontrollperiode.Companion.startDatoForYtelse
import no.nav.medlemskap.regler.common.Datohjelper.parseDato
import java.time.LocalDate

class YtelseSteps : No {
    private val ytelseDomenespråkParser = YtelseDomenespråkParser

    private var ytelsePeriode: InputPeriode? = null
    private var førsteDagForYtelse: LocalDate? = null
    private var kontrollperiode: Kontrollperiode? = null
    private var startDatoForYtelse: LocalDate? = null

    init {
        Gitt("følgende sykemeldingsperiode:") { dataTable: DataTable ->
            ytelsePeriode = DomenespråkParser.mapInputPeriode(dataTable)
        }

        Når("første sykedag beregnes fra sykemeldingsperiode") {
            startDatoForYtelse = startDatoForYtelse(ytelsePeriode!!, null)
            førsteDagForYtelse = startDatoForYtelse
        }

        Når("kontrollperiode for arbeidsforhold beregnes med følgende parametre:") { dataTable: DataTable ->
            val parametreKontrollperiode = ytelseDomenespråkParser.mapParametreKontrollperiode(dataTable)

            ytelsePeriode = parametreKontrollperiode.iputPeriode
            førsteDagForYtelse = parametreKontrollperiode.førsteDagForYtelse
            startDatoForYtelse = startDatoForYtelse(ytelsePeriode!!, førsteDagForYtelse)

            kontrollperiode = kontrollPeriodeForArbeidsforhold(startDatoForYtelse(ytelsePeriode!!, førsteDagForYtelse))
        }

        Når(
            "kontrollperiode for oppholdstillatelse beregnes med følgende parametre:"
        ) { dataTable: DataTable ->
            val parametreKontrollperiode = ytelseDomenespråkParser.mapParametreKontrollperiode(dataTable)

            ytelsePeriode = parametreKontrollperiode.iputPeriode
            førsteDagForYtelse = parametreKontrollperiode.førsteDagForYtelse
            startDatoForYtelse = startDatoForYtelse(ytelsePeriode!!, førsteDagForYtelse)

            kontrollperiode = kontrollPeriodeForOppholdstillatelse(startDatoForYtelse(ytelsePeriode!!, førsteDagForYtelse))
        }

        Når("kontrollperiode for medl beregnes med følgende parametre:") { dataTable: DataTable ->
            val parametreKontrollperiode = ytelseDomenespråkParser.mapParametreKontrollperiode(dataTable)

            ytelsePeriode = parametreKontrollperiode.iputPeriode
            startDatoForYtelse = startDatoForYtelse(ytelsePeriode!!, førsteDagForYtelse)

            kontrollperiode = kontrollPeriodeForMedl(startDatoForYtelse(ytelsePeriode!!, parametreKontrollperiode.førsteDagForYtelse))
        }

        Så("skal første sykedag være {string}") { forventetDato: String ->
            assertEquals(parseDato(forventetDato), førsteDagForYtelse)
        }

        Så("skal kontrollperioden være:") { dataTable: DataTable ->
            val forventetKontrollperiode = ytelseDomenespråkParser.mapKontrollperiode(dataTable)
            assertThat(kontrollperiode).isEqualTo(forventetKontrollperiode)
        }

        Så("skal startdato for ytelse være {string}") { forventetStartDatoForYtelse: String ->
            assertThat(startDatoForYtelse).isEqualTo(parseDato(forventetStartDatoForYtelse))
        }
    }
}
