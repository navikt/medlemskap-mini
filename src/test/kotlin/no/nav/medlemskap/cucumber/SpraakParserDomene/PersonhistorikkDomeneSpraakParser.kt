package no.nav.medlemskap.cucumber.SpraakParserDomene

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.*
import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.domene.barn.PersonhistorikkBarn
import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.*
import java.time.LocalDate

object PersonhistorikkDomeneSpraakParser : BasisDomeneParser() {

    fun mapStatsborgerskap(dataTable: DataTable): List<Statsborgerskap> {
        return mapDataTable(dataTable, StatsborgerskapMapper())
    }

    fun mapAdresser(dataTable: DataTable): List<Adresse> {
        return mapDataTable(dataTable, AdresseMapper())
    }

    fun mapSivilstander(dataTable: DataTable): List<Sivilstand> {
        return mapDataTable(dataTable, SivilstandMapper())
    }

    fun mapDoedsfall(dataTable: DataTable): List<LocalDate> {
        return mapDataTable(dataTable, DoedsfallMapper())
    }

    fun mapFamilierelasjoner(dataTable: DataTable): List<ForelderBarnRelasjon> {
        return mapDataTable(dataTable, FamilieRelasjonMapper())
    }

    fun mapPersonhistorikkBarn(dataTable: DataTable): List<DataOmBarn> {
        return mapDataTable(dataTable, PersonhistorikkBarnMapper())
    }

    fun mapPersonhistorikkEktefelle(dataTable: DataTable): List<PersonhistorikkEktefelle> {
        return mapDataTable(dataTable, PersonhistorikkEktefelleMapper())
    }

    fun mapBarnTilEktefelle(dataTable: DataTable): List<String> {
        return mapDataTable(dataTable, BarnTilEktefelleMapper())
    }

    fun mapNavn(dataTable: DataTable): List<Navn> {
        return mapDataTable(dataTable, NavnMapper())
    }

    class NavnMapper : RadMapper<Navn> {
        override fun mapRad(rad: Map<String, String>): Navn {
            return Navn(
                parseString(PersonhistorikkDomenebegrep.FORNAVN, rad),
                parseValgfriString(PersonhistorikkDomenebegrep.MELLOMNAVN, rad),
                parseString(PersonhistorikkDomenebegrep.ETTERNAVN, rad)
            )
        }
    }

    class StatsborgerskapMapper : RadMapper<Statsborgerskap> {
        override fun mapRad(rad: Map<String, String>): Statsborgerskap {
            return Statsborgerskap(
                parseString(PersonhistorikkDomenebegrep.LANDKODE, rad),
                parseValgfriDato(PersonhistorikkDomenebegrep.FRA_OG_MED_DATO, rad),
                parseValgfriDato(PersonhistorikkDomenebegrep.TIL_OG_MED_DATO, rad),
                parseValgfriBoolean(PersonhistorikkDomenebegrep.HISTORISK.nøkkel(), rad) ?: false
            )
        }
    }

    class SivilstandMapper : RadMapper<Sivilstand> {
        override fun mapRad(rad: Map<String, String>): Sivilstand {
            return Sivilstand(
                type = DomenespråkParser.parseSivilstandstype(PersonhistorikkDomenebegrep.SIVILSTANDSTYPE, rad),
                gyldigFraOgMed = parseValgfriDato(PersonhistorikkDomenebegrep.GYLDIG_FRA_OG_MED, rad),
                gyldigTilOgMed = parseValgfriDato(PersonhistorikkDomenebegrep.GYLDIG_TIL_OG_MED, rad),
                relatertVedSivilstand = parseValgfriString(PersonhistorikkDomenebegrep.RELATERT_VED_SIVILSTAND, rad)
            )
        }
    }

    class DoedsfallMapper : RadMapper<LocalDate> {
        override fun mapRad(rad: Map<String, String>): LocalDate {
            return parseDato(PersonhistorikkDomenebegrep.DOEDSDATO, rad)
        }
    }

    class AdresseMapper : RadMapper<Adresse> {
        override fun mapRad(rad: Map<String, String>): Adresse {
            return Adresse(
                parseString(PersonhistorikkDomenebegrep.LANDKODE, rad),
                parseValgfriDato(PersonhistorikkDomenebegrep.FRA_OG_MED_DATO, rad),
                parseValgfriDato(PersonhistorikkDomenebegrep.TIL_OG_MED_DATO, rad),
                parseValgfriBoolean(PersonhistorikkDomenebegrep.HISTORISK.nøkkel(), rad) ?: false
            )
        }
    }

    class FamilieRelasjonMapper : RadMapper<ForelderBarnRelasjon> {
        override fun mapRad(rad: Map<String, String>): ForelderBarnRelasjon {
            return ForelderBarnRelasjon(
                relatertPersonsIdent = parseString(PersonhistorikkDomenebegrep.RELATERT_PERSONS_IDENT, rad),
                relatertPersonsRolle = DomenespråkParser.parseRolle(PersonhistorikkDomenebegrep.RELATERT_PERSONS_ROLLE, rad),
                minRolleForPerson = DomenespråkParser.parseValgfriRolle(PersonhistorikkDomenebegrep.MIN_ROLLE_FOR_PERSON, rad)
            )
        }
    }

