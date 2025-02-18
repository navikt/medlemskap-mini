package no.nav.medlemskap.cucumber.steps.udi

import no.nav.medlemskap.domene.EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType.ARBEID
import no.nav.medlemskap.domene.EOSellerEFTAGrunnlagskategoriOppholdsrettType.VARIG
import no.nav.medlemskap.domene.EOSellerEFTAOpphold
import no.nav.medlemskap.domene.EOSellerEFTAOppholdType.EOS_ELLER_EFTA_OPPHOLDSTILLATELSE
import no.nav.medlemskap.domene.Periode

class EOSellerEFTAOppholdBuilder {
    var periodeBuilder: PeriodeBuilder = PeriodeBuilder()
    var periode: Periode? = periodeBuilder.build()
    var EOSellerEFTAOppholdType = EOS_ELLER_EFTA_OPPHOLDSTILLATELSE
    var EOSellerEFTAGrunnlagskategoriOppholdsrettType = VARIG
    var EOSellerEFTAGrunnlagskategoriOppholdstillatelseType = ARBEID

    fun build(): EOSellerEFTAOpphold {
        return EOSellerEFTAOpphold(
            periode,
            EOSellerEFTAOppholdType,
            EOSellerEFTAGrunnlagskategoriOppholdsrettType,
            EOSellerEFTAGrunnlagskategoriOppholdstillatelseType
        )
    }
}
