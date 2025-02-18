/**
 * Copyright (c) 2019 NAV
 *
 *
 * MIT License
 *
 *
 * This file incorporates works covered by the following copyright and permission notice:
 * ---
 *
 *
 * Copyright 2017 Pivotal Software, Inc.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.nav.medlemskap.common.influx

import io.micrometer.core.instrument.*
import io.micrometer.core.instrument.Timer
import io.micrometer.core.instrument.step.StepMeterRegistry
import io.micrometer.core.instrument.util.*
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.Socket
import java.net.UnknownHostException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * MeterRegistry for Influx by way of Sensu
 * Modified from [io.micrometer.influx.InfluxMeterRegistry].
 *
 * @see [https://github.com/micrometer-metrics/micrometer/tree/v1.3.5/implementations/micrometer-registry-influx](https://github.com/micrometer-metrics/micrometer/tree/v1.3.5/implementations/micrometer-registry-influx)
 */
class SensuInfluxMeterRegistry private constructor(config: SensuInfluxConfig, clock: Clock, threadFactory: ThreadFactory) : StepMeterRegistry(config, clock) {
    private val config: SensuInfluxConfig
    private val logger = LoggerFactory.getLogger(SensuInfluxMeterRegistry::class.java)

    constructor(config: SensuInfluxConfig, clock: Clock) : this(config, clock, DEFAULT_THREAD_FACTORY) {}

    override fun start(threadFactory: ThreadFactory) {
        if (config.enabled()) {
            logger.info("publishing metrics to influx every " + TimeUtils.format(config.step()))
        }
        super.start(threadFactory)
    }

