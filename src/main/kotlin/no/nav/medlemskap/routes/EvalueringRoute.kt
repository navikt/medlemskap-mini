package no.nav.medlemskap.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.clients.Services
import no.nav.medlemskap.common.RequestContextService
import no.nav.medlemskap.common.apiCounter
import no.nav.medlemskap.common.exceptions.KonsumentIkkeFunnet
import no.nav.medlemskap.common.exceptions.UgyldigRequestException
import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.common.uavklartPåRegel
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.domene.*
import no.nav.medlemskap.domene.Fødselsnummer.Companion.gyldigFnr
import no.nav.medlemskap.domene.Journalpost.Companion.alleFagsakIDer
import no.nav.medlemskap.domene.Journalpost.Companion.harDokument
import no.nav.medlemskap.domene.Ytelse.Companion.name
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.alleAktiveYrkeskoderDerTomErNull
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.antallAnsatteHosArbeidsgivere
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.antallAnsatteHosArbeidsgiversJuridiskeEnheter
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.summVektetStilingsProsentIKontrollPeriode
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.v1.Hovedregler
import no.nav.medlemskap.services.kafka.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger { }
private val secureLogger = KotlinLogging.logger("tjenestekall")
private val TOPIC = "medlemskap.medlemskap-stage1"

fun Routing.evalueringRoute(
    services: Services,
    configuration: Configuration,
    requestContextService: RequestContextService,
    createDatagrunnlag: suspend (request: Request, callId: String, services: Services, clientId: String?) -> Datagrunnlag
) {

    authenticate("azureAuth") {
        post("/") {
            apiCounter().increment()

            val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
            val azp = callerPrincipal.payload.getClaim("azp").asString()
            val endpoint = "/"
            secureLogger.info("EvalueringRoute: azp-claim i principal-token: {}", azp)
            val callId = call.callId ?: UUID.randomUUID().toString()
            val request = validerRequest(call.receive(), azp)

            val datagrunnlag = withContext(
                requestContextService.getCoroutineContext(
                    context = coroutineContext,
                    ytelse = finnYtelse(request.ytelse, azp)
                )
            ) {
                createDatagrunnlag.invoke(
                    request,
                    callId,
                    services,
                    azp
                )
            }
            try {
                val resultat = evaluerData(datagrunnlag)
                val response = lagResponse(
                    callid = callId,
                    versjonTjeneste = configuration.commitSha,
                    endpoint = endpoint,
                    datagrunnlag = datagrunnlag,
                    resultat = resultat
                )
                publishMedlemskapVurdertEvent(callId, response)
                loggResponse(request.fnr, response)

                call.respond(response)
            } catch (t: Throwable) {
                loggError(fnr = request.fnr, datagrunnlag = datagrunnlag, endpoint = endpoint, throwable = t)
                throw t
            }
        }

        post("/kafka") {
            val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
            val azp = callerPrincipal.payload.getClaim("azp").asString()
            val endpoint = "kafka"
            val callId = call.callId ?: UUID.randomUUID().toString()
            val request = validerRequest(call.receive(), azp)

            val datagrunnlag = withContext(
                requestContextService.getCoroutineContext(
                    context = coroutineContext,
                    ytelse = finnYtelse(request.ytelse, azp)
                )
            ) {

                createDatagrunnlag.invoke(
                    request,
                    callId,
                    services,
                    azp
                )
            }
            try {
                val resultat = evaluerData(datagrunnlag)

                val response = lagResponse(
                    callid = callId,
                    versjonTjeneste = configuration.commitSha,
                    endpoint = endpoint,
                    datagrunnlag = datagrunnlag,
                    resultat = resultat
                )

                publishMedlemskapVurdertEvent(callId, response)
                loggResponse(request.fnr, response, endpoint)
                call.respond(response)
            } catch (t: Throwable) {
                loggError(fnr = request.fnr, datagrunnlag = datagrunnlag, endpoint = endpoint, throwable = t)
                throw t
            }
        }
        // kjører regel motor, men publiserer ikke resultat til kafka
        post("/kafka_v2") {
            val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
            val azp = callerPrincipal.payload.getClaim("azp").asString()
            val endpoint = "kafka_v2"
            val callId = call.callId ?: UUID.randomUUID().toString()
            val request = validerRequest(call.receive(), azp)

            val datagrunnlag = withContext(
                requestContextService.getCoroutineContext(
                    context = coroutineContext,
                    ytelse = finnYtelse(request.ytelse, azp)
                )
            ) {

                createDatagrunnlag.invoke(
                    request,
                    callId,
                    services,
                    azp
                )
            }
            try {
                val resultat = evaluerData(datagrunnlag)

                val response = lagResponse(
                    callid = callId,
                    versjonTjeneste = configuration.commitSha,
                    endpoint = endpoint,
                    datagrunnlag = datagrunnlag,
                    resultat = resultat
                )
                loggResponse(request.fnr, response, endpoint)
                call.respond(response)
            } catch (t: Throwable) {
                loggError(fnr = request.fnr, datagrunnlag = datagrunnlag, endpoint = endpoint, throwable = t)
                throw t
            }
        }
        // kjører regel motor, men publiserer ikke resultat til kafka
        post("/vurdering") {
            val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
            val azp = callerPrincipal.payload.getClaim("azp").asString()
            val endpoint = "vurdering"
            val callId = call.callId ?: UUID.randomUUID().toString()
            val request = validerRequest(call.receive(), azp)

            val datagrunnlag = withContext(
                requestContextService.getCoroutineContext(
                    context = coroutineContext,
                    ytelse = finnYtelse(request.ytelse, azp)
                )
            ) {

                createDatagrunnlag.invoke(
                    request,
                    callId,
                    services,
                    azp
                )
            }
            try {
                val resultat = evaluerData(datagrunnlag)

                val response = lagResponse(
                    callid = callId,
                    versjonTjeneste = configuration.commitSha,
                    endpoint = endpoint,
                    datagrunnlag = datagrunnlag,
                    resultat = resultat
                )
                loggResponse(request.fnr, response, endpoint)
                call.respond(response)
            } catch (t: Throwable) {
                loggError(fnr = request.fnr, datagrunnlag = datagrunnlag, endpoint = endpoint, throwable = t)
                throw t
            }
        }
        post("/brukersporsmaal") {
            val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
            val azp = callerPrincipal.payload.getClaim("azp").asString()
            val endpoint = "brukerspørsmål"
            val callId = call.callId ?: UUID.randomUUID().toString()
            val request = validerRequest(call.receive(), azp)

            val datagrunnlag = withContext(
                requestContextService.getCoroutineContext(
                    context = coroutineContext,
                    ytelse = finnYtelse(request.ytelse, azp)
                )
            ) {

                createDatagrunnlag.invoke(
                    request,
                    callId,
                    services,
                    azp
                )
            }
            try {
                val resultat = evaluerData(datagrunnlag)

                val response = lagResponse(
                    callid = callId,
                    versjonTjeneste = configuration.commitSha,
                    endpoint = endpoint,
                    datagrunnlag = datagrunnlag,
                    resultat = resultat
                )
                loggResponse(request.fnr, response, endpoint)
                call.respond(response)
            } catch (t: Throwable) {
                loggError(fnr = request.fnr, datagrunnlag = datagrunnlag, endpoint = endpoint, throwable = t)
                throw t
            }
        }
    }
}

