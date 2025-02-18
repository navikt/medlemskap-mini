package no.nav.medlemskap.domene

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isNull
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.medlemskap.common.objectMapper
import org.junit.jupiter.api.Test

internal class RequestTest {

    @Test
    fun `request skal kunne mappes selv om ytelse ikke er angitt`() {

        val json =
            """
        {
            "fnr": "123456789",
            "periode": {
                "fom": "2019-01-01",
                "tom": "2019-12-31"
            },
            "brukerinput": {
                "arbeidUtenforNorge": false
            }
        }    
            """.trimIndent()

        val objekt = objectMapper.readValue<Request>(json)

        assertThat(objekt.ytelse).isNull()
        assertThat(objekt.fnr).isEqualTo("123456789")
    }

    @Test
    fun `request skal kunne mappes når ytelse er angitt`() {

        val json =
            """
        {
            "fnr": "123456789",
            "periode": {
                "fom": "2019-01-01",
                "tom": "2019-12-31"
            },
            "brukerinput": {
                "arbeidUtenforNorge": false
            },
            "ytelse": "SYKEPENGER"
        }    
            """.trimIndent()

        val objekt = objectMapper.readValue<Request>(json)

        assertThat(objekt.ytelse).isEqualTo(Ytelse.SYKEPENGER)
        assertThat(objekt.fnr).isEqualTo("123456789")
    }

    @Test
    fun `mapping skal feile hvis ytelse er angitt feil`() {

        val json =
            """
        {
            "fnr": "123456789",
            "periode": {
                "fom": "2019-01-01",
                "tom": "2019-12-31"
            },
            "brukerinput": {
                "arbeidUtenforNorge": false
            },
            "ytelse": "IKKE-SYKEPENGER"
        }    
            """.trimIndent()

        assertThat {
            objectMapper.readValue<Request>(json)
        }.isFailure()
    }
}
