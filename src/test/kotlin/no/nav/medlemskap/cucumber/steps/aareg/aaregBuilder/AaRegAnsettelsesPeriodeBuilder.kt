package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegAnsettelsesperiode

class AaRegAnsettelsesPeriodeBuilder {
    var bruksperiodeBuilder = AaRegBruksperiodeBuilder()
    var periodeBuilder = PeriodeBuilder()
    var sporingsinformasjonBuilder = AaRegSporingsinformasjonBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var periode = periodeBuilder.buildAaregPeriode()
    var varslingskode = String()
    var sporingsinformasjon = sporingsinformasjonBuilder.build()

    fun build(): AaRegAnsettelsesperiode =
        AaRegAnsettelsesperiode(
            bruksperiode = bruksperiode,
            periode = periode,
            varslingskode = varslingskode,
            sporingsinformasjon = sporingsinformasjon
        )
}
