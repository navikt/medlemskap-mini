package no.nav.medlemskap.common

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.common.exceptions.*
import v1.mt_1067_nav.no.udi.HentPersonstatusFault

private val logger = KotlinLogging.logger { }
private val secureLogger = KotlinLogging.logger("tjenestekall")

fun StatusPagesConfig.exceptionHandler() {
    exception<GraphqlError> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.InternalServerError) {
            "Feil fra graphql i ${cause.system}: ${cause.errorAsJson()} for konsument ${cause.ytelse}"
        }
    }

    exception<IdenterIkkeFunnet> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.NotFound) {
            "Fant ingen aktør-id for fødselsnummer for konsument ${cause.ytelse}"
        }
    }

    exception<PersonIkkeFunnet> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.NotFound) {
            "Person ikke funnet i ${cause.system} for konsument ${cause.ytelse}"
        }
    }

    exception<Sikkerhetsbegrensing> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.Forbidden) {
            "Personen har sikkerhetsbegrensing i ${cause.system}"
        }
    }

    exception<ClientRequestException> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.InternalServerError) {
            val url = cause.response.call.request.url
            "Kall mot $url feilet"
        }
    }

    exception<ServerResponseException> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.InternalServerError) {
            val url = cause.response.call.request.url
            "Kall mot $url feilet"
        }
    }

    exception<UgyldigRequestException> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.BadRequest) {
            "${cause.message!!} for konsument ${cause.ytelse}"
        }
    }

    exception<HentPersonstatusFault> { call, cause ->

        when (cause.faultInfo.kodeId) {
            10003 -> call.logErrorAndRespond(cause, HttpStatusCode.BadRequest) {
                "Person ikke funnet i UDI-tjenesten"
            }
            10623 -> call.logErrorAndRespond(cause, HttpStatusCode.BadRequest) {
                "Person er død og finnes ikke i UDI-tjenesten"
            }
            else -> call.logErrorAndRespond(cause, HttpStatusCode.ServiceUnavailable) {
                "Ukjent feil mot UDI tjenesten"
            }
        }
    }
    exception<GradertAdresseException> { call, cause ->

        call.logSecureWarningAndRespond(cause, HttpStatusCode.ServiceUnavailable) {
            "GradertAdresse. Lovme skal ikke  kalles for personer med kode 6/7"
        }
    }

    exception<BadRequestException> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.BadRequest) {
            cause.message!!
        }
    }

    exception<MissingKotlinParameterException> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.BadRequest) {
            cause.message!!
        }
    }

    exception<MismatchedInputException> { call, cause ->
        call.logErrorAndRespond(cause, HttpStatusCode.BadRequest) {
            cause.message!!
        }
    }

    exception<Throwable> { call, cause ->
        call.logErrorAndRespond(cause) {
            "An internal error occurred during routing: reason ${cause.message}"
        }
    }
}

private suspend inline fun ApplicationCall.logErrorAndRespond(
    cause: Throwable,
    status: HttpStatusCode = HttpStatusCode.InternalServerError,
    lazyMessage: () -> String
) {

    val message = lazyMessage()
    logger.error(
        message,
        kv("stacktrace", cause.stackTraceToString())
    )
    logger.error(cause.stackTraceToString())
    val response = HttpErrorResponse(
        url = this.request.uri,
        cause = cause.toString(),
        message = message,
        code = status,
        callId = getCorrelationId(callId)
    )
    this.respond(status, response)
}
private suspend inline fun ApplicationCall.logWarningAndRespond(
    cause: Throwable,
    status: HttpStatusCode = HttpStatusCode.InternalServerError,
    lazyMessage: () -> String
) {
    val message = lazyMessage()
    logger.warn(
        message,
        kv("cause", cause),
        kv("callId", callId)
    )
    val response = HttpErrorResponse(
        url = this.request.uri,
        cause = cause.toString(),
        message = message,
        code = status,
        callId = getCorrelationId(callId)
    )
    this.respond(status, response)
}
private suspend inline fun ApplicationCall.logSecureWarningAndRespond(
    cause: Throwable,
    status: HttpStatusCode = HttpStatusCode.InternalServerError,
    lazyMessage: () -> String
) {
    val message = lazyMessage()
    secureLogger.warn(
        message,
        kv("cause", cause),
        kv("callId", callId)
    )
    val response = HttpErrorResponse(
        url = this.request.uri,
        cause = cause.toString(),
        message = message,
        code = status,
        callId = getCorrelationId(callId)
    )
    this.respond(status, response)
}

internal data class HttpErrorResponse(
    val url: String,
    val message: String? = null,
    val cause: String? = null,
    val code: HttpStatusCode = HttpStatusCode.InternalServerError,
    val callId: CorrelationId? = null
)
