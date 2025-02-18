package no.nav.medlemskap.clients.pdl.mapper

import no.nav.medlemskap.domene.personhistorikk.Sivilstandstype
import no.nav.medlemskap.services.pdl.PdlSivilstandMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDate
import no.nav.medlemskap.clients.pdl.generated.enums.Sivilstandstype as PdlSivilstandstype
import no.nav.medlemskap.clients.pdl.generated.hentperson.Sivilstand as PdlSivilstand

class PdlSivilstandMapperTest {

    @Test
    fun mapSivilstander_med_en_sivilstand_skal_gi_en_rad() {
        val pdlSivilstander: List<PdlSivilstand> = listOf(pdlSivilstandGift(DATO_1, RELATERT_VED_SIVILSTAND_1))

        val sivilstander = PdlSivilstandMapper.mapSivilstander(pdlSivilstander)
        assertEquals(1, sivilstander.size)

        val sivilstand = sivilstander[0]
        assertEquals(Sivilstandstype.GIFT, sivilstand.type)
        assertEquals(DATO_1, sivilstand.gyldigFraOgMed)
        assertNull(sivilstand.gyldigTilOgMed)
        assertEquals(RELATERT_VED_SIVILSTAND_1, sivilstand.relatertVedSivilstand)
    }

    @Test
    fun mapSivilstander_med_to_sivilstander_skal_gi_to_rader() {
        val pdlSivilstander: List<PdlSivilstand> = listOf(
            pdlSivilstandGift(DATO_1, RELATERT_VED_SIVILSTAND_1),
            pdlSivilstandSeparert(DATO_2, RELATERT_VED_SIVILSTAND_1)
        )

        val sivilstander = PdlSivilstandMapper.mapSivilstander(pdlSivilstander)
        assertEquals(2, sivilstander.size)
    }

    @Test
    fun mapSivilstander_med_tre_sivilstander_skal_gi_tre_rader() {
        val pdlSivilstander: List<PdlSivilstand> = listOf(
            pdlSivilstandGift(DATO_1, RELATERT_VED_SIVILSTAND_1),
            pdlSivilstandSeparert(DATO_2, RELATERT_VED_SIVILSTAND_1),
            pdlSivilstandSkilt(DATO_3, RELATERT_VED_SIVILSTAND_1)
        )

        val sivilstander = PdlSivilstandMapper.mapSivilstander(pdlSivilstander)
        assertEquals(3, sivilstander.size)
    }

    @Test
    fun mapSivilstander_gyldig_til_dato_settes_til_neste_fra_og_med_dato_minus_en_dag() {
        val pdlSivilstander: List<PdlSivilstand> = listOf(
            pdlSivilstandGift(DATO_1, RELATERT_VED_SIVILSTAND_1),
            pdlSivilstandSeparert(DATO_2, RELATERT_VED_SIVILSTAND_1)
        )

        val sivilstander = PdlSivilstandMapper.mapSivilstander(pdlSivilstander)
        assertEquals(2, sivilstander.size)

        val sivilstand1 = sivilstander[0]
        assertEquals(DATO_2.minusDays(1), sivilstand1.gyldigTilOgMed)

        val sivilstand2 = sivilstander[1]
        assertNull(sivilstand2.gyldigTilOgMed)
    }

    private fun pdlSivilstandGift(gyldigFraOgMed: LocalDate, relatertVedSivilstand: String): PdlSivilstand {
        return PdlSivilstand(PdlSivilstandstype.GIFT, gyldigFraOgMed.toString(), null, relatertVedSivilstand)
    }

    private fun pdlSivilstandSeparert(gyldigFraOgMed: LocalDate, relatertVedSivilstand: String): PdlSivilstand {
        return PdlSivilstand(PdlSivilstandstype.SEPARERT, gyldigFraOgMed.toString(), null, relatertVedSivilstand)
    }

    private fun pdlSivilstandSkilt(gyldigFraOgMed: LocalDate, relatertVedSivilstand: String): PdlSivilstand {
        return PdlSivilstand(PdlSivilstandstype.SKILT, gyldigFraOgMed.toString(), null, relatertVedSivilstand)
    }

    companion object {
        val DATO_1 = LocalDate.of(1999, 2, 2)
        val DATO_2 = LocalDate.of(2005, 3, 4)
        val DATO_3 = LocalDate.of(2007, 6, 7)

        val RELATERT_VED_SIVILSTAND_1 = "123"
    }
}
