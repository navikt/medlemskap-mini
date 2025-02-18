package no.nav.medlemskap.common.exceptions

import no.nav.medlemskap.domene.Ytelse

class UdiHentPersonstatusFaultException(val ytelse: Ytelse) : Exception()
