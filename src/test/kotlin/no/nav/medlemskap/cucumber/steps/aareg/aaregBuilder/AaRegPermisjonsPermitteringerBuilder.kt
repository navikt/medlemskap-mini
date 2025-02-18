package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegPermisjonPermittering
import no.nav.medlemskap.domene.arbeidsforhold.PermisjonPermitteringType

class AaRegPermisjonsPermitteringerBuilder {
    var periodeBuilder = PeriodeBuilder()
    var sporingsinformasjonBuilder = AaRegSporingsinformasjonBuilder()

    var periode = periodeBuilder.buildAaregPeriode()
    var permisjonsPermitteringId = String()
    var sporingsinformasjon = sporingsinformasjonBuilder.build()
    var type = PermisjonPermitteringType.PERMISJON.kodeverdi
    var varslingkode = String()
    var prosent = 1.0

    fun build(): AaRegPermisjonPermittering =
        AaRegPermisjonPermittering(
            periode = periode,
            permisjonPermitteringId = permisjonsPermitteringId,
            sporingsinformasjon = sporingsinformasjon,
            type = type,
            varslingskode = varslingkode,
            prosent = prosent
        )
}