private fun publishMedlemskapVurdertEvent(callId: String, response: Response) {
    val producer = Producer().createProducer((Configuration().kafkaConfig))
    val futureresult = producer.send(createRecord(TOPIC, callId, objectMapper.writeValueAsString(response)))
    futureresult.get()
    producer.close()
    logger.info(
        "kafka request with id $callId processed ok and response published to $TOPIC ", kv("callId", callId)
    )
}

fun Routing.evalueringTestRoute(
    services: Services,
    configuration: Configuration,
    requestContextService: RequestContextService,
    createDatagrunnlag: suspend (request: Request, callId: String, services: Services, clientId: String?) -> Datagrunnlag
) {
    logger.info("autentiserer IKKE kallet")
    post("/") {
        apiCounter().increment()
        val callId = call.callId ?: UUID.randomUUID().toString()
        val request = validerRequest(call.receive(), Ytelse.toMedlemskapClientId())

        val endpoint = "/"

        val datagrunnlag = withContext(
            requestContextService.getCoroutineContext(
                context = coroutineContext,
                ytelse = finnYtelse(request.ytelse, Ytelse.toMedlemskapClientId())
            )
        ) {
            createDatagrunnlag.invoke(
                request,
                callId,
                services,
                Ytelse.toMedlemskapClientId()
            )
        }
        val resultat = evaluerData(datagrunnlag)

        val response = lagResponse(
            callid = callId,
            versjonTjeneste = configuration.commitSha,
            endpoint = endpoint,
            datagrunnlag = datagrunnlag,
            resultat = resultat
        )

        loggResponse(request.fnr, response)

        call.respond(response)
    }
}

