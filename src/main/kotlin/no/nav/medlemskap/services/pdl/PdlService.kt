package no.nav.medlemskap.services.pdl

import mu.KotlinLogging
import no.nav.medlemskap.clients.pdl.PdlClient
import no.nav.medlemskap.clients.pdl.generated.enums.AdressebeskyttelseGradering
import no.nav.medlemskap.clients.pdl.generated.enums.IdentGruppe
import no.nav.medlemskap.clients.pdl.generated.hentperson.Adressebeskyttelse
import no.nav.medlemskap.common.exceptions.GradertAdresseException
import no.nav.medlemskap.common.exceptions.GraphqlError
import no.nav.medlemskap.common.exceptions.IdenterIkkeFunnet
import no.nav.medlemskap.common.exceptions.PersonIkkeFunnet
import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.common.ytelse
import no.nav.medlemskap.domene.barn.PersonhistorikkBarn
import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.Personhistorikk
import no.nav.medlemskap.services.pdl.mapper.PdlMapper
import no.nav.medlemskap.services.pdl.mapper.PdlMapperBarn
import no.nav.medlemskap.services.pdl.mapper.PdlMapperEktefelle
import java.time.LocalDate
import kotlin.coroutines.coroutineContext

class PdlService(private val pdlClient: PdlClient, private val clusterName: String = "dev-gcp") {

    private val logger = KotlinLogging.logger { }

    suspend fun hentAlleAktorIder(fnr: String, callId: String): List<String> {
        val pdlResponse = pdlClient.hentIdenterv2(fnr, callId)

        // Hack for å overleve manglende aktørID i ikke-konsistente data i Q2
        if (pdlResponse.errors != null && clusterName == "dev-gcp") {
            return listOf("111111111111")
        }

        pdlResponse.errors?.let { errors ->
            logger.warn { "Fikk følgende feil fra PDL: ${objectMapper.writeValueAsString(errors)}" }
            throw GraphqlError(errors.first(), "PDL", coroutineContext.ytelse())
        }

        return pdlResponse.data?.hentIdenter?.identer
            ?.filter { it.gruppe == IdentGruppe.AKTORID }
            ?.map { it.ident } ?: throw IdenterIkkeFunnet(coroutineContext.ytelse())
    }

    suspend fun hentPersonHistorikkTilBruker(fnr: String, callId: String): Personhistorikk {
        val hentPersonhistorikkTilBrukerRespons = pdlClient.hentPersonV2(fnr, callId)
        val adresseBeskyttelse = hentPersonhistorikkTilBrukerRespons.data?.hentPerson?.adressebeskyttelse

        if (adresseBeskyttelse?.let { harAdressebeskyttelse(it) } == true) {
            throw GradertAdresseException("PDL", coroutineContext.ytelse())
        }

        hentPersonhistorikkTilBrukerRespons.errors?.forEach {
            if (it.message == "Fant ikke person") {
                throw PersonIkkeFunnet("PDL", coroutineContext.ytelse())
            }
        }

        if (hentPersonhistorikkTilBrukerRespons.data?.hentPerson != null) {
            return PdlMapper.mapTilPersonHistorikkTilBruker(hentPersonhistorikkTilBrukerRespons.data?.hentPerson!!)
        } else throw PersonIkkeFunnet("PDL", coroutineContext.ytelse())
    }

    suspend fun hentPersonHistorikkTilEktefelle(fnrTilEktefelle: String, førsteDatoForYtelse: LocalDate, callId: String): PersonhistorikkEktefelle? {
        val hentPersonhistorikkTilEktefelleResponse = pdlClient.hentPersonV2(fnrTilEktefelle, callId)
        val adresseBeskyttelse = hentPersonhistorikkTilEktefelleResponse.data?.hentPerson?.adressebeskyttelse

        if (adresseBeskyttelse?.let { harAdressebeskyttelse(it) } == true) {
            return null
        }
        hentPersonhistorikkTilEktefelleResponse.errors?.forEach {
            if (it.message == "Fant ikke person") {
                return null
            }
        }

        hentPersonhistorikkTilEktefelleResponse.errors?.let { errors ->
            logger.warn { "Fikk følgende feil fra PDL: ${objectMapper.writeValueAsString(errors)}" }
            throw GraphqlError(errors.first(), "PDL", coroutineContext.ytelse())
        }

        if (hentPersonhistorikkTilEktefelleResponse.data?.hentPerson != null) {
            return PdlMapperEktefelle.mapPersonhistorikkTilEktefelle(fnrTilEktefelle, hentPersonhistorikkTilEktefelleResponse.data?.hentPerson!!, førsteDatoForYtelse)
        }
        return null
    }

    suspend fun hentPersonHistorikkTilBarn(fnrTilBarn: String, callId: String): PersonhistorikkBarn? {
        val hentPersonHistorikkTilBarnRespons = pdlClient.hentPersonV2(fnrTilBarn, callId)
        val adresseBeskyttelse = hentPersonHistorikkTilBarnRespons.data?.hentPerson?.adressebeskyttelse

        if (adresseBeskyttelse?.let { harAdressebeskyttelse(it) } == true) {
            return null
        }

        hentPersonHistorikkTilBarnRespons.errors?.forEach {
            if (it.message == "Fant ikke person") {
                return null
            }
        }

        hentPersonHistorikkTilBarnRespons.errors?.let { errors ->
            logger.warn { "Fikk følgende feil fra PDL: ${objectMapper.writeValueAsString(errors)}" }
            throw GraphqlError(errors.first(), "PDL", coroutineContext.ytelse())
        }

        if (hentPersonHistorikkTilBarnRespons.data?.hentPerson != null) {
            return PdlMapperBarn.mapPersonhistorikkTilBarn(fnrTilBarn, hentPersonHistorikkTilBarnRespons.data?.hentPerson!!)
        }

        return null
    }

    fun harAdressebeskyttelse(adresseBeskyttelse: List<Adressebeskyttelse>): Boolean {
        return adresseBeskyttelse.any { it.gradering != AdressebeskyttelseGradering.UGRADERT }
    }
}
