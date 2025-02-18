package no.nav.medlemskap.clients

import no.nav.medlemskap.clients.aareg.AaRegClient
import no.nav.medlemskap.clients.azuread.AzureAdClient
import no.nav.medlemskap.clients.ereg.EregClient
import no.nav.medlemskap.clients.medl.MedlClient
import no.nav.medlemskap.clients.oppgave.OppgaveClient
import no.nav.medlemskap.clients.pdl.PdlClient
import no.nav.medlemskap.clients.saf.SafClient
import no.nav.medlemskap.clients.sts.StsRestClient
import no.nav.medlemskap.clients.udi.UdiClient
import no.nav.medlemskap.common.cioHttpClient
import no.nav.medlemskap.common.healthcheck.HealthReporter
import no.nav.medlemskap.common.healthcheck.HealthService
import no.nav.medlemskap.common.healthcheck.HttpResponseHealthCheck
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.config.retryRegistry
import no.nav.medlemskap.services.aareg.AaRegService
import no.nav.medlemskap.services.medl.MedlService
import no.nav.medlemskap.services.oppgave.OppgaveService
import no.nav.medlemskap.services.pdl.PdlService
import no.nav.medlemskap.services.saf.SafService

class Services(val configuration: Configuration) {
    private val medlClient: MedlClient
    val medlService: MedlService
    private val aaRegClient: AaRegClient
    val aaRegService: AaRegService
    private val safClient: SafClient
    val safService: SafService
    private val oppgaveClient: OppgaveClient
    val oppgaveService: OppgaveService
    private val pdlClient: PdlClient
    val pdlService: PdlService
    private val eregClient: EregClient
    val udiClient: UdiClient

    val healthService: HealthService
    private val healthReporter: HealthReporter
    private val healthRetry = retryRegistry.retry("Helsesjekker")

    private val stsRetry = retryRegistry.retry("STS")

    init {

        val stsRestClient = StsRestClient(
            baseUrl = configuration.sts.restUrl,
            username = configuration.sts.username,
            password = configuration.sts.password,
            apiKey = configuration.sts.apiKey,
            retry = stsRetry,
            httpClient = cioHttpClient
        )

        val azureAdClient = AzureAdClient(configuration)

        val restClients = RestClients(
            stsClientRest = stsRestClient,
            azureAdClient = azureAdClient,
            configuration = configuration
        )

        medlClient = restClients.medl2(configuration.register.medl2BaseUrl)
        medlService = MedlService(medlClient)
        pdlClient = restClients.pdl(configuration.register.pdlBaseUrl)
        pdlService = PdlService(pdlClient, configuration.cluster)
        eregClient = restClients.ereg(configuration.register.eregBaseUrl)
        aaRegClient = restClients.aaReg(configuration.register.aaRegBaseUrl)
        aaRegService = AaRegService(aaRegClient, eregClient)
        safClient = restClients.saf(configuration.register.safBaseUrl)
        safService = SafService(safClient)
        oppgaveClient = restClients.oppgaver(configuration.register.oppgaveBaseUrl)
        oppgaveService = OppgaveService(oppgaveClient)
        udiClient = restClients.udi(configuration.register.udiBaseUrl)

        healthService = HealthService(
            setOf(
                // HttpResponseHealthCheck("AaReg", { aaRegClient.healthCheck() }),
                HttpResponseHealthCheck("Medl", { medlClient.healthCheck() }, healthRetry),
                // HttpResponseHealthCheck("Oppg", { oppgaveClient.healthCheck() }, healthRetry),
                HttpResponseHealthCheck("PDL", { pdlClient.healthCheck() }, healthRetry),
                HttpResponseHealthCheck("SAF", { safClient.healthCheck() }, healthRetry),
                // HttpResponseHealthCheck("STS", { stsRestClient.healthCheck() }, healthRetry),
            )
        )

        healthReporter = HealthReporter(healthService)
    }
}
