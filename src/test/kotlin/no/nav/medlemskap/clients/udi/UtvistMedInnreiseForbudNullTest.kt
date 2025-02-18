package no.nav.medlemskap.clients.udi

import no.nav.medlemskap.services.udi.UdiMapper
import no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus
import no.udi.mt_1067_nav_data.v1.HentPersonstatusResultat
import no.udi.mt_1067_nav_data.v1.IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class UtvistMedInnreiseForbudNullTest {
    @Test
    fun `utvistMedInnreiseForbud er null`() {
        val utvistMedInnreiseforbudNull =
            HentPersonstatusResultat()
                .withGjeldendeOppholdsstatus(
                    GjeldendeOppholdsstatus()
                        .withIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
                            IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum()
                        )
                )

        val mappetUtvistMedInnreiseForbud = UdiMapper.mapTilOppholdstillatelse(utvistMedInnreiseforbudNull)
        val utvistMedInnreiseForbud = mappetUtvistMedInnreiseForbud
            .gjeldendeOppholdsstatus
            ?.ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
            ?.utvistMedInnreiseForbud
            ?.innreiseForbud

        assertNull(utvistMedInnreiseForbud)
    }
}
