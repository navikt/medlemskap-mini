package no.nav.medlemskap.services.pdl.mapper

import com.neovisionaries.i18n.CountryCode
import mu.KotlinLogging
import no.nav.medlemskap.clients.pdl.generated.DateTime
import no.nav.medlemskap.clients.pdl.generated.hentperson.*
import no.nav.medlemskap.common.exceptions.DetteSkalAldriSkje
import no.nav.medlemskap.domene.personhistorikk.*
import no.nav.medlemskap.domene.personhistorikk.Endring
import no.nav.medlemskap.domene.personhistorikk.Folkeregistermetadata
import no.nav.medlemskap.domene.personhistorikk.ForelderBarnRelasjon
import no.nav.medlemskap.domene.personhistorikk.InnflyttingTilNorge
import no.nav.medlemskap.domene.personhistorikk.Metadata
import no.nav.medlemskap.domene.personhistorikk.Navn
import no.nav.medlemskap.domene.personhistorikk.Opphold
import no.nav.medlemskap.domene.personhistorikk.Sivilstand
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.domene.personhistorikk.UtflyttingFraNorge
import no.nav.medlemskap.regler.common.Datohjelper.parseIsoDato
import no.nav.medlemskap.services.pdl.PdlSivilstandMapper.mapSivilstander
import java.time.LocalDate
import java.time.LocalDateTime

object PdlMapper {

    private val logger = KotlinLogging.logger { }

    fun mapTilPersonHistorikkTilBruker(person: Person): Personhistorikk {

        val statsborgerskap: List<Statsborgerskap> = mapStatsborgerskap(person.statsborgerskap)
        val bostedsadresser: List<Adresse> = mapBostedsadresser(person.bostedsadresse)
        val kontaktadresser: List<Adresse> = mapKontaktadresser(person.kontaktadresse)
        val oppholdsadresser: List<Adresse> = mapOppholdsadresser(person.oppholdsadresse)
        val sivilstand: List<Sivilstand> = mapSivilstander(person.sivilstand)
        val forelderBarnRelasjoner: List<ForelderBarnRelasjon> = mapFamilierelasjoner(person.forelderBarnRelasjon)
        val doedsfall: List<LocalDate> = mapDoedsfall(person.doedsfall)
        val innflyttingTilNorge: List<InnflyttingTilNorge> = mapInnflyttingTilNorge(person.innflyttingTilNorge)
        val utflyttingFraNorge: List<UtflyttingFraNorge> = mapUtflyttingFraNorge(person.utflyttingFraNorge)
        val navn: List<Navn> = mapNavn(person.navn)
        val opphold: List<Opphold> = mapOppholdsTilatelser(person.opphold)

        return Personhistorikk(
            statsborgerskap = statsborgerskap,
            bostedsadresser = bostedsadresser,
            sivilstand = sivilstand,
            forelderBarnRelasjon = forelderBarnRelasjoner,
            kontaktadresser = kontaktadresser,
            oppholdsadresser = oppholdsadresser,
            doedsfall = doedsfall,
            innflyttingTilNorge = innflyttingTilNorge,
            utflyttingFraNorge = utflyttingFraNorge,
            navn = navn,
            oppholdstilatelser = opphold

        )
    }

    private fun mapOppholdsTilatelser(opphold: List<no.nav.medlemskap.clients.pdl.generated.hentperson.Opphold>): List<Opphold> {

        return opphold.map {
            Opphold(
                type = OppholdstillatelseType.valueOf(it.type.name),
                oppholdFra = mapDate(it.oppholdFra),
                oppholdTil = mapDate(it.oppholdTil),
                medtadata = OppholdMetadata(it.metadata.historisk, it.metadata.master, mapEnringer(it.metadata.endringer)),
                folkeregistermetadata = mapFolkeregistermetadata(it.folkeregistermetadata)
            )
        }
    }

    private fun mapDateTime(registrert: String): LocalDateTime {
        return LocalDateTime.parse(registrert)
    }

    private fun mapEnringer(endringer: List<no.nav.medlemskap.clients.pdl.generated.hentperson.Endring>): List<Endring> {
        return endringer.map {
            Endring(
                type = Endringstype.valueOf(it.type.name),
                kilde = it.kilde,
                registrert = mapDateTime(it.registrert),
                registrertAv = it.registrertAv,
                systemkilde = it.systemkilde
            )
        }
    }

    private fun mapDate(dato: String?): LocalDate? {
        if (dato != null) {
            return LocalDate.parse(dato)
        }
        return null
    }

    private fun mapInnflyttingTilNorge(innflyttingTilNorge: List<no.nav.medlemskap.clients.pdl.generated.hentperson.InnflyttingTilNorge>): List<InnflyttingTilNorge> {
        return innflyttingTilNorge.map {
            InnflyttingTilNorge(
                fraflyttingsland = it.fraflyttingsland,
                fraflyttingsstedIUtlandet = it.fraflyttingsstedIUtlandet,
                folkeregistermetadata = mapFolkeregistermetadata(it.folkeregistermetadata),
                metadata = Metadata(it.metadata.historisk)
            )
        }
    }

