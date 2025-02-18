package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei

class HarIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
    ytelse: Ytelse,
    private val oppholdstillatelse: Oppholdstillatelse?,
    regelId: RegelId = RegelId.REGEL_23
) : BasisRegel(regelId, ytelse) {

    override fun operasjon(): Resultat {
        if (oppholdstillatelse?.gjeldendeOppholdsstatus?.ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum != null) {
            return ja(regelId)
        }

        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum {
            return HarIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse
            )
        }
    }
}
