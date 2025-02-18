package no.nav.medlemskap.common.exceptions

import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.domene.Ytelse

data class GraphqlError(val error: Any, val system: String, val ytelse: Ytelse) : Exception() {
    fun errorAsJson() = objectMapper.writeValueAsString(error)
}
