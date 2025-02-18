package no.nav.medlemskap.clients.medl

import io.github.resilience4j.retry.Retry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import mu.KotlinLogging
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.clients.runWithRetryAndMetrics
import no.nav.medlemskap.config.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MedlClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient,
    private val configuration: Configuration,
    private val httpClient: HttpClient,
    private val retry: Retry? = null
) {

    private val logger = KotlinLogging.logger { }

    suspend fun hentMedlemskapsunntak(ident: String, callId: String, fraOgMed: LocalDate? = null, tilOgMed: LocalDate? = null): List<MedlMedlemskapsunntak> {
        val token = azureAdClient.hentToken(configuration.register.medlScope)
        return runCatching {
            runWithRetryAndMetrics<List<MedlMedlemskapsunntak>>("Medl", "MedlemskapsunntakV1", retry) {
                httpClient.get() {
                    url("$baseUrl/api/v1/medlemskapsunntak")
                    header(HttpHeaders.Authorization, "Bearer ${token.token}")
                    header(HttpHeaders.Accept, ContentType.Application.Json)
                    header("Nav-Call-Id", callId)
                    header("Nav-Personident", ident)
                    fraOgMed?.let { parameter("fraOgMed", fraOgMed.tilIsoFormat()) }
                    tilOgMed?.let { parameter("tilOgMed", tilOgMed.tilIsoFormat()) }
                }.body()
            }
        }.fold(
            onSuccess = { it },
            onFailure = { error ->
                when (error) {
                    is ClientRequestException -> {
                        if (error.response.status.value == 404) {
                            listOf()
                        } else if (error.response.status.value == 401) {
                            logger.error("Error from MEDL: {}", error.response.headers.get("WWW-Authenticate"))
                            throw error
                        } else {
                            throw error
                        }
                    }
                    else -> throw error
                }
            }
        )
    }

    private fun LocalDate.tilIsoFormat() = this.format(DateTimeFormatter.ISO_DATE)

    suspend fun healthCheck(): HttpResponse {
        val token = azureAdClient.hentToken(configuration.register.medlScope)
        return httpClient.get {
            url("$baseUrl/api/ping")
            header(HttpHeaders.Authorization, "Bearer ${token.token}")
            header(HttpHeaders.Accept, ContentType.Application.Json)
        }
    }
}
