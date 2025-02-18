package no.nav.medlemskap.services.pdl.mapper

import no.nav.medlemskap.clients.pdl.generated.hentperson.Person
import no.nav.medlemskap.domene.barn.PersonhistorikkBarn
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.ForelderBarnRelasjon
import no.nav.medlemskap.services.pdl.mapper.PdlMapper.mapBostedsadresser
import no.nav.medlemskap.services.pdl.mapper.PdlMapper.mapKontaktadresser

object PdlMapperBarn {

    fun mapPersonhistorikkTilBarn(fnr: String, barn: Person): PersonhistorikkBarn {
        val oppholdsadresse = PdlMapper.mapOppholdsadresser(barn.oppholdsadresse)
        val bostedsadresser: List<Adresse> = mapBostedsadresser(barn.bostedsadresse)
        val kontaktadresser: List<Adresse> = mapKontaktadresser(barn.kontaktadresse)
        val forelderBarnRelasjoner: List<ForelderBarnRelasjon> = barn.forelderBarnRelasjon.map { PdlMapper.mapFamilierelasjon(it) }

        return PersonhistorikkBarn(
            ident = fnr,
            forelderBarnRelasjon = forelderBarnRelasjoner,
            oppholdsadresser = oppholdsadresse,
            bostedsadresser = bostedsadresser,
            kontaktadresser = kontaktadresser
        )
    }
}
