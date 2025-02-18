package no.nav.medlemskap.cucumber.mapping.pdl

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.clients.pdl.generated.enums.ForelderBarnRelasjonRolle
import no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype
import no.nav.medlemskap.clients.pdl.generated.hentperson.*
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import java.time.LocalDate

class PdlDomenespråkParser : BasisDomeneParser() {

    fun mapPdlParametre(dataTable: DataTable): PdlParametre {
        return mapDataTable(dataTable, PdlParametreMapper()).get(0)
    }

    fun mapStatsborgerskap(dataTable: DataTable): List<Statsborgerskap> {
        return mapDataTable(dataTable, StatsborgerskapMapper())
    }

    fun mapBostedsadresser(dataTable: DataTable): List<Bostedsadresse> {
        return mapDataTable(dataTable, BostedsadresseMapper())
    }

    fun mapKontaktadresser(dataTable: DataTable): List<Kontaktadresse> {
        return mapDataTable(dataTable, KontaktadresseMapper())
    }

    fun mapOppholdsadresser(dataTable: DataTable): List<Oppholdsadresse> {
        return mapDataTable(dataTable, OppholdsadresseMapper())
    }

    fun mapSivilstander(dataTable: DataTable): List<Sivilstand> {
        return mapDataTable(dataTable, SivilstandMapper())
    }

    fun mapFamilierelasjoner(dataTable: DataTable): List<ForelderBarnRelasjon> {
        return mapDataTable(dataTable, FamilerelasjonMapper())
    }

    fun mapDoedsfall(dataTable: DataTable): List<Doedsfall> {
        return mapDataTable(dataTable, DoedsfallMapper())
    }

    fun mapNavn(dataTable: DataTable): List<Navn> {
        return mapDataTable(dataTable, NavnMapper())
    }

    class NavnMapper : RadMapper<Navn> {
        override fun mapRad(rad: Map<String, String>): Navn {
            return Navn(
                parseString(Domenebegrep.FORNAVN, rad),
                parseValgfriString(Domenebegrep.MELLOMNAVN, rad),
                parseString(Domenebegrep.ETTERNAVN, rad)
            )
        }
    }

    class StatsborgerskapMapper : RadMapper<Statsborgerskap> {
        override fun mapRad(rad: Map<String, String>): Statsborgerskap {

            val historisk = parseValgfriBoolean(Domenebegrep.HISTORISK.nøkkel(), rad) ?: false

            return Statsborgerskap(
                parseString(Domenebegrep.LAND, rad),
                parseValgfriString(Domenebegrep.GYLDIG_FRA_OG_MED_DATO, rad),
                parseValgfriString(Domenebegrep.GYLDIG_TIL_OG_MED_DATO, rad),
                Metadata(historisk)
            )
        }
    }

    class BostedsadresseMapper : RadMapper<Bostedsadresse> {
        override fun mapRad(rad: Map<String, String>): Bostedsadresse {
            val utenlandskLandkode = parseValgfriString(Domenebegrep.UTENLANDSK_ADRESSE_LANDKODE, rad)

            val utenlandskAdresse = if (utenlandskLandkode != null) {
                UtenlandskAdresse(utenlandskLandkode)
            } else {
                null
            }

            return Bostedsadresse(
                gyldigFraOgMed = parseValgfriString(Domenebegrep.GYLDIG_FRA_OG_MED, rad),
                gyldigTilOgMed = parseValgfriString(Domenebegrep.GYLDIG_TIL_OG_MED, rad),
                utenlandskAdresse = utenlandskAdresse,
                angittFlyttedato = null,
                vegadresse = null,
                matrikkeladresse = null,
                ukjentBosted = null,
                metadata = Metadata(false)
            )
        }
    }

    class KontaktadresseMapper : RadMapper<Kontaktadresse> {
        override fun mapRad(rad: Map<String, String>): Kontaktadresse {
            val utenlandskLandkode = parseValgfriString(Domenebegrep.UTENLANDSK_ADRESSE_LANDKODE, rad)

            val utenlandskAdresse = if (utenlandskLandkode != null) {
                UtenlandskAdresse(utenlandskLandkode)
            } else {
                null
            }

            val utenlandskLandkodeFrittFormat = parseValgfriString(Domenebegrep.UTENLANDSK_ADRESSE_FRITT_FORMAT_LANDKODE, rad)

            val utenlandskAdresseFrittFormat = if (utenlandskLandkodeFrittFormat != null) {
                UtenlandskAdresseIFrittFormat(utenlandskLandkodeFrittFormat)
            } else {
                null
            }

            return Kontaktadresse(
                gyldigFraOgMed = parseValgfriString(Domenebegrep.GYLDIG_FRA_OG_MED, rad),
                gyldigTilOgMed = parseValgfriString(Domenebegrep.GYLDIG_TIL_OG_MED, rad),
                utenlandskAdresse = utenlandskAdresse,
                utenlandskAdresseIFrittFormat = utenlandskAdresseFrittFormat,
                metadata = Metadata(false)
            )
        }
    }

