package no.nav.medlemskap.common.exceptions

import io.ktor.server.plugins.*
import no.nav.medlemskap.domene.Ytelse

class UgyldigRequestException(message: String, val ytelse: Ytelse) : BadRequestException(message)