private fun lagResponse(callid: String, datagrunnlag: Datagrunnlag, resultat: Resultat, versjonTjeneste: String, endpoint: String): Response {
    return Response(
        vurderingsID = callid,
        tidspunkt = LocalDateTime.now(),
        versjonRegler = "v1",
        versjonTjeneste = versjonTjeneste,
        kanal = endpoint,
        datagrunnlag = datagrunnlag,
        resultat = resultat
    )
}

private fun loggResponse(fnr: String, response: Response, endpoint: String = "/") {
    val startDatoForYtelse = response.datagrunnlag.startDatoForYtelse
    val kontrollperiode:Kontrollperiode = Kontrollperiode(startDatoForYtelse.minusMonths(12),startDatoForYtelse)
    val resultat = response.resultat
    val årsaker = resultat.årsaker
    val årsakerSomRegelIdStr = årsaker.map { it.regelId.toString() }
    val årsak = årsaker.map { it.regelId.toString() }.firstOrNull()
    val aarsaksAnt = årsaker.size
    runCatching {
        secureLogger.info(
            "{} konklusjon gitt for bruker {}, ytelse {}", resultat.svar.name, fnr, response.datagrunnlag.ytelse,
            kv("fnr", fnr),
            kv("orgnummer", response.datagrunnlag.gyldigeOrgnummer()),
            kv("fom", response.datagrunnlag.periode.fom.toString()),
            kv("tom", response.datagrunnlag.periode.tom.toString()),
            kv("førsteDagForYtelse", response.datagrunnlag.førsteDagForYtelse.toString()),
            kv("brukerInput", response.datagrunnlag.brukerinput.toString()),
            kv("startdatoForYtelse", response.datagrunnlag.startDatoForYtelse.toString()),
            kv("ytelse", response.datagrunnlag.ytelse),
            kv("svar", response.resultat.svar),
            kv("årsak", årsak),
            kv("årsaker", årsakerSomRegelIdStr),
            kv("aarsaksAnt", aarsaksAnt),
            kv("aarsaker", årsakerSomRegelIdStr.toString()),
            kv("statsborgerskap", response.datagrunnlag.gyldigeStatsborgerskap().toString()),
            kv("statsborgerskapAnt", response.datagrunnlag.gyldigeStatsborgerskap().size),
            kv("erTredjelandsborger", response.resultat.erTredjelandsborger()),
            kv("erEosBorger", response.resultat.erEøsBorger()),
            kv("erNorskBorger", response.resultat.erNorskBorger()),
            kv("erTredjelandsborgerMedEØSFamilie", response.resultat.erFamilieEOS()),
            kv("AaRegUtenlandsoppholdLandkode", response.datagrunnlag.gyldigeAaRegUtenlandsopphold()),
            kv("AaRegUtenlandsoppsholdPeriodeFom", response.datagrunnlag.gyldigeAaRegUtenlandsoppholdPeriodeFom().toString()),
            kv("AaRegUtenlandsoppsholdPeriodeTom", response.datagrunnlag.gyldigeAaRegUtenlandsoppholdPeriodeTom().toString()),
            kv("skipsinfo", response.datagrunnlag.kombinasjonAvSkipsregisterFartsomradeOgSkipstype()),
            kv("response", objectMapper.writeValueAsString(response)),
            kv("gjeldendeOppholdsstatus", response.datagrunnlag.oppholdstillatelse?.gjeldendeOppholdsstatus.toString()),
            kv("arbeidsadgangtype", response.datagrunnlag.oppholdstillatelse?.arbeidsadgang?.arbeidsadgangType.toString()),
            kv("fagsak_id", response.datagrunnlag.dokument.alleFagsakIDer()),
            kv("har_dokument", response.datagrunnlag.dokument.harDokument()),
            kv("har_innflytting", response.datagrunnlag.pdlpersonhistorikk.harInnflyttingTilNorge()),
            kv("har_utflytting", response.datagrunnlag.pdlpersonhistorikk.harUtflyttingFraNorge()),
            kv("yrkeskoder", response.datagrunnlag.arbeidsforhold.alleAktiveYrkeskoderDerTomErNull()),
            kv("vektetStillingsProsent", response.datagrunnlag.arbeidsforhold.summVektetStilingsProsentIKontrollPeriode(kontrollperiode)),
            kv("har_medlperiode_uten_arbeidsforhold", response.datagrunnlag.harPeriodeUtenMedlemskapOgIkkeArbeidsforhold()),
            kv(
                "Antall_ansatte_hos_arbeidsgiver",
                response.datagrunnlag.arbeidsforhold.antallAnsatteHosArbeidsgivere(
                    Kontrollperiode.kontrollPeriodeForArbeidsforhold(response.datagrunnlag.startDatoForYtelse)
                )
            ),
            kv(
                "antall_ansatte_for_juridiske_enheter",
                response.datagrunnlag.arbeidsforhold.antallAnsatteHosArbeidsgiversJuridiskeEnheter(
                    Kontrollperiode.kontrollPeriodeForArbeidsforhold(response.datagrunnlag.startDatoForYtelse)
                )
            ),
            kv("endpoint", endpoint)
            // kv("regleroverstyrt", response.resultat.erReglerOverstyrt())
        )
    }.onFailure {
        loggError(fnr, datagrunnlag = response.datagrunnlag, endpoint, it)
    }

    if (årsaker.isNotEmpty()) {
        uavklartPåRegel(årsaker.first(), response.datagrunnlag.ytelse.name()).increment()
    }
}

