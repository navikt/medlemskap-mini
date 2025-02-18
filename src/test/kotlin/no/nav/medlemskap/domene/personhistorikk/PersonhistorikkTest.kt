package no.nav.medlemskap.domene.personhistorikk

import no.nav.medlemskap.cucumber.steps.pdl.PersonhistorikkBuilder
import no.nav.medlemskap.domene.InputPeriode
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PersonhistorikkTest {

    @Test
    fun `Liste med dødsfall før periode gir false`() {

        val personhistorikkBuilder = PersonhistorikkBuilder()
        personhistorikkBuilder.doedsfall.add(doedsfallFoerInputPeriode)
        val kontrollperiode = InputPeriode(
            fom = LocalDate.of(2019, 1, 1),
            tom = LocalDate.of(2019, 12, 31)
        )
        assertFalse(personhistorikkBuilder.build().erBrukerDoedEtterPeriode(kontrollperiode))
    }

    @Test
    fun `Liste med dødsfall i periode gir false`() {

        val personhistorikkBuilder = PersonhistorikkBuilder()
        personhistorikkBuilder.doedsfall.add(doedsfallEtterInputPeriode)

        val kontrollperiode = InputPeriode(
            fom = LocalDate.of(2019, 1, 1),
            tom = LocalDate.of(2019, 12, 31)
        )

        assertTrue(personhistorikkBuilder.build().erBrukerDoedEtterPeriode(kontrollperiode))
    }

    @Test
    fun `Liste med dødsfall i etter gir true`() {

        val personhistorikkBuilder = PersonhistorikkBuilder()
        personhistorikkBuilder.doedsfall.add(doedsfallFoerInputPeriode)

        val kontrollperiode = InputPeriode(
            fom = LocalDate.of(2019, 1, 1),
            tom = LocalDate.of(2019, 12, 31)
        )

        assertFalse(personhistorikkBuilder.build().erBrukerDoedEtterPeriode(kontrollperiode))
    }

    @Test
    fun `Liste med dødsfall før OG etter  periode gir false`() {
        val personhistorikkBuilder = PersonhistorikkBuilder()
        personhistorikkBuilder.doedsfall.add(doedsfallFoerInputPeriode)
        personhistorikkBuilder.doedsfall.add(doedsfallEtterInputPeriode)

        val kontrollperiode = InputPeriode(
            fom = LocalDate.of(2019, 1, 1),
            tom = LocalDate.of(2019, 12, 31)
        )

        assertFalse(personhistorikkBuilder.build().erBrukerDoedEtterPeriode(kontrollperiode))
    }

    val doedsfallEtterInputPeriode = LocalDate.of(2020, 1, 1)

    val doedsfallFoerInputPeriode = LocalDate.of(2018, 1, 1)
}
