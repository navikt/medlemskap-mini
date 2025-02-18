package no.nav.medlemskap.clients.udi

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import no.nav.medlemskap.domene.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UdiProxyTest {
    @Test
    fun `mapping til EØS eller EFTA opphold objekt`() {
        val fileContent = this::class.java.classLoader.getResource("udi-proxy-testdata/EOSellerEFTAOpphold.json").readText(Charsets.UTF_8)
        val res = JacksonParser().parse(fileContent)

        val forventetOppholdstillatelse = Oppholdstillatelse(
            uttrekkstidspunkt = null,
            foresporselsfodselsnummer = "12345678910",
            avgjoerelse = null,
            gjeldendeOppholdsstatus = GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                eosellerEFTAOpphold = EOSellerEFTAOpphold(
                    periode = Periode(
                        fom = LocalDate.of(2019, 2, 4),
                        tom = LocalDate.of(2024, 2, 4)
                    ),
                    eosellerEFTAGrunnlagskategoriOppholdsrettType = EOSellerEFTAGrunnlagskategoriOppholdsrettType.FAMILIE,
                    eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null,
                    eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT
                ),
                uavklart = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null
            ),
            arbeidsadgang = null,
            uavklartFlyktningstatus = null,
            harFlyktningstatus = null
        )

        assertEquals(res, forventetOppholdstillatelse)
    }
}

class JacksonParser {
    fun parse(jsonString: String): Oppholdstillatelse {
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        return mapper.readValue(jsonString, Oppholdstillatelse::class.java)
    }
}
