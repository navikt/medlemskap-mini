package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegSporingsinformasjon
import java.time.LocalDateTime

class AaRegSporingsinformasjonBuilder {

    var endretAv = String()
    var endretKilde = String()
    var endretKildeReferanse = String()
    var endretTidspunkt = LocalDateTime.now()
    var opprettetAv = String()
    var opprettetKilde = String()
    var opprettetKildereferanse = String()
    var opprettetTidspunkt = LocalDateTime.now()

    fun build(): AaRegSporingsinformasjon {
        return AaRegSporingsinformasjon(
            endretAv = endretAv,
            endretTidspunkt = endretTidspunkt,
            endretKildeReferanse = endretKildeReferanse,
            endretKilde = endretKilde,
            opprettetTidspunkt = opprettetTidspunkt,
            opprettetKildereferanse = opprettetKildereferanse,
            opprettetKilde = opprettetKilde,
            opprettetAv = opprettetAv
        )
    }
}
