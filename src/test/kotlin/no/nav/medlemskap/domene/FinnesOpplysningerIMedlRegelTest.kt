package no.nav.medlemskap.domene

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.v1.registrerteOpplysninger.FinnesOpplysningerIMedlRegel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FinnesOpplysningerIMedlRegelTest {
    @Test
    fun `Regel A skal ikke slå ut for MEDL perioder som er mer en 1 år gamle OG for perioder med status AVSLÅTT `() {
        val fileContent = this::class.java.classLoader.getResource("Regel_A_Invalid_MEDL_Perioder.json").readText(Charsets.UTF_8)
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        val datagrunnlag = mapper.readValue<Datagrunnlag>(fileContent)
        var resultat = FinnesOpplysningerIMedlRegel.fraDatagrunnlag(datagrunnlag).utfør()

        Assertions.assertEquals(Svar.NEI, resultat.svar)
    }
    @Test
    fun `Regel A skal  slå ut for MEDL perioder som IKKE mer en 1 år gamle OG for perioder med status IKKE AVSLÅTT `() {
        val fileContent = this::class.java.classLoader.getResource("Regel_A_GYLDIG_MEDL_Perioder_i_kontrollPeriode.json").readText(Charsets.UTF_8)
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        val datagrunnlag = mapper.readValue<Datagrunnlag>(fileContent)
        var resultat = FinnesOpplysningerIMedlRegel.fraDatagrunnlag(datagrunnlag).utfør()

        Assertions.assertEquals(Svar.JA, resultat.svar)
    }
}
