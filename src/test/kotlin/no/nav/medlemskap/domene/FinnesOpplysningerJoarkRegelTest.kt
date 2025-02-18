package no.nav.medlemskap.domene

import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.v1.registrerteOpplysninger.FinnesOpplysningerIJoarkRegel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

class FinnesOpplysningerJoarkRegelTest {
    @Test
    fun `Regel C skal ikke slå ut for der det ikke finnes dokumenter `() {

        var resultat = FinnesOpplysningerIJoarkRegel(
            ytelse = Ytelse.SYKEPENGER,
            periode = InputPeriode(LocalDate.now(), LocalDate.now()),
            førsteDagForYtelse = LocalDate.now(),
            dokument = emptyList()
        )
            .utfør()

        Assertions.assertEquals(Svar.NEI, resultat.svar)
    }
    @Test
    fun `Regel C skal slå der det finnes dokumenter som er mindre en 1 år gamle `() {

        var journalpost = Journalpost(
            datoOpprettet = LocalDateTime.now().toString(),
            relevanteDatoer = null,
            journalpostId = "1234567",
            journalfortAvNavn = "SAKSB",
            tittel = "Varsel om saksbehandlingstid",
            journalposttype = "U",
            journalstatus = "FERDIGSTILT",
            tema = "MED",
            sak = Sak("2101A01"),
            dokumenter = listOf(Dokument("99999", "Varsel om saksbehandlingstid"))
        )
        var resultat = FinnesOpplysningerIJoarkRegel(
            ytelse = Ytelse.SYKEPENGER,
            periode = InputPeriode(LocalDate.now(), LocalDate.now()),
            førsteDagForYtelse = LocalDate.now(),
            dokument = listOf(journalpost)
        ).utfør()

        Assertions.assertEquals(Svar.JA, resultat.svar)
    }
    @Test
    fun `Regel C skal ikke slå der det (kun) finnes dokumenter som er fra før 2011_01_01 `() {

        var journalpost = Journalpost(
            datoOpprettet = LocalDateTime.of(2010, Month.DECEMBER, 31, 23, 59).toString(),
            relevanteDatoer = null,
            journalpostId = "1234567",
            journalfortAvNavn = "SAKSB",
            tittel = "Varsel om saksbehandlingstid",
            journalposttype = "U",
            journalstatus = "FERDIGSTILT",
            tema = "MED",
            sak = Sak("2101A01"),
            dokumenter = listOf(Dokument("99999", "Varsel om saksbehandlingstid"))
        )
        var resultat = FinnesOpplysningerIJoarkRegel(
            ytelse = Ytelse.SYKEPENGER,
            periode = InputPeriode(LocalDate.now(), LocalDate.now()),
            førsteDagForYtelse = LocalDate.now(),
            dokument = listOf(journalpost)
        ).utfør()

        Assertions.assertEquals(Svar.NEI, resultat.svar)
    }
    @Test
    fun `Regel C skal bruke RelevantDato_DATO_JOURNALFOERT dersom denne ikke er null`() {

        var journalpost = Journalpost(
            datoOpprettet = LocalDateTime.now().toString(),
            relevanteDatoer = listOf(RelevantDato(LocalDateTime.of(2010, Month.DECEMBER, 31, 23, 59).toString(), Datotype.DATO_JOURNALFOERT)),
            journalpostId = "1234567",
            journalfortAvNavn = "SAKSB",
            tittel = "Varsel om saksbehandlingstid",
            journalposttype = "U",
            journalstatus = "FERDIGSTILT",
            tema = "MED",
            sak = Sak("2101A01"),
            dokumenter = listOf(Dokument("99999", "Varsel om saksbehandlingstid"))
        )
        var resultat = FinnesOpplysningerIJoarkRegel(
            ytelse = Ytelse.SYKEPENGER,
            periode = InputPeriode(LocalDate.now(), LocalDate.now()),
            førsteDagForYtelse = LocalDate.now(),
            dokument = listOf(journalpost)
        ).utfør()

        Assertions.assertEquals(Svar.NEI, resultat.svar)
    }
}
