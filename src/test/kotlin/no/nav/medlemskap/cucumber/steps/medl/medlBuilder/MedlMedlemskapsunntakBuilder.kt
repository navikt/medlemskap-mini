package no.nav.medlemskap.cucumber.steps.medl.medlBuilder

import no.nav.medlemskap.clients.medl.MedlMedlemskapsunntak
import java.time.LocalDate

class MedlMedlemskapsunntakBuilder {

    var medlSporingsinformasjonBuilder = MedlSporingsinformasjonBuilder()

    var dekning = String()
    var fraOgMed = LocalDate.now()
    var grunnlag = String()
    var helsedel = false
    var ident = String()
    var lovvalg = "ENDL"
    var lovvalgsland = String()
    var medlem = false
    var status = "GYLD"
    var statusaarsak = String()
    var tilOgMed = LocalDate.MAX
    var untakId = 1
    var sporingsinformasjon = medlSporingsinformasjonBuilder.build()
    var studieinformasjon = null

    fun build(): List<MedlMedlemskapsunntak> {
        return mutableListOf(
            MedlMedlemskapsunntak(
                dekning = dekning,
                fraOgMed = fraOgMed,
                grunnlag = grunnlag,
                helsedel = helsedel,
                ident = ident,
                lovvalg = lovvalg,
                lovvalgsland = lovvalgsland,
                medlem = medlem,
                status = status,
                statusaarsak = statusaarsak,
                tilOgMed = tilOgMed,
                unntakId = untakId,
                sporingsinformasjon = sporingsinformasjon,
                studieinformasjon = studieinformasjon
            )
        )
    }
}
