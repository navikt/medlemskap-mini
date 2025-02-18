package no.nav.medlemskap.cucumber.steps.udi

import no.nav.medlemskap.domene.Arbeidsadgang
import no.nav.medlemskap.domene.Oppholdstillatelse
import java.time.LocalDateTime

class OppholdstillatelseBuilder {
    var gjeldendeOppholdsstatusBuilder = GjeldendeOppholdsstatusBuilder()
    var uttrekkstidspunkt: LocalDateTime? = null
    var foresporselsfodselsnummer: String? = null
    var avgjoerelse: Boolean? = null
    var gjeldendeOppholdsstatus = gjeldendeOppholdsstatusBuilder.build()
    var arbeidsadgang: Arbeidsadgang? = null
    var uavklartFlyktningstatus: Boolean? = null
    var harFlyktningstatus: Boolean? = null

    fun fromOppholdstillatelse(oppholdstillatelse: Oppholdstillatelse) {
        uttrekkstidspunkt = oppholdstillatelse.uttrekkstidspunkt
        foresporselsfodselsnummer = oppholdstillatelse.foresporselsfodselsnummer
        avgjoerelse = oppholdstillatelse.avgjoerelse
        gjeldendeOppholdsstatus = oppholdstillatelse.gjeldendeOppholdsstatus!!
        arbeidsadgang = oppholdstillatelse.arbeidsadgang
        uavklartFlyktningstatus = oppholdstillatelse.uavklartFlyktningstatus
        harFlyktningstatus = oppholdstillatelse.harFlyktningstatus
    }

    fun build(): Oppholdstillatelse {
        return Oppholdstillatelse(
            uttrekkstidspunkt,
            foresporselsfodselsnummer,
            avgjoerelse,
            gjeldendeOppholdsstatus,
            arbeidsadgang,
            uavklartFlyktningstatus,
            harFlyktningstatus
        )
    }
}
