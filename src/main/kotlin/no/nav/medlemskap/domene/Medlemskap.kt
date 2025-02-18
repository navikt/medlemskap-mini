package no.nav.medlemskap.domene

import no.nav.medlemskap.domene.Medlemskap.Companion.angittePerioderSammenfallerMedUnntaksperioderInnenforAngittSlingringsrom
import no.nav.medlemskap.domene.Medlemskap.Companion.unntakIMedlForKontrollperiode
import no.nav.medlemskap.regler.common.Funksjoner.er
import java.time.LocalDate

data class Medlemskap(
    val dekning: String?,
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val erMedlem: Boolean,
    val lovvalg: Lovvalg?,
    val lovvalgsland: String?,
    val periodeStatus: PeriodeStatus?,
    val grunnlag:String?
) : Comparable<Medlemskap> {
    private val periode = Periode(fraOgMed, tilOgMed)

    fun overlapper(annenPeriode: Periode): Boolean {
        return periode.overlapper(annenPeriode)
    }

    override fun compareTo(other: Medlemskap): Int {
        return this.tilOgMed.compareTo(other.tilOgMed)
    }

    companion object {
        infix fun List<Medlemskap>.finnesPersonIMedlForKontrollPeriode(kontrollPeriode: Kontrollperiode): Boolean =
            this.filter {
                it.overlapper(kontrollPeriode.periode) && it.periodeStatus != PeriodeStatus.AVST
            }.isNotEmpty()

        infix fun List<Medlemskap>.finnesUavklartePerioder(kontrollPeriode: Kontrollperiode): Boolean =
            this.filter {
                it.overlapper(kontrollPeriode.periode) &&
                    (
                        (it.lovvalg != null && it.lovvalg != Lovvalg.ENDL) ||
                            (it.periodeStatus != null && it.periodeStatus != PeriodeStatus.GYLD)
                        )
            }.isNotEmpty()

        infix fun List<Medlemskap>.harMedlPeriodeMedOgUtenMedlemskap(kontrollPeriode: Kontrollperiode): Boolean =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).any { it.erMedlem } &&
                this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).any { !it.erMedlem }

        fun List<Medlemskap>.harUnntakInnenforAngittePerioder(kontrollPeriode: Kontrollperiode, perioder: List<Periode>): Boolean =
            this.unntakIMedlForKontrollperiode(kontrollPeriode)
                .any { unntak -> perioder.any { it.overlapper(unntak.periode) } }

        fun List<Medlemskap>.erPeriodeSammenfallendeMedUnntak(kontrollPeriode: Kontrollperiode, perioder: List<Periode>, dagersSlingringsrom: Long): Boolean {
            val unntakIKontrollperiode = this.unntakIMedlForKontrollperiode(kontrollPeriode)

            return angittePerioderSammenfallerMedUnntaksperioderInnenforAngittSlingringsrom(unntakIKontrollperiode, perioder, dagersSlingringsrom) ||
                erFomOgTomIUnntaksperiodeLikAngittPeriode(unntakIKontrollperiode, perioder)
        }

        private fun angittePerioderSammenfallerMedUnntaksperioderInnenforAngittSlingringsrom(unntak: List<Medlemskap>, perioder: List<Periode>, dagersSlingringsrom: Long): Boolean {
            val unntakStartdatoMedSlingringsmonn = unntak
                .map { Periode(it.periode.fom?.minusDays(dagersSlingringsrom), it.periode.fom?.plusDays(dagersSlingringsrom)) }

            return unntakStartdatoMedSlingringsmonn.any { unntaksperiode ->
                perioder.any {
                    (it.fom?.isAfter(unntaksperiode.fom) == true && it.fom.isBefore(unntaksperiode.tom))
                }
            }
        }

        private fun erFomOgTomIUnntaksperiodeLikAngittPeriode(unntak: List<Medlemskap>, perioder: List<Periode>) =
            unntak.any { unntaksperiode ->
                perioder.any { it.fom?.isEqual(unntaksperiode.fraOgMed) == true }
            }

        infix fun List<Medlemskap>.harPeriodeMedMedlemskap(kontrollPeriode: Kontrollperiode): Boolean =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).any { it.erMedlem && it.lovvalgsland er "NOR" }

        infix fun List<Medlemskap>.harPeriodeUtenMedlemskap(kontrollPeriode: Kontrollperiode): Boolean =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).any { !it.erMedlem }

        infix fun List<Medlemskap>.harUSAellerCANunntakiKontrollperiode(kontrollPeriode: Kontrollperiode): Boolean =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).filter { !it.erMedlem }
                .any { it.lovvalgsland.equals("USA") || it.lovvalgsland.equals("CAN") }

        infix fun List<Medlemskap>.harGyldigeMedlemskapsperioder(kontrollPeriode: Kontrollperiode): Boolean =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).none { it.tilOgMed.isAfter(it.fraOgMed.plusYears(5)) }

        fun List<Medlemskap>.erMedlemskapsperioderOver12Mnd(erMedlem: Boolean, kontrollPeriode: Kontrollperiode): Boolean =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).filter { it.erMedlem == erMedlem }
                .harSammenhengendeMedlemskapIHeleGittPeriode(kontrollPeriode)

        infix fun List<Medlemskap>.gjeldendeDekning(kontrollPeriode: Kontrollperiode): String? =
            this.medlemskapsPerioderOver12MndPeriode(true, kontrollPeriode).sorted().last().dekning

        private fun List<Medlemskap>.harSammenhengendeMedlemskapIHeleGittPeriode(kontrollPeriode: Kontrollperiode) =
            this.any { it.fraOgMed.isBefore(kontrollPeriode.fom.plusDays(1)) } &&
                this.any { it.tilOgMed.isAfter(kontrollPeriode.tom.minusDays(1)) } &&
                this.sammenhengendePerioder()

        private fun List<Medlemskap>.sammenhengendePerioder() = this.sorted().zipWithNext { a, b -> b.fraOgMed.isBefore(a.tilOgMed.plusDays(2)) }.all { it }

        infix fun List<Medlemskap>.tidligsteFraOgMedDatoForMedl(kontrollPeriode: Kontrollperiode): LocalDate =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).minOrNull()!!.fraOgMed

        private fun List<Medlemskap>.unntakIMedlForKontrollperiode(kontrollperiode: Kontrollperiode): List<Medlemskap> =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollperiode)
                .filter { !it.erMedlem }

        private fun List<Medlemskap>.medlemskapsPerioderOver12MndPeriode(erMedlem: Boolean, kontrollPeriode: Kontrollperiode): List<Medlemskap> =
            this.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode).filter {
                it.erMedlem == erMedlem
            }

        private infix fun List<Medlemskap>.brukerensMedlemskapsperioderIMedlForPeriode(kontrollPeriode: Kontrollperiode): List<Medlemskap> =
            this.filter {
                it.overlapper(kontrollPeriode.periode) &&
                    (it.lovvalg == null || it.lovvalg == Lovvalg.ENDL) &&
                    (it.periodeStatus == null || it.periodeStatus == PeriodeStatus.GYLD)
            }
    }
}

