package no.nav.medlemskap.services.pdl.mapper

import no.bekk.bekkopen.person.FodselsnummerValidator
import no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle
import no.nav.medlemskap.clients.pdl.generated.hentperson.ForelderBarnRelasjon
import no.nav.medlemskap.clients.pdl.generated.hentperson.Person
import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.ForelderBarnRelasjon.Companion.erBarnUnder25Aar
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.services.pdl.mapper.PdlMapper.mapBostedsadresser
import no.nav.medlemskap.services.pdl.mapper.PdlMapper.mapKontaktadresser
import java.time.LocalDate

object PdlMapperEktefelle {

    fun mapPersonhistorikkTilEktefelle(fnr: String, ektefelle: Person, førsteDatoForYtelse: LocalDate): PersonhistorikkEktefelle {
        val barn = mapFnrBarnTilBrukersEktefelle(ektefelle.forelderBarnRelasjon, førsteDatoForYtelse)
        val statsborgerskap: List<Statsborgerskap> = PdlMapper.mapStatsborgerskap(ektefelle.statsborgerskap)
        val oppholdsadresse = PdlMapper.mapOppholdsadresser(ektefelle.oppholdsadresse)
        val bostedsadresser: List<Adresse> = mapBostedsadresser(ektefelle.bostedsadresse)
        val kontaktadresser: List<Adresse> = mapKontaktadresser(ektefelle.kontaktadresse)

        return PersonhistorikkEktefelle(
            ident = fnr,
            barn = barn,
            statsborgerskap = statsborgerskap,
            oppholdsadresser = oppholdsadresse,
            bostedsadresser = bostedsadresser,
            kontaktadresser = kontaktadresser
        )
    }

    private fun mapFnrBarnTilBrukersEktefelle(familierelasjoner: List<ForelderBarnRelasjon>, førsteDatoForYtelse: LocalDate): List<String> {
        return familierelasjoner
            .filter { it.relatertPersonsRolle == ForelderBarnRelasjonRolle.BARN }
            .filter { FodselsnummerValidator.isValid(it.relatertPersonsIdent) }
            .filter { it.relatertPersonsIdent != null && it.relatertPersonsIdent!!.erBarnUnder25Aar(førsteDatoForYtelse) }
            .map { it.relatertPersonsIdent!! }
    }
}
