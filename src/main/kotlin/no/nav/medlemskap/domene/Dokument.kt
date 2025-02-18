package no.nav.medlemskap.domene

import no.nav.medlemskap.regler.common.Funksjoner.erDelAv
import no.nav.medlemskap.regler.common.Funksjoner.isNotNullOrEmpty
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter

/**
 * Journalpost fra Joark
 */
data class Journalpost(
    val datoOpprettet: String,
    val relevanteDatoer: List<RelevantDato>?,
    val journalpostId: String,
    val journalfortAvNavn: String?,
    val tittel: String?,
    val journalposttype: String?,
    val journalstatus: String?,
    val tema: String?,
    val sak: Sak?,
    val dokumenter: List<Dokument>?
) {

    companion object {
        private val tillatteTemaer = listOf("MED", "UFM", "TRY")

        fun Journalpost.harJournalFortDatoEtter(localDateTime: LocalDateTime): Boolean {
            val firstDayOf2011 = localDateTime
            var datoOpprettet: LocalDateTime
            try {
                datoOpprettet = LocalDateTime.parse(this.datoOpprettet, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (e: Throwable) {
                datoOpprettet = LocalDateTime.now()
            }
            val relevantDatoJournalFort = this.relevanteDatoer?.find { it.datotype == Datotype.DATO_JOURNALFOERT }
            if (relevantDatoJournalFort != null) {
                val journalfortDato = LocalDateTime.parse(relevantDatoJournalFort.dato, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                return journalfortDato.isAfter(firstDayOf2011)
            } else {
                return datoOpprettet.isAfter(firstDayOf2011)
            }
        }
        fun String.asLocalDatetime(): LocalDateTime {
            return try {
                LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (e: Throwable) {
                LocalDateTime.now()
            }
        }

        fun List<Journalpost>.finnesDokumenterMedTillatteTeamer(): Boolean =
            this.dokumenterMedTillatteTemaer().isNotEmpty()

        fun List<Journalpost>.dokumenterMedTillatteTemaer(): List<Journalpost> =

            this.filtrervekkDokumenterMedJournalDatofør(LocalDateTime.of(2011, Month.JANUARY, 1, 0, 0))
                .filtrerVekkGamleMEDjournalposterBasertPaaNyesteMELsak()
                .filterNot { it.sak?.fagsakId?.contains("MEL-") == true }
                .filter { it.tema erDelAv tillatteTemaer }
                .filter { (it.journalfortAvNavn.isNullOrEmpty() || !it.journalfortAvNavn.contains("medlemskap-joark")) }

        fun List<Journalpost>.harDokument(): Boolean = this.isNotNullOrEmpty()

        fun List<Journalpost>.alleFagsakIDer(): List<String> {
            return this.map { journalpost -> journalpost.sak?.fagsakId.toString() }
        }

        fun List<Journalpost>.filtrerVekkGamleUrelevanteDokumenter(): List<Journalpost> {
            return this.filter { it.harJournalFortDatoEtter(LocalDateTime.of(2011, Month.JANUARY, 1, 0, 0)) }
        }
        fun List<Journalpost>.filtrervekkDokumenterMedJournalDatofør(dato: LocalDateTime): List<Journalpost> {
            return this.filter { it.harJournalFortDatoEtter(dato) }
        }
        fun List<Journalpost>.filtrerVekkGamleMEDjournalposterBasertPaaNyesteMELsak(): List<Journalpost> {
            var nyesteDato: LocalDateTime? = null

            if (this.any { it.datoOpprettet == "null" || it.datoOpprettet.isEmpty() })
                return this

            this.forEach {
                val dato = LocalDateTime.parse(it.datoOpprettet)
                if (it.sak?.fagsakId?.contains("MEL") == true && (nyesteDato == null || dato.isAfter(nyesteDato)))
                    nyesteDato = LocalDateTime.parse(it.datoOpprettet)
            }

            return this.filterNot { it.tema.equals("MED") && nyesteDato?.isAfter(LocalDateTime.parse(it.datoOpprettet)) == true }
        }
    }
}

/*
 * Gosys saksdokument
 */
data class Dokument(
    val dokumentId: String,
    val tittel: String?
)

data class RelevantDato(
    val dato: String,
    val datotype: Datotype
)
enum class Datotype {
    DATO_OPPRETTET,
    DATO_SENDT_PRINT,
    DATO_EKSPEDERT,
    DATO_JOURNALFOERT,
    DATO_REGISTRERT,
    DATO_AVS_RETUR,
    DATO_DOKUMENT,
    DATO_LEST,
    /**
     * This is a default enum value that will be used when attempting to deserialize unknown value.
     */
    __UNKNOWN_VALUE,
}
data class Sak(val fagsakId: String?)