    class OppholdsadresseMapper : RadMapper<Oppholdsadresse> {
        override fun mapRad(rad: Map<String, String>): Oppholdsadresse {
            val utenlandskLandkode = parseValgfriString(Domenebegrep.UTENLANDSK_ADRESSE_LANDKODE, rad)

            val utenlandskAdresse = if (utenlandskLandkode != null) {
                UtenlandskAdresse(utenlandskLandkode)
            } else {
                null
            }

            return Oppholdsadresse(
                gyldigFraOgMed = parseValgfriString(Domenebegrep.GYLDIG_FRA_OG_MED, rad),
                gyldigTilOgMed = parseValgfriString(Domenebegrep.GYLDIG_TIL_OG_MED, rad),
                utenlandskAdresse = utenlandskAdresse,
                metadata = Metadata(false)
            )
        }
    }

    class PdlParametreMapper : RadMapper<PdlParametre> {
        override fun mapRad(rad: Map<String, String>): PdlParametre {
            return PdlParametre(parseDato(Domenebegrep.FØRSTE_DATO_FOR_YTELSE, rad))
        }
    }

    class SivilstandMapper : RadMapper<Sivilstand> {

        override fun mapRad(rad: Map<String, String>): Sivilstand {
            val sivilstandstype = Sivilstandstype.valueOf(parseString(Domenebegrep.SIVILSTANDSTYPE, rad))

            return Sivilstand(
                type = sivilstandstype,
                gyldigFraOgMed = parseValgfriString(Domenebegrep.GYLDIG_FRA_OG_MED, rad),
                bekreftelsesdato = parseValgfriString(Domenebegrep.BEKREFTELSESDATO, rad),
                relatertVedSivilstand = parseValgfriString(Domenebegrep.RELATERT_VED_SIVILSTAND, rad)
            )
        }
    }

    class FamilerelasjonMapper : RadMapper<ForelderBarnRelasjon> {

        override fun mapRad(rad: Map<String, String>): ForelderBarnRelasjon {
            val relatertPersonsrolle = ForelderBarnRelasjonRolle.valueOf(parseString(Domenebegrep.RELATERT_PERSONS_ROLLE, rad))
            val minRolleForPersonStr = parseValgfriString(Domenebegrep.MIN_ROLLE_FOR_PERSON, rad)

            val minRolleForPerson = if (minRolleForPersonStr != null) {
                ForelderBarnRelasjonRolle.valueOf(minRolleForPersonStr)
            } else {
                null
            }

            return ForelderBarnRelasjon(
                relatertPersonsIdent = parseString(Domenebegrep.RELATERT_PERSONS_IDENT, rad),
                relatertPersonsRolle = relatertPersonsrolle,
                minRolleForPerson = minRolleForPerson
            )
        }
    }

    class DoedsfallMapper : RadMapper<Doedsfall> {

        override fun mapRad(rad: Map<String, String>): Doedsfall {
            return Doedsfall(
                doedsdato = parseValgfriString(Domenebegrep.DOEDSDATO, rad)
            )
        }
    }

    enum class Domenebegrep(val nøkkel: String) : Domenenøkkel {
        BEKREFTELSESDATO("Bekreftelsesdato"),
        DOEDSDATO("Dødsdato"),
        FØRSTE_DATO_FOR_YTELSE("Første dato for ytelse"),
        GYLDIG_FRA_OG_MED_DATO("Gyldig fra og med dato"),
        GYLDIG_TIL_OG_MED_DATO("Gyldig til og med dato"),
        HISTORISK("Historisk"),
        GYLDIG_FRA_OG_MED("Gyldig fra og med"),
        GYLDIG_TIL_OG_MED("Gyldig til og med"),
        LAND("Land"),
        MIN_ROLLE_FOR_PERSON("Min rolle for person"),
        RELATERT_PERSONS_IDENT("Relatert persons ident"),
        RELATERT_PERSONS_ROLLE("Relatert persons rolle"),
        RELATERT_VED_SIVILSTAND("Relatert ved sivilstand"),
        SIVILSTANDSTYPE("Type"),
        UTENLANDSK_ADRESSE_LANDKODE("Utenlandsk adresse landkode"),
        UTENLANDSK_ADRESSE_FRITT_FORMAT_LANDKODE("Utenlandsk adresse frittformat landkode"),
        FORNAVN("Fornavn"),
        MELLOMNAVN("Mellomnavn"),
        ETTERNAVN("Etternavn");

        override fun nøkkel(): String {
            return nøkkel
        }
    }

    data class PdlParametre(val førsteDatoForYtelse: LocalDate)
}
