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

import io.micrometer.core.instrument.step.StepRegistryConfig

/**
 * Configuration for [SensuInfluxMeterRegistry].
 * Modified from [io.micrometer.influx.InfluxConfig].
 *
 * @see [https://github.com/micrometer-metrics/micrometer/tree/v1.3.5/implementations/micrometer-registry-influx](https://github.com/micrometer-metrics/micrometer/tree/v1.3.5/implementations/micrometer-registry-influx)
 */
interface SensuInfluxConfig : StepRegistryConfig {
    override fun prefix(): String {
        return "sensuinflux"
    }

    /**
     * @return The db to send metrics to. Defaults to "default".
     */
    fun db(): String {
        return "default"
    }

    /**
     * @return The hostname for the Sensu backend. The default is `sensu.nais`.
     */
    fun hostname(): String {
        return "sensu.nais"
    }

    /**
     * @return The port for the Sensu backend. The default is `3030`.
     */
    fun port(): Int {
        return 3030
    }

    /**
     * @return The name of the metrics for the Sensu backend. Default is "test-events", but should be overriden.
     */
    fun sensuName(): String {
        return "test-events"
    }
}