    class BarnTilEktefelleMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return String()
        }
    }

    class PersonhistorikkEktefelleMapper : RadMapper<PersonhistorikkEktefelle> {
        override fun mapRad(rad: Map<String, String>): PersonhistorikkEktefelle {
            val fraOgMedDato = parseValgfriDato(PersonhistorikkDomenebegrep.FRA_OG_MED_DATO, rad)
            val tilOgMedDato = parseValgfriDato(PersonhistorikkDomenebegrep.TIL_OG_MED_DATO, rad)
            val historisk = parseValgfriBoolean(PersonhistorikkDomenebegrep.HISTORISK.nøkkel(), rad) ?: false

            val statsborgerskap = mutableListOf<Statsborgerskap>()
            val landkode = parseValgfriString(PersonhistorikkDomenebegrep.STATSBORGERSKAP, rad)
            if (landkode != null) {
                statsborgerskap.add(Statsborgerskap(landkode, fraOgMedDato, tilOgMedDato, historisk))
            }

            val bostedsadresser = mutableListOf<Adresse>()
            val bostedsadresse = parseValgfriString(PersonhistorikkDomenebegrep.BOSTED, rad)
            if (bostedsadresse != null) {
                bostedsadresser.add(Adresse(bostedsadresse, fraOgMedDato, tilOgMedDato, historisk))
            }

            val kontaktadresser = mutableListOf<Adresse>()
            val kontaktadresse = parseValgfriString(PersonhistorikkDomenebegrep.KONTAKTADRESSE, rad)
            if (kontaktadresse != null) {
                kontaktadresser.add(Adresse(kontaktadresse, fraOgMedDato, tilOgMedDato, historisk))
            }

            val oppholdsadresser = mutableListOf<Adresse>()
            val oppholdsadresse = parseValgfriString(PersonhistorikkDomenebegrep.OPPHOLDSADRESSE, rad)
            if (oppholdsadresse != null) {
                oppholdsadresser.add(Adresse(oppholdsadresse, fraOgMedDato, tilOgMedDato, historisk))
            }

            return PersonhistorikkEktefelle(
                ident = parseString(Domenebegrep.IDENT, rad),
                barn = mutableListOf<String>(),
                statsborgerskap = statsborgerskap,
                bostedsadresser = bostedsadresser,
                kontaktadresser = kontaktadresser,
                oppholdsadresser = oppholdsadresser

            )
        }
    }

    class PersonhistorikkBarnMapper : RadMapper<DataOmBarn> {
        override fun mapRad(rad: Map<String, String>): DataOmBarn {
            val fraOgMedDato = parseValgfriDato(PersonhistorikkDomenebegrep.FRA_OG_MED_DATO, rad)
            val tilOgMedDato = parseValgfriDato(PersonhistorikkDomenebegrep.TIL_OG_MED_DATO, rad)

            val bostedsadresser = mutableListOf<Adresse>()
            val bostedsadresse = parseValgfriString(PersonhistorikkDomenebegrep.BOSTED, rad)
            val historisk = parseValgfriBoolean(PersonhistorikkDomenebegrep.HISTORISK.nøkkel(), rad) ?: false
            if (bostedsadresse != null) {
                bostedsadresser.add(Adresse(bostedsadresse, fraOgMedDato, tilOgMedDato, historisk))
            }

            val kontaktadresser = mutableListOf<Adresse>()
            val kontaktadresse = parseValgfriString(PersonhistorikkDomenebegrep.KONTAKTADRESSE, rad)
            if (kontaktadresse != null) {
                kontaktadresser.add(Adresse(kontaktadresse, fraOgMedDato, tilOgMedDato, historisk))
            }

            val oppholdsadresser = mutableListOf<Adresse>()
            val oppholdsadresse = parseValgfriString(PersonhistorikkDomenebegrep.OPPHOLDSADRESSE, rad)
            if (oppholdsadresse != null) {
                oppholdsadresser.add(Adresse(oppholdsadresse, fraOgMedDato, tilOgMedDato, historisk))
            }

            return DataOmBarn(
                PersonhistorikkBarn(
                    ident = parseString(PersonhistorikkDomenebegrep.IDENT, rad),
                    forelderBarnRelasjon = mutableListOf<ForelderBarnRelasjon>(),
                    bostedsadresser = bostedsadresser,
                    kontaktadresser = kontaktadresser,
                    oppholdsadresser = oppholdsadresser
                )
            )
        }
    }
}

enum class PersonhistorikkDomenebegrep(val nøkkel: String) : Domenenøkkel {
    BOSTED("Bosted"),
    DOEDSDATO("Dødsdato"),
    FRA_OG_MED_DATO("Fra og med dato"),
    GYLDIG_FRA_OG_MED("Gyldig fra og med dato"),
    GYLDIG_TIL_OG_MED("Gyldig til og med dato"),
    HISTORISK("Historisk"),
    IDENT("Ident"),
    KONTAKTADRESSE("Kontaktadresse"),
    LANDKODE("Landkode"),
    MIN_ROLLE_FOR_PERSON("Min rolle for person"),
    OPPHOLDSADRESSE("Oppholdsadresse"),
    PROSENT("Prosent"),
    RELATERT_PERSONS_IDENT("Relatert persons ident"),
    RELATERT_VED_SIVILSTAND("Relatert ved sivilstand"),
    RELATERT_PERSONS_ROLLE("Relatert persons rolle"),
    SIVILSTANDSTYPE("Sivilstandstype"),
    STATSBORGERSKAP("Statsborgerskap"),
    TIL_OG_MED_DATO("Til og med dato"),
    FORNAVN("Fornavn"),
    MELLOMNAVN("Mellomnavn"),
    ETTERNAVN("Etternavn");

    override fun nøkkel(): String {
        return nøkkel
    }
}
