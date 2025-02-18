package no.nav.medlemskap.common

import io.micrometer.core.instrument.*
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import io.micrometer.prometheusmetrics.PrometheusRenameFilter
import no.nav.medlemskap.common.influx.SensuInfluxConfig
import no.nav.medlemskap.common.influx.SensuInfluxMeterRegistry
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.Ytelse.Companion.name
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.common.Årsak
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.truncate

fun configurePrometheusMeterRegistry(): PrometheusMeterRegistry {
    val prometheusRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    prometheusRegistry.config().meterFilter(PrometheusRenameFilter())
//    prometheusRegistry.config().meterFilter(MeterFilter.denyNameStartsWith(/*"regel_call"));
//    prometheusRegistry.config().meterFilter(MeterFilter.denyNameStartsWith("api_hit_counter"));*/
    Metrics.globalRegistry.add(prometheusRegistry)
    return prometheusRegistry
}

fun configureSensuInfluxMeterRegistry(): SensuInfluxMeterRegistry {
    val config: SensuInfluxConfig = object : SensuInfluxConfig {
        override fun step(): Duration? {
            return Duration.ofSeconds(10)
        }

        override fun sensuName(): String = "medlemskap-mini-events"

        override operator fun get(k: String?): String? {
            return null // accept the rest of the defaults
        }
    }

    val influxMeterRegistry = SensuInfluxMeterRegistry(config, Clock.SYSTEM)
    influxMeterRegistry.config().meterFilter(
        MeterFilter.denyUnless {
            it.name.startsWith("api_hit_counter") ||
                it.name.startsWith("regel_calls_influx")
        }
    )
    influxMeterRegistry.config().commonTags(defaultInfluxTags())
    Metrics.globalRegistry.add(influxMeterRegistry)
    return influxMeterRegistry
}

fun defaultInfluxTags() = listOf(
    Tag.of("application", getenv("NAIS_APP_NAME", "medlemskap-mini")),
    Tag.of("cluster", getenv("NAIS_CLUSTER_NAME", "dev-gcp")),
    Tag.of("namespace", getenv("NAIS_NAMESPACE", "default"))
)

private fun getenv(env: String, defaultValue: String): String {
    return if (System.getenv(env) != null) System.getenv(env) else defaultValue
}

fun regelCounter(regel: String, status: String, ytelse: String): Counter = Counter
    .builder("regel_calls_total")
    .tags("regel", regel, "status", status, "ytelse", ytelse)
    .description("counter for ja, nei, uavklart for regel calls")
    .register(Metrics.globalRegistry)

fun regelInfluxCounter(regel: String, status: String, ytelse: String): Counter = Counter
    .builder("regel_calls_influx")
    .tags("regelnummer", regel, "status", status, "ytelse", ytelse)
    .description("counter for ja, nei, uavklart for regler")
    .register(Metrics.globalRegistry)

fun ytelseCounter(ytelse: String): Counter = Counter
    .builder("ytelse_total")
    .tags("ytelse", ytelse)
    .description("counter for ytelser")
    .register(Metrics.globalRegistry)

fun regelUendretCounterMidlertidig(regelId: RegelId, svar: Svar, ytelse: Ytelse): Counter = Counter // Når dekning kan returneres av tjenesten kan denne fjernes.
    .builder("regel_uendret_arbeidsforhold")
    .tags("regel", regelId.identifikator, "svar", svar.name, "ytelse", ytelse.name())
    .description("counter for ja eller nei for regel 1.4")
    .register(Metrics.globalRegistry)

fun stillingsprosentCounter(stillingsprosent: Double, ytelse: String): Counter =
    if (stillingsprosent < 100.0) {
        Counter.builder("stillingsprosent_deltid")
            .tags("stillingsprosent", getStillingsprosentIntervall(stillingsprosent), "ytelse", ytelse)
            .description("counter for fordeling av stillingsprosenter")
            .register(Metrics.globalRegistry)
    } else {
        Counter.builder("stillingsprosent_heltid")
            .tags("ytelse", ytelse)
            .description("counter for antall brukere med heltidsstilling")
            .register(Metrics.globalRegistry)
    }

fun samletStillingsprosentCounter(stillingsprosent: Double, ytelse: String): Counter =
    Counter.builder("stillingsprosent_samlet")
        .tags("stillingsprosent", getStillingsprosentIntervall(stillingsprosent), "ytelse", ytelse)
        .description("counter for fordeling av samlet stillingsprosent")
        .register(Metrics.globalRegistry)

fun merEnn10ArbeidsforholdCounter(ytelse: Ytelse): Counter = Counter
    .builder("over_10_arbeidsforhold")
    .tags("ytelse", ytelse.name())
    .description("counter for brukere med flere enn 10 arbeidsforhold")
    .register(Metrics.globalRegistry)

