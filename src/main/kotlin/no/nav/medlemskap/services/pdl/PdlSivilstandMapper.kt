package no.nav.medlemskap.services.pdl

import no.nav.medlemskap.common.exceptions.DetteSkalAldriSkje
import no.nav.medlemskap.domene.personhistorikk.Sivilstand
import no.nav.medlemskap.domene.personhistorikk.Sivilstandstype
import no.nav.medlemskap.regler.common.Datohjelper.parseIsoDato
import java.time.LocalDate

object PdlSivilstandMapper {

    fun mapSivilstander(pdlSivilstander: List<no.nav.medlemskap.clients.pdl.generated.hentperson.Sivilstand>): List<Sivilstand> {
        val sivilstander =
            pdlSivilstander.filter {
                it.type != no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.UGIFT && it.type != no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.UOPPGITT
            }.sortedBy {
                parseIsoDato(it.gyldigFraOgMed) ?: LocalDate.MIN
            }

        if (sivilstander.size < 2) {
            return sivilstander.map {
                mapSivilstand(it)
            }
        }

        return sivilstander
            .zipWithNext { sivilstand, neste ->
                mapSivilstand(sivilstand, parseIsoDato(neste.gyldigFraOgMed)?.minusDays(1))
            }.plus(mapSivilstand(sivilstander.last()))
    }

    private fun mapSivilstand(sivilstand: no.nav.medlemskap.clients.pdl.generated.hentperson.Sivilstand, gyldigTilOgMed: LocalDate? = null): Sivilstand {
        val gyldigFraOgMed = if (sivilstand.gyldigFraOgMed == null) {
            parseIsoDato(sivilstand.bekreftelsesdato)
        } else {
            parseIsoDato(sivilstand.gyldigFraOgMed)
        }

        return Sivilstand(
            type = mapSivilstandType(sivilstand.type),
            gyldigFraOgMed = gyldigFraOgMed,
            gyldigTilOgMed = gyldigTilOgMed,
            relatertVedSivilstand = sivilstand.relatertVedSivilstand
        )
    }

    private fun mapSivilstandType(type: no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype): Sivilstandstype {
        return when (type) {
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.GIFT -> Sivilstandstype.GIFT
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.ENKE_ELLER_ENKEMANN -> Sivilstandstype.ENKE_ELLER_ENKEMANN
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.SKILT -> Sivilstandstype.SKILT
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.SEPARERT -> Sivilstandstype.SEPARERT
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.REGISTRERT_PARTNER -> Sivilstandstype.REGISTRERT_PARTNER
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.SEPARERT_PARTNER -> Sivilstandstype.SEPARERT_PARTNER
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.SKILT_PARTNER -> Sivilstandstype.SKILT_PARTNER
            no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype.GJENLEVENDE_PARTNER -> Sivilstandstype.GJENLEVENDE_PARTNER
            else -> throw DetteSkalAldriSkje("Denne sivilstandstypen skal være filtrert bort")
        }
    }
}
