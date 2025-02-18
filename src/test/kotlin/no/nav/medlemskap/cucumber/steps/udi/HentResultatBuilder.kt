package no.nav.medlemskap.cucumber.steps.udi

import no.udi.mt_1067_nav_data.v1.*

class HentResultatBuilder {

    val hentPersonstatusResultatMedArbeidsadgang: HentPersonstatusResultat =
        HentPersonstatusResultat()
            .withArbeidsadgang(Arbeidsadgang())

    val hentPersonstatusResultatMedGjeldendeOppholdsstatusUavklart: HentPersonstatusResultat =
        HentPersonstatusResultat().withGjeldendeOppholdsstatus(GjeldendeOppholdsstatus())

    val hentPersonstatusResultatMedOppholdsstatus: HentPersonstatusResultat =
        HentPersonstatusResultat()
            .withGjeldendeOppholdsstatus(
                GjeldendeOppholdsstatus().withOppholdstillatelseEllerOppholdsPaSammeVilkar(
                    OppholdstillatelseEllerOppholdsPaSammeVilkar()
                )
            )

    val hentPersonstatusResultatIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum: HentPersonstatusResultat =
        HentPersonstatusResultat()
            .withGjeldendeOppholdsstatus(
                GjeldendeOppholdsstatus()
                    .withIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
                        IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum()
                            .withAvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak(
                                AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak()
                            )
                            .withUtvistMedInnreiseForbud(UtvistMedInnreiseForbud())
                            .withOvrigIkkeOpphold(OvrigIkkeOpphold())
                    )
            )

    val hentPersonstatusResultatEOSellerEFTABeslutningOmOppholdsrett: HentPersonstatusResultat =
        HentPersonstatusResultat()
            .withGjeldendeOppholdsstatus(
                GjeldendeOppholdsstatus()
                    .withEOSellerEFTAOpphold(
                        EOSellerEFTAOpphold()
                            .withEOSellerEFTABeslutningOmOppholdsrett(EOSellerEFTABeslutningOmOppholdsrett())
                    )
            )

    val hentPersonstatusResultatEOSellerEFTAOppholdstillatelse: HentPersonstatusResultat =
        HentPersonstatusResultat()
            .withGjeldendeOppholdsstatus(
                GjeldendeOppholdsstatus()
                    .withEOSellerEFTAOpphold(
                        EOSellerEFTAOpphold()
                            .withEOSellerEFTAOppholdstillatelse(EOSellerEFTAOppholdstillatelse())
                    )
            )

    val hentPersonstatusResultatEOSellerEFTAVarigOppholdsrett: HentPersonstatusResultat =
        HentPersonstatusResultat()
            .withGjeldendeOppholdsstatus(
                GjeldendeOppholdsstatus()
                    .withEOSellerEFTAOpphold(
                        EOSellerEFTAOpphold()
                            .withEOSellerEFTAVedtakOmVarigOppholdsrett(EOSellerEFTAVedtakOmVarigOppholdsrett())
                    )
            )
}
