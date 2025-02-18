package no.nav.medlemskap.common.exceptions

import no.nav.medlemskap.domene.Ytelse

class IdenterIkkeFunnet(val ytelse: Ytelse) : Exception()
