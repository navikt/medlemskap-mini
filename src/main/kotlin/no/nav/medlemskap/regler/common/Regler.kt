package no.nav.medlemskap.regler.common

import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.Regelflyt.Companion.medlemskonklusjonUavklart
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.common.Resultat.Companion.uavklart
import no.nav.medlemskap.regler.common.Svar.JA
import no.nav.medlemskap.regler.common.Svar.NEI
import no.nav.medlemskap.regler.v1.RegelFactory

abstract class Regler(
    val ytelse: Ytelse,
    val regelFactory: RegelFactory,
    val overstyrteRegler: Map<RegelId, Svar>
) {

    abstract fun hentHovedflyt(): Regelflyt

    fun kjørHovedflyt(): Resultat {
        return kjørRegelflyt(hentHovedflyt())
    }

    protected fun kjørRegelflyt(regelflyt: Regelflyt): Resultat {
        return regelflyt.utfør()
    }

    protected fun lagRegelflyt(
        regel: Regel,
        hvisJa: Regelflyt? = null,
        hvisNei: Regelflyt? = null,
        hvisUavklart: Regelflyt = medlemskonklusjonUavklart(ytelse),
        årsak: Årsak? = null
    ): Regelflyt {
        return Regelflyt(
            regel = regel,
            hvisJa = hvisJa,
            hvisNei = hvisNei,
            hvisUavklart = hvisUavklart,
            overstyrteRegler = overstyrteRegler,
            årsak = årsak
        )
    }

    protected fun hentRegel(regelId: RegelId): Regel {
        return regelFactory.create(regelId)
    }

    companion object {
        private fun utledResultat(resultater: Map<RegelId, Resultat>): Resultat {

            return when {
                resultater[RegelId.REGEL_A]?.svar == JA && resultater[RegelId.REGEL_B]?.svar == NEI -> ja(RegelId.REGEL_OPPLYSNINGER)
                resultater.values.any { it.svar == JA } -> uavklart(RegelId.REGEL_OPPLYSNINGER, årsak = Årsak.fraResultat(resultater.values.first { it.svar == JA }))
                else -> nei(RegelId.REGEL_OPPLYSNINGER)
            }
        }

        fun minstEnAvDisse(vararg regler: Regel): Resultat {
            val delresultatMap = regler.map { it.regelId to it.utfør() }.toMap()

            return utledResultat(delresultatMap).copy(
                delresultat = delresultatMap.values.toList()
            )
        }
    }
}