    private fun mapFolkeregistermetadata(folkeregistermetadata: no.nav.medlemskap.clients.pdl.generated.hentperson.Folkeregistermetadata?): Folkeregistermetadata {
        return Folkeregistermetadata(
            ajourholdstidspunkt = parseNullableDateTimeFraPDL(folkeregistermetadata?.ajourholdstidspunkt),
            gyldighetstidspunkt = parseNullableDateTimeFraPDL(folkeregistermetadata?.gyldighetstidspunkt),
            opphoerstidspunkt = parseNullableDateTimeFraPDL(folkeregistermetadata?.opphoerstidspunkt),
            aarsak = folkeregistermetadata?.aarsak
        )
    }

    private fun mapUtflyttingFraNorge(utflyttingFraNorge: List<no.nav.medlemskap.clients.pdl.generated.hentperson.UtflyttingFraNorge>): List<UtflyttingFraNorge> {
        return utflyttingFraNorge.map {
            UtflyttingFraNorge(
                tilflyttingsland = it.tilflyttingsland,
                tilflyttingsstedIUtlandet = it.tilflyttingsstedIUtlandet,
                utflyttingsDato = parseNullableDateStringFraPDL(it.utflyttingsdato),
                metadata = Metadata(it.metadata.historisk)
            )
        }
    }

    private fun mapNavn(navn: List<no.nav.medlemskap.clients.pdl.generated.hentperson.Navn>): List<Navn> {
        return mapPersonNavn(navn)
    }

    private fun mapPersonNavn(navn: List<no.nav.medlemskap.clients.pdl.generated.hentperson.Navn>): List<Navn> {
        return navn.map {
            Navn(
                fornavn = it.fornavn,
                mellomnavn = it.mellomnavn,
                etternavn = it.etternavn
            )
        }
    }

    private fun mapDoedsfall(doedsfall: List<Doedsfall>): List<LocalDate> {
        return doedsfall.mapNotNull {
            parseIsoDato(it.doedsdato)
        }
    }

    fun mapOppholdsadresser(pdlOppholdsadresser: List<Oppholdsadresse>): List<Adresse> {

        if (pdlOppholdsadresser.size < 2) {
            return pdlOppholdsadresser.map {
                mapOppholdsadresse(it)
            }
        }

        val sortertPdlOppholdsadresser = pdlOppholdsadresser.sortedBy { parseIsoDato(it.gyldigFraOgMed) ?: LocalDate.MIN }
        return sortertPdlOppholdsadresser.zipWithNext { oppholdsadresse, neste ->
            mapOppholdsadresse(oppholdsadresse, parseIsoDato(neste.gyldigFraOgMed)?.minusDays(1))
        }.plus(mapOppholdsadresse(sortertPdlOppholdsadresser.last()))
    }

    fun mapOppholdsadresse(oppholdsadresse: Oppholdsadresse, gyldigTilOgMed: LocalDate? = null): Adresse {
        return Adresse(
            fom = parseIsoDato(oppholdsadresse.gyldigFraOgMed),
            tom = parseIsoDato(oppholdsadresse.gyldigTilOgMed) ?: gyldigTilOgMed,
            landkode = mapLandkodeForOppholdsadresse(oppholdsadresse),
            historisk = oppholdsadresse.metadata.historisk
        )
    }

    fun mapFamilierelasjoner(pdlFamilierelasjoner: List<no.nav.medlemskap.clients.pdl.generated.hentperson.ForelderBarnRelasjon>): List<ForelderBarnRelasjon> {
        return pdlFamilierelasjoner.map { mapFamilierelasjon(it) }
    }

    fun mapFamilierelasjon(familierelasjon: no.nav.medlemskap.clients.pdl.generated.hentperson.ForelderBarnRelasjon): ForelderBarnRelasjon {
        return ForelderBarnRelasjon(
            relatertPersonsIdent = familierelasjon.relatertPersonsIdent,
            relatertPersonsRolle = mapFamileRelasjonsrolle(familierelasjon.relatertPersonsRolle)!!,
            minRolleForPerson = mapFamileRelasjonsrolle(familierelasjon.minRolleForPerson)
        )
    }

    fun mapOppholdsadresse(oppholdsadresse: Oppholdsadresse): Adresse {
        return Adresse(
            fom = parseIsoDato(oppholdsadresse.gyldigFraOgMed),
            tom = parseIsoDato(oppholdsadresse.gyldigTilOgMed),
            landkode = mapLandkodeForOppholdsadresse(oppholdsadresse),
            historisk = oppholdsadresse.metadata.historisk
        )
    }

    fun mapLandkodeForOppholdsadresse(oppholdsadresse: Oppholdsadresse): String {
        if (oppholdsadresse.utenlandskAdresse != null) {
            return mapLandkode(oppholdsadresse.utenlandskAdresse!!.landkode)
        }
        return CountryCode.NO.alpha3
    }

