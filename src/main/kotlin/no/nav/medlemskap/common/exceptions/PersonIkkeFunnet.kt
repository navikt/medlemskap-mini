package no.nav.medlemskap.common.exceptions

import no.nav.medlemskap.domene.Ytelse

class PersonIkkeFunnet(val system: String, val ytelse: Ytelse) : Exception()
