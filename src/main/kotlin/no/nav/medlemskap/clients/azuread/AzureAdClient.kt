package no.nav.medlemskap.clients.azuread

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import no.nav.medlemskap.clients.Token
import no.nav.medlemskap.common.apacheHttpClient
import no.nav.medlemskap.config.Configuration

class AzureAdClient(private val configuration: Configuration) {

    suspend fun hentToken(scope: String): Token {
        val formUrlEncode = listOf(
            "client_id" to configuration.azureAd.clientId,
            "scope" to scope,
            "client_secret" to configuration.azureAd.clientSecret,
            "grant_type" to "client_credentials"
        ).formUrlEncode()

        return apacheHttpClient.post {
            url(configuration.azureAd.tokenEndpoint)
            setBody(TextContent(formUrlEncode, ContentType.Application.FormUrlEncoded))
        }.body()
    }
}