fun usammenhengendeArbeidsforholdCounter(ytelse: Ytelse): Counter = Counter
    .builder("usammenhengende_arbeidsforhold")
    .tags("ytelse", ytelse.name())
    .description("counter for usammenhengende arbeidsforhold")
    .register(Metrics.globalRegistry)

fun harIkkeArbeidsforhold12MndTilbakeCounter(ytelse: Ytelse): Counter = Counter
    .builder("ingen_arbeidsforhold_fra_12_mnd_tilbake")
    .tags("ytelse", ytelse.name())
    .description("counter for brukere som ikke har arbeidsforhold som starter 12 mnd tilbake")
    .register(Metrics.globalRegistry)

fun antallDagerUtenArbeidsforhold(ytelse: Ytelse): DistributionSummary = DistributionSummary
    .builder("antall_dager_uten_arbeidsforhold")
    .publishPercentileHistogram()
    .tags("ytelse", ytelse.name())
    .description("")
    .register(Metrics.globalRegistry)

fun antallDagerMellomArbeidsforhold(ytelse: Ytelse): DistributionSummary = DistributionSummary
    .builder("antall_dager_mellom_arbeidsforhold")
    .publishPercentileHistogram()
    .tags("ytelse", ytelse.name())
    .description("")
    .register(Metrics.globalRegistry)

fun dekningCounter(dekning: String, ytelse: String): Counter = Counter
    .builder("dekningstyper")
    .tags("dekningstyper", dekning, "ytelse", ytelse)
    .description("Ulike dekningskoder til brukere som har spurt tjenesten")
    .register(Metrics.globalRegistry)

fun uavklartPåRegel(årsak: Årsak, ytelse: String): Counter = Counter
    .builder("uavklart_for_regel")
    .tags("regel", "${årsak.regelId} - ${årsak.avklaring}", "ytelse", ytelse)
    .description("Regler som gir uavklart")
    .register(Metrics.globalRegistry)

private fun getStillingsprosentIntervall(stillingsprosent: Double): String {
    // Fjerner desimaler fremfor å runde av fordi regelsjekken godtar ikke f.eks. 24.9% stilling som høy nok til å regnes som 25% stilling.
    val stillingsprosentHeltall = truncate(stillingsprosent).toInt()
    when {
        0 > stillingsprosentHeltall -> return "N/A"
        0 == stillingsprosentHeltall -> return "0"
        (1..14).contains(stillingsprosentHeltall) -> return "1 - 14"
        (15..24).contains(stillingsprosentHeltall) -> return "15 - 24"
        (25..34).contains(stillingsprosentHeltall) -> return "25 - 34"
        (35..44).contains(stillingsprosentHeltall) -> return "35 - 44"
        (45..54).contains(stillingsprosentHeltall) -> return "45 - 54"
        (55..64).contains(stillingsprosentHeltall) -> return "55 - 64"
        (65..74).contains(stillingsprosentHeltall) -> return "65 - 74"
        (75..84).contains(stillingsprosentHeltall) -> return "75 - 84"
        (85..99).contains(stillingsprosentHeltall) -> return "85 - 99"
        (100..100).contains(stillingsprosentHeltall) -> return "100"
    }
    return "Over 100"
}

fun medlCounter(): Counter = Counter
    .builder("medl_counter")
    .description("Registrerer dersom det finnes en periode i medl")
    .register(Metrics.globalRegistry)

fun apiCounter(): Counter = Counter
    .builder("api_hit_counter")
    .description("Registers a counter for each hit to the api")
    .register(Metrics.globalRegistry)

fun clientTimer(service: String?, operation: String?): Timer =
    Timer.builder("client_calls_latency")
        .tags("service", service ?: "UKJENT", "operation", operation ?: "UKJENT")
        .description("latency for calls to other services")
        .publishPercentileHistogram()
        .register(Metrics.globalRegistry)

fun clientCounter(service: String?, operation: String?, status: String): Counter = Counter
    .builder("client_calls_total")
    .tags("service", service ?: "UKJENT", "operation", operation ?: "UKJENT", "status", status)
    .description("counter for failed or successful calls to other services")
    .register(Metrics.globalRegistry)

fun clientsGauge(client: String): AtomicInteger =
    AtomicInteger().apply {
        Gauge.builder("health_check_clients_status") { this }
            .description("Indikerer applikasjonens baksystemers helsestatus. 0 er OK, 1 indikerer feil.")
            .tags("client", client)
            .register(Metrics.globalRegistry)
    }

fun totalGauge(): AtomicInteger =
    AtomicInteger().apply {
        Gauge.builder("health_check_status") { this }
            .description("Indikerer applikasjonens helsestatus. 0 er OK, 1 indikerer feil.")
            .register(Metrics.globalRegistry)
    }
