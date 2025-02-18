package no.nav.medlemskap.clients.pdl

import com.expediagroup.graphql.client.serialization.types.KotlinxGraphQLResponse
import com.expediagroup.graphql.client.types.GraphQLClientResponse
import io.github.resilience4j.retry.Retry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.clients.pdl.generated.HentIdenter
import no.nav.medlemskap.clients.pdl.generated.HentPerson
import no.nav.medlemskap.clients.runWithRetryAndMetrics
import no.nav.medlemskap.config.Configuration

class PdlClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient,
    private val configuration: Configuration,
    private val username: String,
    private val httpClient: HttpClient,
    private val retry: Retry? = null,
    private val pdlApiKey: String
) {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    suspend fun hentIdenterv2(fnr: String, callId: String): GraphQLClientResponse<HentIdenter.Result> {

        return runWithRetryAndMetrics("PDL", "HentIdenter", retry) {
            val token = azureAdClient.hentToken(configuration.register.pdlScope)
            val query = HentIdenter(
                variables = HentIdenter.Variables(
                    fnr,
                    null,
                    true
                )
            )
            val response: KotlinxGraphQLResponse<HentIdenter.Result> = httpClient.post() {
                url(baseUrl)
                setBody(query)
                header(HttpHeaders.Authorization, "Bearer ${token.token}")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Accept, ContentType.Application.Json)
                header("Nav-Call-Id", callId)
                header("Nav-Consumer-Id", configuration.azureAd.clientId)
                header("x-nav-apiKey", pdlApiKey)
            }.body()

            if (!response.errors.isNullOrEmpty()) {
                logger.error("PDL response errors: ${response.errors}")
                // TODO: utfør feil håndtering. Gjøres utenfor denne koden?
            }
            response
        }
    }

    suspend fun hentPersonV2(fnr: String, callId: String): GraphQLClientResponse<HentPerson.Result> {

        return runWithRetryAndMetrics("PDL", "HentPerson", retry) {
            val token = azureAdClient.hentToken(configuration.register.pdlScope)

            val query = HentPerson(
                variables = HentPerson.Variables(fnr, true, true)
            )
            val response: KotlinxGraphQLResponse<HentPerson.Result> = httpClient.post() {
                url(baseUrl)
                setBody(query)
                header(HttpHeaders.Authorization, "Bearer ${token.token}")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Accept, ContentType.Application.Json)
                header("behandlingsnummer", "B451")
                header("Nav-Call-Id", callId)
                header("Nav-Consumer-Id", configuration.azureAd.clientId)
                header("x-nav-apiKey", pdlApiKey)
            }.body()

            if (!response.extensions.isNullOrEmpty()) {
                logger.warn(
                    "extension fra PDL:",
                    kv("extensions", response.extensions.toString())
                )
            }

            if (!response.errors.isNullOrEmpty()) {
                logger.error("PDL response errors: ${response.errors}")
                // TODO: utfør feil håndtering. Gjøres utenfor denne koden?
            }
            response
        }
    }

    suspend fun healthCheck(): HttpResponse {
        return httpClient.options {
            url(baseUrl)
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header("x-nav-apiKey", pdlApiKey)
            header("Nav-Consumer-Id", configuration.azureAd.clientId)
        }
    }
}
