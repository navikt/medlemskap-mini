package no.nav.medlemskap.clients.sts

import io.github.resilience4j.retry.Retry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import no.nav.medlemskap.clients.Token
import no.nav.medlemskap.clients.runWithRetryAndMetrics
import no.nav.medlemskap.clients.shouldBeRenewed
import java.util.*

class StsRestClient(
    private val baseUrl: String,
    private val username: String,
    private val password: String,
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val retry: Retry? = null
) {
    private var cachedOidcToken: Token? = null
    private var cachedSamlToken: Token? = null

    suspend fun oidcToken(): String {
        if (cachedOidcToken.shouldBeRenewed()) {
            cachedOidcToken = runWithRetryAndMetrics<Token>("STS", "TokenV1", retry) {
                httpClient.get() {
                    url("$baseUrl/rest/v1/sts/token")
                    header(HttpHeaders.Authorization, "Basic ${credentials()}")
                    header("x-nav-apiKey", apiKey)
                    parameter("grant_type", "client_credentials")
                    parameter("scope", "openid")
                }.body()
            }
        }

        return cachedOidcToken!!.token
    }

    // Skrur av pga. 401 meldinger i kibana
    /*
    suspend fun healthCheck(): HttpResponse {
        return httpClient.options {
            url("$baseUrl/ping")
            header("Nav-Consumer-Id", username)
            header("x-nav-apiKey", apiKey)
        }
    }
*/

    suspend fun samlToken(): String {
        if (cachedSamlToken.shouldBeRenewed()) {
            cachedSamlToken = runWithRetryAndMetrics<Token>("STS", "SamlTokenV1", retry) {
                httpClient.get() {
                    url("$baseUrl/rest/v1/sts/samltoken")
                    header(HttpHeaders.Authorization, "Basic ${credentials()}")
                }.body()
            }
        }

        val urldecodedBase64 = cachedSamlToken!!.token
            .replace('-', '+')
            .replace('_', '/')
            .plus("=".repeat(cachedSamlToken!!.token.length % 4))

        return String(Base64.getDecoder().decode(urldecodedBase64))
    }

    private fun credentials() = Base64.getEncoder().encodeToString("$username:$password".toByteArray(Charsets.UTF_8))
}
