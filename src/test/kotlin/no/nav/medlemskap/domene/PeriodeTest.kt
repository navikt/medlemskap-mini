package no.nav.medlemskap.domene

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class PeriodeTest {

    @Test
    fun periode_er_gyldig_hvis_fom_er_før_tom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        assertTrue(periode.erGyldigPeriode())
    }

    @Test
    fun periode_er_ugyldig_hvis_tom_er_før_fom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 10), tom = LocalDate.of(2018, 2, 1))
        assertFalse(periode.erGyldigPeriode())
    }

    @Test
    fun periode_er_gyldig_hvis_fom_er_lik_tom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 10), tom = LocalDate.of(2018, 2, 10))
        assertTrue(periode.erGyldigPeriode())
    }

    @Test
    fun dato_overlapper_hvis_dato_er_lik_fom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))

        val dato = LocalDate.of(2018, 2, 1)
        assertTrue(periode.overlapper(dato))
    }

    @Test
    fun dato_overlapper_hvis_dato_er_lik_tom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))

        val dato = LocalDate.of(2018, 2, 10)
        assertTrue(periode.overlapper(dato))
    }

    @Test
    fun dato_overlapper_ikke_hvis_dato_er_etter_tom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))

        val dato = LocalDate.of(2018, 2, 11)
        assertFalse(periode.overlapper(dato))
    }

    @Test
    fun dato_overlapper_ikke_hvis_dato_er_før_fom() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))

        val dato = LocalDate.of(2018, 1, 28)
        assertFalse(periode.overlapper(dato))
    }

    @Test
    fun periode_overlapper_hvis_periodene_er_like() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        val annenPeriode = Periode(fom = periode.fom, tom = periode.tom)
        assertTrue(periode.overlapper(annenPeriode))
    }

    @Test
    fun periode_overlapper_hvis_periodene_delvis_overlapper() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        val annenPeriode = Periode(fom = periode.fomNotNull().plusDays(5), tom = periode.tom)
        assertTrue(periode.overlapper(annenPeriode))
    }

    @Test
    fun periode_overlapper_ikke_periodene_ikke_overlapper() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        val annenPeriode = Periode(fom = periode.fomNotNull().minusYears(1), tom = periode.fomNotNull().minusDays(1))
        assertFalse(periode.overlapper(annenPeriode))
    }

    @Test
    fun encloses_hvis_periodene_er_like() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        val annenPeriode = Periode(fom = periode.fom, tom = periode.tom)
        assertTrue(periode.overlapper(annenPeriode))
    }

    @Test
    fun encloses_hvis_annen_periode_er_inneholdt_i_periode() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        val annenPeriode = Periode(fom = periode.fomNotNull().plusDays(1), tom = periode.tomNotNull().minusDays(1))
        assertTrue(periode.encloses(annenPeriode))
    }

    @Test
    fun encloses_ikke_hvis_annen_periode_går_utenfor_periode() {
        val periode = Periode(fom = LocalDate.of(2018, 2, 1), tom = LocalDate.of(2018, 2, 10))
        val annenPeriode = Periode(fom = periode.fomNotNull().minusDays(5), tom = periode.tomNotNull())
        assertFalse(periode.encloses(annenPeriode))
    }
}
