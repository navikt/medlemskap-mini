package no.nav.medlemskap.common.exceptions

import no.nav.medlemskap.domene.Ytelse

class GradertAdresseException(val system: String, val ytelse: Ytelse) : Exception()
