package no.nav.medlemskap.domene

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Kontrollperiode(
    val fom: LocalDate,
    val tom: LocalDate
) {
    val periode = Periode(fom, tom)
    val antallDager = fom.until(tom, ChronoUnit.DAYS).toDouble() + 1

    val isReferansePeriode = fom.until(tom, ChronoUnit.DAYS).toInt() == 28

    companion object {
        fun kontrollperiodeForSykepenger(startDatoForYtelse: LocalDate): Kontrollperiode =
            Kontrollperiode(
                fom = startDatoForYtelse.minusDays(28),
                tom = startDatoForYtelse
            )
        fun kontrollperiodeForBarneBriller(startDatoForYtelse: LocalDate): Kontrollperiode =
            Kontrollperiode(
                fom = startDatoForYtelse.minusDays(0),
                tom = startDatoForYtelse
            )

        fun kontrollPeriodeForPersonhistorikk(startDatoForYtelse: LocalDate): Kontrollperiode {
            return Kontrollperiode(
                fom = startDatoForYtelse.minusMonths(12),
                tom = startDatoForYtelse
            )
        }

        fun kontrollPeriodeForMedl(startDatoForYtelse: LocalDate): Kontrollperiode {
            return Kontrollperiode(
                fom = startDatoForYtelse.minusMonths(12),
                tom = startDatoForYtelse
            )
        }

        fun kontrollPeriodeForArbeidsforhold(startDatoForYtelse: LocalDate): Kontrollperiode {
            return Kontrollperiode(
                fom = startDatoForYtelse.minusMonths(12),
                tom = startDatoForYtelse
            )
        }

        fun kontrollPeriodeForOppholdstillatelse(startDatoForYtelse: LocalDate): Kontrollperiode {
            return Kontrollperiode(
                fom = startDatoForYtelse.minusMonths(12),
                tom = startDatoForYtelse.plusMonths(2)
            )
        }

        fun startDatoForYtelse(inputPeriode: InputPeriode, førsteDagForYtelse: LocalDate?): LocalDate {
            if (førsteDagForYtelse != null) {
                return førsteDagForYtelse
            } else {
                return inputPeriode.fom.minusDays(1)
            }
        }


    }
}
