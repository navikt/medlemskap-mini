package no.nav.medlemskap.cucumber.steps.pdl

import io.cucumber.datatable.DataTable
import io.cucumber.java8.No
import io.kotest.matchers.collections.shouldContainExactly
import io.ktor.server.plugins.*
import no.nav.medlemskap.clients.pdl.generated.hentperson.*
import no.nav.medlemskap.cucumber.SpraakParserDomene.PersonhistorikkDomeneSpraakParser
import no.nav.medlemskap.cucumber.mapping.pdl.PdlDomenespråkParser
import no.nav.medlemskap.domene.barn.PersonhistorikkBarn
import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.Personhistorikk
import no.nav.medlemskap.services.pdl.mapper.PdlMapper
import no.nav.medlemskap.services.pdl.mapper.PdlMapperBarn
import no.nav.medlemskap.services.pdl.mapper.PdlMapperEktefelle
import java.time.LocalDate

class PdlMapperSteps : No {

    private val pdlDomenespråkParser = PdlDomenespråkParser()

    private var pdlPersonBuilder = PdlPersonBuilder()
    private var pdlPeronBuilderBarn = PdlPersonBuilder()
    private var pdlPersonBuilderEktefelle = PdlPersonBuilder()
    private var personhistorikk: Personhistorikk? = null
    private var personhistorikkEktefelle: PersonhistorikkEktefelle? = null
    private var personhistorikkBarn: PersonhistorikkBarn? = null

