package no.nav.medlemskap.regler.common

import no.nav.medlemskap.common.regelCounter
import no.nav.medlemskap.common.regelInfluxCounter
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.Ytelse.Companion.name
import no.nav.medlemskap.regler.common.Konklusjonstype.*
import no.nav.medlemskap.regler.common.RegelId.Companion.metricName
import no.nav.medlemskap.regler.common.RegelId.REGEL_MEDLEM_KONKLUSJON

data class Regel(
    val regelId: RegelId,
    val ytelse: Ytelse,
    val operasjon: () -> Resultat
) {
    fun utfør(): Resultat = operasjon.invoke().apply {
        regelCounter(this@Regel.regelId.metricName(), this.svar.name, ytelse.name()).increment()
        regelInfluxCounter(this@Regel.regelId.identifikator, this.svar.name, ytelse.name()).increment()
    }.copy(
        regelId = regelId
    )

    companion object {
        fun regelUavklart(ytelse: Ytelse, regelId: RegelId, konklusjonstype: Konklusjonstype = REGEL) = Regel(
            regelId = regelId,
            ytelse = ytelse,
            operasjon = { Resultat.uavklart(regelId, konklusjonstype) }
        )

        fun regelJa(ytelse: Ytelse, regelId: RegelId, konklusjonstype: Konklusjonstype = REGEL) = Regel(
            regelId = regelId,
            ytelse = ytelse,
            operasjon = { Resultat.ja(regelId, konklusjonstype) }
        )

        fun regelNei(ytelse: Ytelse, regelId: RegelId, konklusjonstype: Konklusjonstype = REGEL) = Regel(
            regelId = regelId,
            ytelse = ytelse,
            operasjon = { Resultat.nei(regelId, konklusjonstype) }
        )

        fun uavklartKonklusjon(ytelse: Ytelse, regelId: RegelId = REGEL_MEDLEM_KONKLUSJON) = Regel(
            regelId = regelId,
            ytelse = ytelse,
            operasjon = { Resultat.uavklart(regelId, MEDLEM) }
        )

        fun jaKonklusjon(ytelse: Ytelse, regelId: RegelId = REGEL_MEDLEM_KONKLUSJON) = Regel(
            regelId = regelId,
            ytelse = ytelse,
            operasjon = { Resultat.ja(regelId, MEDLEM) }
        )

        fun neiKonklusjon(ytelse: Ytelse, regelId: RegelId = REGEL_MEDLEM_KONKLUSJON) = Regel(
            regelId = regelId,
            ytelse = ytelse,
            operasjon = { Resultat.nei(regelId, MEDLEM) }
        )
    }
}
