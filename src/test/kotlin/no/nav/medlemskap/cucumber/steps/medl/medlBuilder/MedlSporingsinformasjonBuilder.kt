package no.nav.medlemskap.cucumber.steps.medl.medlBuilder

import no.nav.medlemskap.clients.medl.MedlSporingsinformasjon
import java.time.LocalDate
import java.time.LocalDateTime

class MedlSporingsinformasjonBuilder {

    var besluttet = LocalDate.MIN
    var kilde = String()
    var kildedokument = String()
    var opprettet = LocalDateTime.now()
    var opprettetAv = String()
    var registrert = LocalDate.now()
    var sistEndret = LocalDateTime.MAX
    var sistEndretAv = String()
    var versjon = String()

    fun build(): MedlSporingsinformasjon {
        return MedlSporingsinformasjon(
            besluttet = besluttet,
            kilde = kilde,
            kildedokument = kildedokument,
            opprettet = opprettet,
            opprettetAv = opprettetAv,
            registrert = registrert,
            sistEndret = sistEndret,
            sistEndretAv = sistEndretAv,
            versjon = versjon
        )
    }
}
