package no.nav.medlemskap.domene.personhistorikk

import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Landkode
import no.nav.medlemskap.domene.Periode
import java.time.LocalDate

data class Statsborgerskap(
    val landkode: String,
    val fom: LocalDate?,
    val tom: LocalDate?,
    val historisk: Boolean?
) {
    private val periode = Periode(fom, tom)

    fun overlapper(dato: LocalDate): Boolean {
        return periode.overlapper(dato)
    }

    companion object {
        protected fun erBrukerBritiskBorgerUtenAnnetEøsStatsborgerskap(statsborgerskap: List<Statsborgerskap>, kontrollPeriodeForPersonhistorikk: Kontrollperiode): Boolean {
            if (!statsborgerskap.erBritiskBorger(kontrollPeriodeForPersonhistorikk)) {
                return false
            }

            return !statsborgerskap
                .gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk)
                .filterNot { Landkode.erBritisk(it) }.any { Landkode.erEØSland(it) }
        }

        infix fun List<Statsborgerskap>.statsborgerskapFørst(kontrollPeriodeForPersonhistorikk: Kontrollperiode): Set<String> {
            return this
                .filter { it.overlapper(kontrollPeriodeForPersonhistorikk.fom) && it.historisk == false }
                .map { it.landkode }
                .toSet()
        }

        infix fun List<Statsborgerskap>.statsborgerskapSist(kontrollPeriodeForPersonhistorikk: Kontrollperiode): Set<String> {
            return this
                .filter { it.overlapper(kontrollPeriodeForPersonhistorikk.tom) && it.historisk == false }
                .map { it.landkode }
                .toSet()
        }

        infix fun List<Statsborgerskap>.gyldigeStatsborgerskap(
            kontrollPeriodeForPersonhistorikk: Kontrollperiode
        ): List<String> {
            return statsborgerskapFørst(kontrollPeriodeForPersonhistorikk)
                .filter { statsborgerskapSist(kontrollPeriodeForPersonhistorikk).contains(it) }
        }

        infix fun List<Statsborgerskap>.erSveitsiskBorger(
            kontrollPeriodeForPersonhistorikk: Kontrollperiode
        ): Boolean {
            return gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk).any { Landkode.erSveitsisk(it) }
        }

        infix fun List<Statsborgerskap>.erBritiskBorger(
            kontrollPeriodeForPersonhistorikk: Kontrollperiode
        ): Boolean {
            return gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk).any { Landkode.erBritisk(it) }
        }

        infix fun List<Statsborgerskap>.erNorskBorger(
            kontrollPeriodeForPersonhistorikk: Kontrollperiode
        ): Boolean {
            return gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk).any { Landkode.erNorsk(it) }
        }

        infix fun List<Statsborgerskap>.harNyligBlittNorskStatsborger(kontrollPeriode: Kontrollperiode): Boolean {
            return this.any {
                Landkode.erNorsk(it.landkode) &&
                    it.harFaattStatsborgerskapIKontrollperiode(kontrollPeriode) &&
                    it.historisk == false
            }
        }

        infix fun Statsborgerskap.harFaattStatsborgerskapIKontrollperiode(kontrollPeriode: Kontrollperiode): Boolean =
            this.periode.fom?.isAfter(kontrollPeriode.fom) == true

        infix fun List<Statsborgerskap>.erNordiskBorger(
            kontrollPeriodeForPersonhistorikk: Kontrollperiode
        ): Boolean {
            return gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk).any { Landkode.erNordisk(it) }
        }

        infix fun List<Statsborgerskap>.erEøsBorger(
            kontrollPeriodeForPersonhistorikk: Kontrollperiode
        ): Boolean {
            return gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk).any { Landkode.erEØSland(it) }
        }

        infix fun List<Statsborgerskap>.hentStatsborgerskapFor(dato: LocalDate): List<String> =
            this.filter {
                it.overlapper(dato)
            }.map { it.landkode }

        infix fun List<Statsborgerskap>.harEndretSisteÅret(kontrollPeriode: Kontrollperiode): Boolean =
            this.any { erStatsborgerskapetInnenforPerioden(it, kontrollPeriode) }

        private fun erStatsborgerskapetInnenforPerioden(it: Statsborgerskap, kontrollPeriode: Kontrollperiode): Boolean =
            kontrollPeriode.periode.encloses(Periode(fom = it.fom, tom = it.fom)) || kontrollPeriode.periode.encloses(
                Periode(fom = it.tom, tom = it.tom)
            )

        fun List<Statsborgerskap>.erAnnenStatsborger(startDatoForYtelse: LocalDate): Boolean {
            val kontrollPeriodeForPersonhistorikk =
                Kontrollperiode.kontrollPeriodeForPersonhistorikk(startDatoForYtelse)

            return !erEøsBorger(kontrollPeriodeForPersonhistorikk)
        }
    }
}
