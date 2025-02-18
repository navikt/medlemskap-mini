package no.nav.medlemskap.domene

import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.regler.common.Resultat
import java.time.LocalDateTime

data class Response(
    val vurderingsID: String?,
    val tidspunkt: LocalDateTime,
    val versjonTjeneste: String,
    val versjonRegler: String,
    val kanal: String,
    val datagrunnlag: Datagrunnlag,
    val resultat: Resultat
) {

    companion object {
        fun fraJson(responseJson: String): Response {
            return objectMapper.readValue(responseJson, Response::class.java)
        }
    }
}