    init {
        Gitt("følgende statsborgerskap fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.statsborgerskap = pdlDomenespråkParser.mapStatsborgerskap(dataTable)
        }

        Gitt("følgende bostedsadresser fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.bostedsadresser = pdlDomenespråkParser.mapBostedsadresser(dataTable)
        }

        Gitt("følgende kontaktadresser fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.kontaktadresser = pdlDomenespråkParser.mapKontaktadresser(dataTable)
        }

        Gitt("følgende oppholdsadresser fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.oppholdsadresser = pdlDomenespråkParser.mapOppholdsadresser(dataTable)
        }

        Gitt("følgende sivilstander fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.sivilstander = pdlDomenespråkParser.mapSivilstander(dataTable)
        }

        Gitt("følgende familierelasjoner fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.familierelasjoner = pdlDomenespråkParser.mapFamilierelasjoner(dataTable)
        }

        Gitt("følgende opplysninger om doedsfall fra PDL:") { dataTable: DataTable ->
            pdlPersonBuilder.doedsfall = pdlDomenespråkParser.mapDoedsfall(dataTable)
        }

        Gitt("følgende bostedsadresse i barnets personhistorikk") { dataTable: DataTable ->
            pdlPeronBuilderBarn.bostedsadresser = pdlDomenespråkParser.mapBostedsadresser(dataTable)
        }

        Gitt("følgende bostedsadresse til ektefelles personhistorikk") { dataTable: DataTable ->
            pdlPersonBuilderEktefelle.bostedsadresser = pdlDomenespråkParser.mapBostedsadresser(dataTable)
        }

        Gitt("følgende navn fra PDL") { dataTable: DataTable ->
            pdlPersonBuilder.navn = pdlDomenespråkParser.mapNavn(dataTable)
        }

        Når("statsborgerskap mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("bostedsadresser mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("kontaktadresser mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("oppholdsadresser mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("sivilstander mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("doedsfall mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("familierelasjoner mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("navn mappes") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("PDL Person mappes til personhistorikk i datagrunnlaget") {
            personhistorikk = mapTilPersonhistorikk()
        }

        Når("personhistorikken til bruker, ektefelle og barn mappes med følgende parametre") { dataTable: DataTable ->
            val pdlParametre = pdlDomenespråkParser.mapPdlParametre(dataTable)

            personhistorikk = mapTilPersonhistorikk()
            personhistorikkBarn = mapTilPersonhistorikkBarn()
            personhistorikkEktefelle = mapTilPersonhistorikkEktefelle(pdlParametre.førsteDatoForYtelse)
        }

        Så("skal mappet statsborgerskap være") { dataTable: DataTable ->
            val statsborgerskapForventet = PersonhistorikkDomeneSpraakParser.mapStatsborgerskap(dataTable)
            personhistorikk!!.statsborgerskap.shouldContainExactly(statsborgerskapForventet)
        }

        Så("skal mappede bostedsadresser være") { dataTable: DataTable ->
            val bostedsadresserForventet = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            personhistorikk!!.bostedsadresser.shouldContainExactly(bostedsadresserForventet)
        }

        Så("skal mappede kontaktadresser være") { dataTable: DataTable ->
            val kontaktadresserForventet = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            personhistorikk!!.kontaktadresser.shouldContainExactly(kontaktadresserForventet)
        }

        Så("skal mappede oppholdsadresser være") { dataTable: DataTable ->
            val oppholdsadresserForventet = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            personhistorikk!!.oppholdsadresser.shouldContainExactly(oppholdsadresserForventet)
        }

        Så("skal mappede sivilstander være") { dataTable: DataTable ->
            val sivilstanderForventet = PersonhistorikkDomeneSpraakParser.mapSivilstander(dataTable)
            personhistorikk!!.sivilstand.shouldContainExactly(sivilstanderForventet)
        }

        Så("skal mappede familierelasjoner være") { dataTable: DataTable ->
            val familierelasjonerForventet = PersonhistorikkDomeneSpraakParser.mapFamilierelasjoner(dataTable)
            personhistorikk!!.forelderBarnRelasjon.shouldContainExactly(familierelasjonerForventet)
        }

        Så("skal mappede doedsfall være") { dataTable: DataTable ->
            val doedsfallForventet = PersonhistorikkDomeneSpraakParser.mapDoedsfall(dataTable)
            personhistorikk?.doedsfall.shouldContainExactly(doedsfallForventet)
        }

        Så("mappede bostedadresse til barnet være") { dataTable: DataTable ->
            val bostedsadresseForventet = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            personhistorikkBarn?.bostedsadresser.shouldContainExactly(bostedsadresseForventet)
        }

        Så("følgende bostedsadresse til ektefelle være") { dataTable: DataTable ->
            val bostedsadresseForventet = PersonhistorikkDomeneSpraakParser.mapAdresser(dataTable)
            personhistorikkEktefelle?.bostedsadresser.shouldContainExactly(bostedsadresseForventet)
        }

        Så("skal mappet navn være") { dataTable: DataTable ->
            val navn = PersonhistorikkDomeneSpraakParser.mapNavn(dataTable)
            personhistorikk?.navn.shouldContainExactly(navn)
        }
    }

    private fun mapTilPersonhistorikk(): Personhistorikk {
        return PdlMapper.mapTilPersonHistorikkTilBruker(pdlPersonBuilder.build())
    }

    private fun mapTilPersonhistorikkBarn(): PersonhistorikkBarn {
        val identBarn = personhistorikk?.forelderBarnRelasjon?.get(0)?.relatertPersonsIdent
            ?: throw BadRequestException("Ingen fnr registrert på barn i familierelasjoner")
        return PdlMapperBarn.mapPersonhistorikkTilBarn(identBarn, pdlPeronBuilderBarn.build())
    }

    private fun mapTilPersonhistorikkEktefelle(førsteDatoForYtelse: LocalDate): PersonhistorikkEktefelle {
        val identEktefelle = personhistorikk?.sivilstand?.get(0)?.relatertVedSivilstand
            ?: throw BadRequestException("Ingen fnr registrert på ektefelle i sivilstand")
        return PdlMapperEktefelle.mapPersonhistorikkTilEktefelle(identEktefelle, pdlPersonBuilderEktefelle.build(), førsteDatoForYtelse)
    }

    class PdlPersonBuilder {
        var statsborgerskap: List<Statsborgerskap> = emptyList()
        var bostedsadresser: List<Bostedsadresse> = emptyList()
        var kontaktadresser: List<Kontaktadresse> = emptyList()
        var oppholdsadresser: List<Oppholdsadresse> = emptyList()
        var sivilstander: List<Sivilstand> = emptyList()
        var familierelasjoner: List<ForelderBarnRelasjon> = emptyList()
        var doedsfall: List<Doedsfall> = emptyList()
        var adressebeskyttelse: List<Adressebeskyttelse> = emptyList()
        val innflyttingTilNorge: List<InnflyttingTilNorge> = emptyList()
        val utflyttingFraNorge: List<UtflyttingFraNorge> = emptyList()
        var navn: List<Navn> = emptyList()

        fun build(): Person {
            return Person(
                forelderBarnRelasjon = familierelasjoner,
                statsborgerskap = statsborgerskap,
                sivilstand = sivilstander,
                bostedsadresse = bostedsadresser,
                kontaktadresse = kontaktadresser,
                oppholdsadresse = oppholdsadresser,
                doedsfall = doedsfall,
                adressebeskyttelse = adressebeskyttelse,
                innflyttingTilNorge = innflyttingTilNorge,
                utflyttingFraNorge = utflyttingFraNorge,
                navn = navn,
                opphold = emptyList()
            )
        }
    }
}
