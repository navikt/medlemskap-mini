package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.regler.common.Resultat

object ReglerService {

    fun kjørRegler(datagrunnlag: Datagrunnlag): Resultat {
        return Hovedregler(datagrunnlag).kjørHovedregler()
    }
}