    override fun publish() {
        try {
            val hostname = config.hostname()
            val port = config.port()
            val sensuName = config.sensuName()
            for (batch in MeterPartition.partition(this, config.batchSize())) {
                val influxLineProtocolData = batch.stream()
                    .flatMap { meter: Meter ->
                        meter.match(
                            { gauge: Gauge -> writeGauge(gauge.id, gauge.value()) },
                            { counter: Counter -> writeCounter(counter.id, counter.count()) },
                            { timer: Timer -> writeTimer(timer) },
                            { summary: DistributionSummary -> writeSummary(summary) },
                            { timer: LongTaskTimer -> writeLongTaskTimer(timer) },
                            { gauge: TimeGauge -> writeGauge(gauge.id, gauge.value(baseTimeUnit)) },
                            { counter: FunctionCounter -> writeCounter(counter.id, counter.count()) },
                            { timer: FunctionTimer -> writeFunctionTimer(timer) }
                        ) { m: Meter -> writeMeter(m) }
                    }
                    .collect(Collectors.joining("\n"))
                val data = SensuEvent(sensuName, influxLineProtocolData)
                val startTime = System.currentTimeMillis()
                try {
                    Socket(hostname, port).use { socket ->
                        try {
                            OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8).use { writer ->
                                writer.write(data.json, 0, data.json.length)
                                writer.flush()
                                logger.debug("wrote {} bytes of data", data.json.length)
                                logger.debug("wrote {} to Sensu", data.json)
                            }
                        } catch (e: IOException) {
                            logger.error("Unable to send event {}", data, e)
                        }
                    }
                } catch (e: UnknownHostException) {
                    logger.error("Unknown host: {}:{} {}", hostname, port, e.message)
                } catch (e: IOException) {
                    logger.error("Unable to send event to {}:{}", hostname, port, e)
                } catch (e: Exception) {
                    logger.error("Unable to send event", e)
                } finally {
                    logger.info("Influx/sensu reporting time: {}", System.currentTimeMillis() - startTime)
                }
            }
        } catch (e: Throwable) {
            logger.error("failed to send metrics to influx", e)
        }
    }

    // VisibleForTesting
    fun writeMeter(m: Meter): Stream<String?> {
        val fields: MutableList<Field> = ArrayList()
        for (measurement in m.measure()) {
            val value = measurement.value
            if (!java.lang.Double.isFinite(value)) {
                continue
            }
            val fieldKey = measurement.statistic.tagValueRepresentation
                .replace("(.)(\\p{Upper})".toRegex(), "$1_$2").toLowerCase()
            fields.add(Field(fieldKey, value))
        }
        if (fields.isEmpty()) {
            return Stream.empty()
        }
        val id = m.id
        return Stream.of(influxLineProtocol(id, id.type.name.toLowerCase(), fields.stream()))
    }

    private fun writeLongTaskTimer(timer: LongTaskTimer): Stream<String?> {
        val fields = Stream.of(
            Field("active_tasks", timer.activeTasks().toDouble()),
            Field("duration", timer.duration(baseTimeUnit))
        )
        return Stream.of(influxLineProtocol(timer.id, "long_task_timer", fields))
    }

    // VisibleForTesting
    fun writeCounter(id: Meter.Id, count: Double): Stream<String?> {
        return if (java.lang.Double.isFinite(count)) {
            Stream.of(influxLineProtocol(id, "counter", Stream.of(Field("value", count))))
        } else Stream.empty()
    }

    // VisibleForTesting
    fun writeGauge(id: Meter.Id, value: Double?): Stream<String?> {
        return if (java.lang.Double.isFinite(value!!)) {
            Stream.of(influxLineProtocol(id, "gauge", Stream.of(Field("value", value))))
        } else Stream.empty()
    }

    private fun writeFunctionTimer(timer: FunctionTimer): Stream<String?> {
        val fields = Stream.of(
            Field("sum", timer.totalTime(baseTimeUnit)),
            Field("count", timer.count()),
            Field("mean", timer.mean(baseTimeUnit))
        )
        return Stream.of(influxLineProtocol(timer.id, "histogram", fields))
    }

    private fun writeTimer(timer: Timer): Stream<String?> {
        val fields = Stream.of(
            Field("sum", timer.totalTime(baseTimeUnit)),
            Field("count", timer.count().toDouble()),
            Field("mean", timer.mean(baseTimeUnit)),
            Field("upper", timer.max(baseTimeUnit))
        )
        return Stream.of(influxLineProtocol(timer.id, "histogram", fields))
    }

    private fun writeSummary(summary: DistributionSummary): Stream<String?> {
        val fields = Stream.of(
            Field("sum", summary.totalAmount()),
            Field("count", summary.count().toDouble()),
            Field("mean", summary.mean()),
            Field("upper", summary.max())
        )
        return Stream.of(influxLineProtocol(summary.id, "histogram", fields))
    }

    private fun influxLineProtocol(id: Meter.Id, metricType: String, fields: Stream<Field>): String {
        val tags = getConventionTags(id).stream()
            .filter { t: Tag -> StringUtils.isNotBlank(t.value) }
            .map { t: Tag -> "," + t.key + "=" + t.value }
            .collect(Collectors.joining(""))
        return (
            getConventionName(id) +
                tags + ",metric_type=" + metricType + " " +
                fields.map { obj: Field -> obj.toString() }.collect(Collectors.joining(",")) +
                " " + clock.wallTime() + "000000"
            )
    }

    override fun getBaseTimeUnit(): TimeUnit {
        return TimeUnit.NANOSECONDS
    }

    class Builder internal constructor(private val config: SensuInfluxConfig) {
        private var clock = Clock.SYSTEM
        private var threadFactory = DEFAULT_THREAD_FACTORY
        fun clock(clock: Clock): Builder {
            this.clock = clock
            return this
        }

        fun threadFactory(threadFactory: ThreadFactory): Builder {
            this.threadFactory = threadFactory
            return this
        }

        fun build(): SensuInfluxMeterRegistry {
            return SensuInfluxMeterRegistry(config, clock, threadFactory)
        }
    }

    internal inner class Field(val key: String, val value: Double) {
        override fun toString(): String {
            return key + "=" + DoubleFormat.decimalOrNan(value)
        }
    }

    internal class SensuEvent(sensuName: String, output: String) {
        val json: String

        init {
            json = "{" +
                "\"name\":\"" + sensuName + "\"," +
                "\"type\":\"metric\"," +
                "\"handlers\":[\"events_nano\"]," +
                "\"output\":\"" + output + "\"," +
                "\"status\":0" +
                "}"
        }
    }

    companion object {
        private val DEFAULT_THREAD_FACTORY: ThreadFactory = NamedThreadFactory("sensu-influx-metrics-publisher")
        fun builder(config: SensuInfluxConfig): Builder {
            return Builder(config)
        }
    }

    init {
        config().namingConvention(SensuInfluxNamingConvention())
        this.config = config
        start(threadFactory)
    }
}
