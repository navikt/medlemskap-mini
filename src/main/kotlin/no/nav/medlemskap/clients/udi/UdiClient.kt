package no.nav.medlemskap.clients.udi

import io.github.resilience4j.retry.Retry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.clients.runWithRetryAndMetrics
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.domene.Oppholdstillatelse

class UdiClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient,
    private val httpClient: HttpClient,
    private val retry: Retry? = null
) {
    val configuration: Configuration = Configuration()

    suspend fun oppholdstillatelse(udiRequest: UdiRequest, callId: String): Oppholdstillatelse {
        val token = azureAdClient.hentToken(configuration.register.udiScope)
        return runWithRetryAndMetrics("UDI-proxy", "Oppholdstillatelse", retry) {
            httpClient.post {
                url("$baseUrl/udi/person")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${token.token}")
                header("Nav-Call-Id", callId)
                header("X-Correlation-Id", callId)
                setBody(udiRequest)
            }.body()
        }
    }
}
