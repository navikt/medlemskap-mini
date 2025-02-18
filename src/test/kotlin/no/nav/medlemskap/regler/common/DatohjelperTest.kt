package no.nav.medlemskap.regler.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import no.nav.medlemskap.domene.Periode
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DatohjelperTest {

    @Test
    fun `to perioder med datoer etter hverandre skal være connected`() {

        val nå = LocalDate.now()
        val toDagerSiden: LocalDate = nå.minusDays(2)
        val enDagSiden: LocalDate = nå.minusDays(1)
        val idag: LocalDate = nå
        val imorgen: LocalDate = nå.plusDays(1)

        val førstePeriode = Periode(toDagerSiden, enDagSiden)
        val andrePeriode = Periode(idag, imorgen)

        assertThat(førstePeriode.interval().isConnected(andrePeriode.interval())).isTrue()
    }

    @Test
    fun `en periode contains sin egen startdato`() {

        val nå = LocalDate.now()
        val toDagerSiden: LocalDate = nå.minusDays(2)
        val enDagSiden: LocalDate = nå.minusDays(1)

        val periode = Periode(toDagerSiden, enDagSiden)

        assertThat(periode.interval().contains(periode.fomNotNull().startOfDayInstant())).isTrue()
        assertThat(periode.interval().contains(toDagerSiden.startOfDayInstant())).isTrue()
    }

    @Test
    fun `en periode contains sin egen sluttdato`() {

        val nå = LocalDate.now()
        val toDagerSiden: LocalDate = nå.minusDays(2)
        val enDagSiden: LocalDate = nå.minusDays(1)

        val periode = Periode(toDagerSiden, enDagSiden)

        assertThat(periode.interval().contains(periode.tomNotNull().startOfDayInstant())).isTrue()
        assertThat(periode.interval().contains(enDagSiden.startOfDayInstant())).isTrue()
    }

    @Test
    fun `en periode med tom fraogmed contains sin egen startdato`() {

        val nå = LocalDate.now()
        val enDagSiden: LocalDate = nå.minusDays(1)

        val periode = Periode(null, enDagSiden)

        assertThat(periode.interval().contains(periode.fomNotNull().startOfDayInstant())).isTrue()
    }

    @Test
    fun `en periode med tom tilogmed contains sin egen sluttdato`() {

        val nå = LocalDate.now()
        val toDagerSiden: LocalDate = nå.minusDays(2)

        val periode = Periode(toDagerSiden, null)

        assertThat(periode.interval().contains(periode.tomNotNull().startOfDayInstant())).isTrue()
    }

    @Test
    fun `en periode med tom tilogmed er unbounded`() {

        val nå = LocalDate.now()
        val toDagerSiden: LocalDate = nå.minusDays(2)

        val periode = Periode(toDagerSiden, null)

        assertThat(periode.interval().isUnboundedEnd).isTrue()
    }

    @Test
    fun parseIsoDate() {
        assertThat(Datohjelper.parseIsoDato(null)).isNull()

        assertThat(Datohjelper.parseIsoDato("2008-12-11T12:03:04")).isEqualTo(LocalDate.of(2008, 12, 11))

        assertThat(Datohjelper.parseIsoDato("2008-12-11")).isEqualTo(LocalDate.of(2008, 12, 11))
    }

    @Test
    fun parseDato() {
        assertThat(Datohjelper.parseDato("01.02.2008")).isEqualTo(LocalDate.of(2008, 2, 1))
    }
}
