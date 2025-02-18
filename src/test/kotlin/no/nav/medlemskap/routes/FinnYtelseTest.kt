package no.nav.medlemskap.routes

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import no.nav.medlemskap.common.exceptions.KonsumentIkkeFunnet
import no.nav.medlemskap.domene.Ytelse
import org.junit.jupiter.api.Test

internal class FinnYtelseTest {

    @Test
    fun `ytelse skal hentes fra request når den er satt`() {

        val ytelseFraRequest = Ytelse.DAGPENGER
        val clientId = "a16f1075-4482-4577-baab-d2a7323655fa" // =SYKEPENGER

        val ytelse = finnYtelse(ytelseFraRequest, clientId)
        assertThat(ytelse).isEqualTo(Ytelse.DAGPENGER)
    }

    @Test
    fun `ytelse skal hentes fra clientId når den ikke er satt i request`() {

        val ytelseFraRequest = null
        val clientId = "a16f1075-4482-4577-baab-d2a7323655fa" // =SYKEPENGER

        val ytelse = finnYtelse(ytelseFraRequest, clientId)
        assertThat(ytelse).isEqualTo(Ytelse.SYKEPENGER)
    }

    @Test
    fun `exception skal kastes når request ikke er satt og clientId er feil`() {
        val ytelseFraRequest = null
        val clientId = "ikke-eksisterende"

        assertThat {
            finnYtelse(ytelseFraRequest, clientId)
        }.isFailure().isInstanceOf(KonsumentIkkeFunnet::class)
    }

    @Test
    fun `exception skal kastes når request ikke er satt og clientId er null`() {
        val ytelseFraRequest = null
        val clientId = null

        assertThat {
            finnYtelse(ytelseFraRequest, clientId)
        }.isFailure().isInstanceOf(KonsumentIkkeFunnet::class)
    }
}