enum class Lovvalg() {
    ENDL, FORL, UAVK
}

enum class PeriodeStatus() {
    GYLD, AVST, UAVK
}

enum class Dekning(val dekningKodeverdi: String) {
    FOLKETRYGDLOVEN2_6("FTL_2-6"),
    FOLKETRYGDLOVEN2_7A_2A("FTL_2-7a_2_ledd_a"),
    FOLKETRYGDLOVEN2_7A_2B("FTL_2-7a_2_ledd_b"),
    FOLKETRYGDLOVEN2_7_3A("FTL_2-7_3_ledd_a"),
    FOLKETRYGDLOVEN2_7_3B("FTL_2-7_3_ledd_b"),
    FOLKETRYGDLOVEN2_9_1A("FTL_2-9_1_ledd_a"),
    FOLKETRYGDLOVEN2_9_1B("FTL_2-9_1_ledd_b"),
    FOLKETRYGDLOVEN2_9_1C("FTL_2-9_1_ledd_c"),
    FOLKETRYGDLOVEN2_9_2_1A("FTL_2-9_2_ld_jfr_1a"),
    FOLKETRYGDLOVEN2_9_2_1C("FTL_2-9_2_ld_jfr_1c"),
    FULL("Full"),
    IHT_AVTALE("IHT_Avtale"),
    IKKEPENDEL("IKKEPENDEL"),
    OPPHOR("Opphor"),
    PENDEL("PENDEL"),
    UNNTATT("Unntatt");

    companion object {

        private val dekningForYtelseMap: Map<Ytelse, List<Dekning>> = hashMapOf(
            Ytelse.SYKEPENGER to dekningForSykepenger(),
            Ytelse.DAGPENGER to dekningForDagpenger(),
            Ytelse.ENSLIG_FORSORGER to dekningForEnsligForsorger()
        )

        private val uavklarteDekningerForYtelseMap: Map<Ytelse, List<Dekning>> = hashMapOf(
            Ytelse.SYKEPENGER to uavklarteDekningerForSykepenger(),
            Ytelse.DAGPENGER to fellesUavklarteDekninger(),
            Ytelse.ENSLIG_FORSORGER to fellesUavklarteDekninger()
        )

        private fun dekningForSykepenger(): List<Dekning> =
            listOf(
                FULL,
                FOLKETRYGDLOVEN2_6,
                FOLKETRYGDLOVEN2_7_3A,
                FOLKETRYGDLOVEN2_9_2_1A,
                FOLKETRYGDLOVEN2_9_2_1C
            )

        private fun dekningForDagpenger(): List<Dekning> =
            listOf(
                FULL,
                FOLKETRYGDLOVEN2_7_3A,
                FOLKETRYGDLOVEN2_7A_2A,
                FOLKETRYGDLOVEN2_9_1B,
                FOLKETRYGDLOVEN2_9_1C,
                FOLKETRYGDLOVEN2_9_2_1A,
                FOLKETRYGDLOVEN2_9_2_1C
            )

        private fun dekningForEnsligForsorger(): List<Dekning> =
            listOf(
                FULL,
                FOLKETRYGDLOVEN2_7_3A,
                FOLKETRYGDLOVEN2_7A_2A,
                FOLKETRYGDLOVEN2_9_1B,
                FOLKETRYGDLOVEN2_9_1C,
                FOLKETRYGDLOVEN2_9_2_1A,
                FOLKETRYGDLOVEN2_9_2_1C
            )

        private fun uavklarteDekningerForSykepenger(): List<Dekning> =
            listOf(FOLKETRYGDLOVEN2_7A_2A, FOLKETRYGDLOVEN2_7A_2B) + fellesUavklarteDekninger()

        fun fellesUavklarteDekninger(): List<Dekning> =
            listOf(
                IHT_AVTALE,
                IKKEPENDEL,
                PENDEL,
                OPPHOR
            )

        fun Dekning.uavklartForYtelse(ytelse: Ytelse): Boolean = uavklarteDekningerForYtelseMap[ytelse]?.contains(this) ?: false

        fun Dekning.gjelderForYtelse(ytelse: Ytelse): Boolean = dekningForYtelseMap[ytelse]?.contains(this) ?: false

        fun from(kodeverdi: String?): Dekning? = values().firstOrNull { it.dekningKodeverdi == kodeverdi }
    }
}
