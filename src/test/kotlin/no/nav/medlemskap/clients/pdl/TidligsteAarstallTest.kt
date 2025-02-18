package no.nav.medlemskap.clients.pdl

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class TidligsteAarstallTest {

    @Test
    fun `en sortert liste med int skal gi den laveste først`() {

        val liste = listOf(2020, 1980, 1990, 1985)

        assertThat(liste.sorted().last()).isEqualTo(2020)
    }
}
