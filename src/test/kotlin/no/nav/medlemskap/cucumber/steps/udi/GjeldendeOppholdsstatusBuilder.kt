package no.nav.medlemskap.cucumber.steps.udi

import no.nav.medlemskap.domene.GjeldendeOppholdsstatus

class GjeldendeOppholdsstatusBuilder {
    var oppholdstillatelseBuilder = OppholdstillatelsePåSammeVilkårBuilder()
    var eosEllerEFTAOppholdBuilder = EOSellerEFTAOppholdBuilder()
    var oppholdstillatelsePaSammeVilkar = oppholdstillatelseBuilder.build()
    var eosellerEFTAOpphold = eosEllerEFTAOppholdBuilder.build()
    var uavklart = null
    var ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null

    fun build(): GjeldendeOppholdsstatus {
        return GjeldendeOppholdsstatus(
            eosellerEFTAOpphold = eosellerEFTAOpphold,
            oppholdstillatelsePaSammeVilkar = oppholdstillatelsePaSammeVilkar,
            uavklart = uavklart,
            ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
        )
    }
}
