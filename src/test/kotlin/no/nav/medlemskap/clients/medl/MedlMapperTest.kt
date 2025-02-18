package no.nav.medlemskap.clients.medl

import no.nav.medlemskap.domene.Dekning
import no.nav.medlemskap.domene.Lovvalg
import no.nav.medlemskap.domene.PeriodeStatus
import no.nav.medlemskap.services.medl.mapMedlemskapResultat
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.LocalDate
import java.time.LocalDateTime

class MedlMapperTest {

    @Test
    fun mapMedlPeriode() {
        val dato = LocalDate.now()
        val medlSporingsinformasjon = MedlSporingsinformasjon(dato, "kilde", "kildedokument", LocalDateTime.now(), "opprettetAv", dato, LocalDateTime.now(), "sistEndretAv", "versjon")
        val medlStudieinformasjon = MedlStudieinformasjon(false, true, "statsborgerland", "studieland")
        val medlMedlemskapsunntak = MedlMedlemskapsunntak(
            "Full", dato, "grunnlag", true, "ident",
            "ENDL", "lovvalgsland", true, "GYLD", "statusaarsak", dato, 123, medlSporingsinformasjon, medlStudieinformasjon
        )

        val mappedTilMedlemskap = mapMedlemskapResultat(listOf(medlMedlemskapsunntak))
        assertEquals(1, mappedTilMedlemskap.size)
        val medlemskapsperiode = mappedTilMedlemskap[0]

        assertEquals(dato, medlemskapsperiode.fraOgMed)
        assertEquals(dato, medlemskapsperiode.tilOgMed)
        assertTrue(medlemskapsperiode.erMedlem)
        assertEquals("lovvalgsland", medlemskapsperiode.lovvalgsland)
        assertEquals(Dekning.FULL.dekningKodeverdi, medlemskapsperiode.dekning)
        assertEquals(Lovvalg.ENDL, medlemskapsperiode.lovvalg)
        assertEquals(PeriodeStatus.GYLD, medlemskapsperiode.periodeStatus)
    }
}