private fun loggError(fnr: String, datagrunnlag: Datagrunnlag, endpoint: String = "/", throwable: Throwable) {
    secureLogger.error(
        "teknisk feil i regelkjøring for bruker {}, ytelse {}", fnr, datagrunnlag.ytelse,
        kv("fnr", fnr),
        kv("fom", datagrunnlag.periode.fom.toString()),
        kv("tom", datagrunnlag.periode.tom.toString()),
        kv("førsteDagForYtelse", datagrunnlag.førsteDagForYtelse.toString()),
        kv("brukerInput", datagrunnlag.brukerinput.toString()),
        kv("startdatoForYtelse", datagrunnlag.startDatoForYtelse.toString()),
        kv("ytelse", datagrunnlag.ytelse),
        kv("statsborgerskap", datagrunnlag.gyldigeStatsborgerskap().toString()),
        kv("statsborgerskapAnt", datagrunnlag.gyldigeStatsborgerskap().size),
        kv("datagrunnlag", objectMapper.writeValueAsString(datagrunnlag)),
        kv("endpoint", endpoint),
        kv("stacktrace", throwable.stackTrace)
    )
}

private fun validerRequest(request: Request, azp: String): Request {
    val ytelse = finnYtelse(request.ytelse, azp)
    if (ytelse != Ytelse.SYKEPENGER && request.førsteDagForYtelse == null) {
        throw UgyldigRequestException("Første dag for ytelse kan ikke være null (inputperiode skal ikke lenger brukes)", ytelse)
    }

    if (request.periode.tom.isBefore(request.periode.fom)) {
        throw UgyldigRequestException("Periode tom kan ikke være før periode fom", ytelse)
    }

    if (!gyldigFnr(request.fnr)) {
        throw UgyldigRequestException("Ugyldig fødselsnummer", ytelse)
    }

    return request
}

fun finnYtelse(ytelseFraRequest: Ytelse?, clientId: String?) =
    (ytelseFraRequest ?: Ytelse.fromClientId(clientId))
        ?: throw KonsumentIkkeFunnet("Fant ikke clientId i mapping til ytelse. Ta kontakt med medlemskap-teamet for tilgang til tjenesten.")

fun evaluerData(datagrunnlag: Datagrunnlag): Resultat =
    Hovedregler(datagrunnlag).kjørHovedregler()

private fun createRecord(topic: String, key: String = UUID.randomUUID().toString(), value: String): ProducerRecord<String, String> {
    return ProducerRecord(topic, key, value)
}
