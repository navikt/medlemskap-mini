package no.nav.medlemskap.regler.v1.registrerteOpplysninger

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.*

class HarBrukerRegistrerteOpplysningerRegel(
    ytelse: Ytelse,
    val regelMedl: Regel,
    val regelJoark: Regel,
    val regelGsak: Regel
) : BasisRegel(RegelId.REGEL_OPPLYSNINGER, ytelse) {
    override fun operasjon(): Resultat {
        return Regler.minstEnAvDisse(regelMedl, regelJoark, regelGsak)
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerRegistrerteOpplysningerRegel {
            return HarBrukerRegistrerteOpplysningerRegel(
                ytelse = datagrunnlag.ytelse,
                regelMedl = FinnesOpplysningerIMedlRegel.fraDatagrunnlag(datagrunnlag).regel,
                regelJoark = FinnesOpplysningerIJoarkRegel.fraDatagrunnlag(datagrunnlag).regel,
                regelGsak = FinnesOpplysningerIGosysRegel.fraDatagrunnlag(datagrunnlag).regel
            )
        }
    }
}
