package no.nav.medlemskap.domene

import no.nav.medlemskap.domene.Journalpost.Companion.finnesDokumenterMedTillatteTeamer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class journalpostTest {
    @Test
    fun `filtrerer ut dokumenter som ikke er relevante`() {
        val dokument = listOf(
            Journalpost(
                relevanteDatoer = null,
                datoOpprettet = LocalDateTime.now().toString(),
                journalpostId = "",
                journalfortAvNavn = "",
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "",
                sak = null,
                dokumenter = null
            )
        )
        val finnesDokument = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertFalse(finnesDokument)
    }

    @Test
    fun `filtrerer ikke ut dokumenter som ikke er relevante`() {
        val dokument = listOf(
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = "",
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            )
        )
        val finnesDokument = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertTrue(finnesDokument)
    }

    @Test
    fun `filtrerer ut dokumenter uavhengig av tema som vi ikke oppretter selv`() {
        val dokument = listOf(
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = "medlemskap-joark-listener",
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            )
        )
        val finnesDokument = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertFalse(finnesDokument)
    }

    @Test
    fun `filtrerer  ut dokumenter der journalpostDato er eldre en 2011_01_01`() {
        val dokument = listOf(
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = listOf(RelevantDato("2010-05-25T19:43:43", Datotype.DATO_JOURNALFOERT)),
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            )
        )
        val finnesDokument = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertFalse(finnesDokument)
    }
    @Test
    fun `beholder dokumenter der journalpostDato er nyere en 2011_01_01`() {
        val dokument = listOf(
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = listOf(RelevantDato("2012-05-25T19:43:43", Datotype.DATO_JOURNALFOERT)),
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            )
        )
        val finnesDokument = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertTrue(finnesDokument)
    }
    @Test
    fun `filtrerer ikke ut dokumenter med null verdi i journalført av navn`() {
        val dokument = listOf(
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            )
        )
        val finnesDokument = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertTrue(finnesDokument)
    }

    @Test
    fun `test`() {
        val dokument = listOf(
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            ),
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = Sak("MEL-123456789"),
                dokumenter = null
            ),
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            ),
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            ),
            Journalpost(
                datoOpprettet = LocalDateTime.now().toString(),
                relevanteDatoer = null,
                journalpostId = "",
                journalfortAvNavn = null,
                journalposttype = "",
                journalstatus = "",
                tittel = "",
                tema = "MED",
                sak = null,
                dokumenter = null
            )
        )

        val filtrerteDokumenter = dokument.finnesDokumenterMedTillatteTeamer()

        Assertions.assertTrue(filtrerteDokumenter)
    }
}
