package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegAntallTimerForTimeloennet
import no.nav.medlemskap.clients.aareg.AaRegArbeidsavtale
import no.nav.medlemskap.clients.aareg.AaRegArbeidsforhold
import java.time.LocalDateTime

class AaRegArbeidsforholdBuilder {
    var ansettelesperiodeBuilder = AaRegAnsettelsesPeriodeBuilder()
    var antallTimerForTimeloennetBuilder = AaRegAntallTimerForLoennetBuilder()
    var arbeidsavtaleBuilder = AaRegArbeidsavtaleBuilder()
    var arbeidsgiverBuilder = AaRegArbeidsgiverBuilder()
    var ansettelsesPeriodeBuilder = AaRegAnsettelsesPeriodeBuilder()
    var arbeidstakerBuilder = AaRegPersonBuilder()
    var opplysningspliktigBuilder = AaregOpplysningspliktigArbeidsgiverBuilder()
    var permisjonsPermitteringerBuilder = AaRegPermisjonsPermitteringerBuilder()
    var sporingsinformasjonBuilder = AaRegSporingsinformasjonBuilder()
    var utenlandsoppholdBuilder = AaRegUtenlandsoppholdBuilder()

    var antallTimerForLoennet = mutableListOf<AaRegAntallTimerForTimeloennet>(antallTimerForTimeloennetBuilder.build())
    var arbeidsavtaler = mutableListOf<AaRegArbeidsavtale>(arbeidsavtaleBuilder.build())
    var arbeidsforholdId = String()
    var arbeidsgiver = arbeidsgiverBuilder.build()
    var arbeidsavtale = arbeidsavtaleBuilder.build()
    var arbeidstaker = arbeidstakerBuilder.build()
    var ansettelsesperiode = ansettelesperiodeBuilder.build()
    var innrapportertEtterAOrdningen = true
    var navArbeidsforholdId = 1
    var opplysningspliktig = opplysningspliktigBuilder.build()
    var permisjonPermitteringer = mutableListOf(permisjonsPermitteringerBuilder.build())
    var registrert = LocalDateTime.now()
    var sistBekreftet = LocalDateTime.now()
    var sporingsinformasjon = sporingsinformasjonBuilder.build()
    var type = "ordinaertArbeidsforhold"
    var utenlandsopphold = mutableListOf(utenlandsoppholdBuilder.build())

    fun build(): AaRegArbeidsforhold {

        return AaRegArbeidsforhold(
            ansettelsesperiode = ansettelsesperiode,
            antallTimerForTimeloennet = antallTimerForLoennet,
            arbeidsavtaler = arbeidsavtaler,
            arbeidsforholdId = arbeidsforholdId,
            arbeidsgiver = arbeidsgiver,
            arbeidstaker = arbeidstaker,
            innrapportertEtterAOrdningen = innrapportertEtterAOrdningen,
            navArbeidsforholdId = navArbeidsforholdId,
            opplysningspliktig = opplysningspliktig,
            permisjonPermitteringer = permisjonPermitteringer,
            registrert = registrert,
            sistBekreftet = sistBekreftet,
            sporingsinformasjon = sporingsinformasjon,
            type = type,
            utenlandsopphold = utenlandsopphold
        )
    }
}