    fun mapLandkodeForKontaktadresse(kontaktadresse: Kontaktadresse): String {
        if (kontaktadresse.utenlandskAdresse != null) {
            return mapLandkode(kontaktadresse.utenlandskAdresse!!.landkode)
        }
        if (kontaktadresse.utenlandskAdresseIFrittFormat != null) {
            return mapLandkode(kontaktadresse.utenlandskAdresseIFrittFormat!!.landkode)
        }
        return CountryCode.NO.alpha3
    }

    fun mapBostedsadresser(pdlBostedsadresser: List<Bostedsadresse>): List<Adresse> {

        if (pdlBostedsadresser.size < 2) {
            return pdlBostedsadresser.map {
                mapBostedadresse(it)
            }
        }
        val sortertPdlBostedsadresser = pdlBostedsadresser.sortedBy { parseIsoDato(it.gyldigFraOgMed) ?: LocalDate.MIN }
        return sortertPdlBostedsadresser.zipWithNext { bostedadresse, neste ->
            mapBostedadresse(bostedadresse, parseIsoDato(neste.gyldigFraOgMed)?.minusDays(1))
        }.plus(mapBostedadresse(sortertPdlBostedsadresser.last()))
    }

    private fun mapBostedadresse(adresse: Bostedsadresse, gyldigTilOgMed: LocalDate? = null): Adresse {

        return Adresse(
            landkode = adresse.utenlandskAdresse?.landkode ?: "NOR",
            fom = parseIsoDato(adresse.gyldigFraOgMed),
            tom = parseIsoDato(adresse.gyldigTilOgMed) ?: gyldigTilOgMed,
            historisk = adresse.metadata.historisk
        )
    }

    fun mapKontaktadresser(pdlKontaktadresser: List<Kontaktadresse>): List<Adresse> {

        if (pdlKontaktadresser.size < 2) {
            return pdlKontaktadresser.map {
                mapKontaktadresse(it)
            }
        }

        val sortertPdlKontaktadresser = pdlKontaktadresser.sortedBy { parseIsoDato(it.gyldigFraOgMed) ?: LocalDate.MIN }
        return sortertPdlKontaktadresser.zipWithNext { kontaktadresse, neste ->
            mapKontaktadresse(kontaktadresse, parseIsoDato(neste.gyldigFraOgMed)?.minusDays(1))
        }.plus(mapKontaktadresse(sortertPdlKontaktadresser.last()))
    }

    private fun mapKontaktadresse(pdlKontaktadresse: Kontaktadresse, gyldigTilOgMed: LocalDate? = null): Adresse {
        return Adresse(
            fom = parseIsoDato(pdlKontaktadresse.gyldigFraOgMed),
            tom = parseIsoDato(pdlKontaktadresse.gyldigTilOgMed) ?: gyldigTilOgMed,
            landkode = mapLandkodeForKontaktadresse(pdlKontaktadresse),
            historisk = pdlKontaktadresse.metadata.historisk
        )
    }

    fun mapStatsborgerskap(statsborgerskap: List<no.nav.medlemskap.clients.pdl.generated.hentperson.Statsborgerskap>): List<Statsborgerskap> {
        return statsborgerskap.map {
            Statsborgerskap(
                landkode = it.land,
                fom = parseIsoDato(it.gyldigFraOgMed),
                tom = parseIsoDato(it.gyldigTilOgMed),
                historisk = it.metadata.historisk
            )
        }.sortedBy { it.fom }
    }

    private fun mapFamileRelasjonsrolle(rolle: no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle?): ForelderBarnRelasjonRolle? {
        return rolle.let {
            when (it) {
                no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle.BARN -> ForelderBarnRelasjonRolle.BARN
                no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle.MOR -> ForelderBarnRelasjonRolle.MOR
                no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle.FAR -> ForelderBarnRelasjonRolle.FAR
                no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle.MEDMOR -> ForelderBarnRelasjonRolle.MEDMOR
                else -> throw DetteSkalAldriSkje("Denne familierelasjonen er ikke tilgjengelig")
            }
        }
    }

    private fun mapLandkode(landkode: String): String {
        if (landkode.length == 3) {
            return landkode
        }
        return try {
            CountryCode.getByCode(landkode.toUpperCase()).alpha3
        } catch (e: Exception) {
            logger.warn("Klarte ikke å mappe {}", landkode, e)
            "UKJENT"
        }
    }

    private fun parseNullableDateTimeFraPDL(date: DateTime?): LocalDateTime? {
        return if (date != null) {
            LocalDateTime.parse(date)
        } else {
            null
        }
    }
    private fun parseNullableDateStringFraPDL(date: no.nav.medlemskap.clients.pdl.generated.Date?): LocalDate? {
        return if (date != null) {
            LocalDate.parse(date)
        } else {
            null
        }
    }
}
