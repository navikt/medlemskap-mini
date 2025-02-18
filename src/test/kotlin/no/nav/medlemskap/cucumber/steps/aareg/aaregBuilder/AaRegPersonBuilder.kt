package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegPerson
import no.nav.medlemskap.clients.aareg.AaRegPersonType

class AaRegPersonBuilder {
    var offentligIdent = String()
    var type = AaRegPersonType.Person
    var aktoerId = String()

    fun build(): AaRegPerson =
        AaRegPerson(
            offentligIdent = offentligIdent,
            type = type,
            aktoerId = aktoerId
        )
}
