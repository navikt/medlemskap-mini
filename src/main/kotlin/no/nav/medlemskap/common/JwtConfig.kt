package no.nav.medlemskap.common

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import mu.KotlinLogging
import no.nav.medlemskap.config.AzureAdOpenIdConfiguration
import no.nav.medlemskap.config.Configuration
import java.net.URL
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger { }

class JwtConfig(val configuration: Configuration, azureAdOpenIdConfiguration: AzureAdOpenIdConfiguration) {

    companion object {
        const val REALM = "medlemskap-mini"
    }

    val jwkProvider: JwkProvider = JwkProviderBuilder(URL(azureAdOpenIdConfiguration.jwksUri))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    fun validate(credentials: JWTCredential): Principal? {
        logger.info { "Validerer JWT Credential" }
        return try {
            requireNotNull(credentials.payload.audience) { "Auth: Audience mangler i token" }
            require(credentials.payload.audience.contains(configuration.azureAd.jwtAudience)) { "Auth: Ugyldig audience i token" }
            JWTPrincipal(credentials.payload)
        } catch (e: Exception) {
            logger.error(e) { "Failed to validate token" }
            null
        }
    }
}
