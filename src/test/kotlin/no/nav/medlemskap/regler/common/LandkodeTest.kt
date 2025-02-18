package no.nav.medlemskap.regler.common

import com.neovisionaries.i18n.CountryCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LandkodeTest {

    @Test
    fun `ISO 3166-1 Alpha-2 (To bokstaver) landkoder gir tre-bokstav`() {

        Assertions.assertEquals("NOR", CountryCode.getByCode("no".toUpperCase()).alpha3)
    }

    @Test
    fun `ISO 3166-1 Alpha-2 (To bokstaver) landkoder gir tre-bokstav med store bokstaver`() {

        Assertions.assertEquals("NOR", CountryCode.getByCode("NO").alpha3)
    }

    @Test
    fun `ISO 3166-1 Alpha-3 (Tre bokstaver) landkoder gir tre-bokstav med store bokstaver`() {

        Assertions.assertEquals("USA", CountryCode.getByCode("USA").alpha3)
    }

    @Test
    fun `ISO 3166-1 Alpha-2 NO gir NOR som alpha3`() {
        Assertions.assertEquals("NOR", CountryCode.NO.alpha3)
    }
}
