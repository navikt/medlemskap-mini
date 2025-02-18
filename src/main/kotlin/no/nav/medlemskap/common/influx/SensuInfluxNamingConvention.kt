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

import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.config.NamingConvention
import java.util.regex.Pattern

/**
 * [NamingConvention] for Influx.
 * Modified from[io.micrometer.influx.InfluxNamingConvention].
 *
 * @see [https://github.com/micrometer-metrics/micrometer/tree/v1.3.5/implementations/micrometer-registry-influx](https://github.com/micrometer-metrics/micrometer/tree/v1.3.5/implementations/micrometer-registry-influx)
 */
class SensuInfluxNamingConvention
/**
 * By default, telegraf's configuration option for `metric_separator`
 * is an underscore, which corresponds to [NamingConvention.snakeCase].
 */
@JvmOverloads constructor(private val delegate: NamingConvention = NamingConvention.snakeCase) : NamingConvention {
    override fun name(name: String, type: Meter.Type, baseUnit: String?): String {
        return escape(delegate.name(name, type, baseUnit).replace("=", "_"))
    }

    override fun tagKey(key: String): String { // `time` cannot be a field key or tag key
        require(key != "time") { "'time' is an invalid tag key in InfluxDB" }
        return escape(delegate.tagKey(key))
    }

    override fun tagValue(value: String): String { // `time` cannot be a field key or tag key
        require(value != "time") { "'time' is an invalid tag value in InfluxDB" }
        return escape(delegate.tagValue(value))
    }

    private fun escape(string: String): String {
        return PATTERN_SPECIAL_CHARACTERS.matcher(string).replaceAll("\\\\$1")
    }

    companion object {
        // https://docs.influxdata.com/influxdb/v1.3/write_protocols/line_protocol_reference/#special-characters
        private val PATTERN_SPECIAL_CHARACTERS = Pattern.compile("([, =\"])")
    }
}
