package no.nav.medlemskap.regler.common

import no.nav.medlemskap.domene.Ytelse

abstract class BasisRegel(val regelId: RegelId, val ytelse: Ytelse) {
    val regel = Regel(regelId, ytelse, { operasjon() })

    fun utfør(): Resultat {
        return regel.utfør()
    }

    abstract fun operasjon(): Resultat
}
