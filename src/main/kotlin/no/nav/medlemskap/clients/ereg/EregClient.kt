package no.nav.medlemskap.clients.ereg

import io.github.resilience4j.retry.Retry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import mu.KotlinLogging
import no.nav.medlemskap.clients.runWithRetryAndMetrics
import no.nav.medlemskap.config.Configuration

class EregClient(
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val configuration: Configuration,
    private val eregApiKey: String,
    private val retry: Retry? = null
) {
    private val logger = KotlinLogging.logger { }

    suspend fun hentOrganisasjon(orgnummer: String?, callId: String): Organisasjon {
        val organisasjonsInfo: Organisasjon = kotlin.runCatching {
            runWithRetryAndMetrics<Organisasjon>("Ereg", "hentOrganisasjon", retry) {
                httpClient.get() {
                    url("$baseUrl/v1/organisasjon/$orgnummer")
                    parameter("inkluderHierarki", true)
                    header(HttpHeaders.Accept, ContentType.Application.Json)
                    header("Nav-Call-Id", callId)
                    header("Nav-Consumer-Id", configuration.sts.username)
                    header("x-nav-apiKey", eregApiKey)
                }.body()
            }
        }.fold(
            onSuccess = { it },
            onFailure = { error ->
                when (error) {
                    is ClientRequestException -> {
                        if (error.response.status.value == 404) {
                            Organisasjon(
                                null,
                                null,
                                orgnummer,
                                null,
                                null,
                                null
                            )
                        } else {
                            throw error
                        }
                    }
                    else -> {
                        logger.error("${this.javaClass.name} failed with error ${error.message}")
                        throw error
                    }
                }
            }

        )
        return organisasjonsInfo
    }
}
