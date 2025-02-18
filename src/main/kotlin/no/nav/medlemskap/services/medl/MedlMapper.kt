package no.nav.medlemskap.services.medl

import no.nav.medlemskap.clients.medl.MedlMedlemskapsunntak
import no.nav.medlemskap.domene.Lovvalg
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.PeriodeStatus

fun mapMedlemskapResultat(medlemskapsunntak: List<MedlMedlemskapsunntak>): List<Medlemskap> {
    return medlemskapsunntak.map {
        Medlemskap(
            it.dekning,
            it.fraOgMed,
            it.tilOgMed,
            it.medlem,
            Lovvalg.valueOf(it.lovvalg),
            it.lovvalgsland,
            PeriodeStatus.valueOf(it.status),
            it.grunnlag
        )
    }
}
