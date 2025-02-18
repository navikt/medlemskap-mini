package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei

class HarBrukerOppholdPåSammeVilkårFlagg(
    ytelse: Ytelse,
    private val oppholdstillatelse: Oppholdstillatelse?,
    regelId: RegelId = RegelId.REGEL_19_8
) : BasisRegel(regelId, ytelse) {

    override fun operasjon(): Resultat {
        if (oppholdstillatelse?.gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.soknadIkkeAvgjort == true) {
            return ja(regelId)
        }

        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerOppholdPåSammeVilkårFlagg {
            return HarBrukerOppholdPåSammeVilkårFlagg(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse
            )
        }
    }
}
