package no.nav.medlemskap.common.exceptions

import java.lang.Exception

class Sikkerhetsbegrensing(cause: Throwable, val system: String) : Exception(cause)
