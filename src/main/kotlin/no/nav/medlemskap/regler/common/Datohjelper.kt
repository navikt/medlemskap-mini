package no.nav.medlemskap.regler.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.min

object Datohjelper {
    private val norskDatoFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val isoDatoFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun parseDato(dato: String): LocalDate {
        return if (dato.contains(".")) {
            LocalDate.parse(dato, norskDatoFormatter)
        } else {
            LocalDate.parse(dato, isoDatoFormatter)
        }
    }

    fun parseIsoDato(dato: String?): LocalDate? {
        if (dato == null) {
            return null
        }

        return LocalDate.parse(
            dato.substring(0, min(dato.length, 10)),
            isoDatoFormatter
        )
    }

    fun parseIsoDatoTid(datoTid: String?): LocalDateTime? {
        if (datoTid == null) {
            return null
        }

        return LocalDateTime.parse(datoTid, DateTimeFormatter.ISO_DATE_TIME)
    }
}

fun erDatoerSammenhengende(sluttDato: LocalDate, startDato: LocalDate?, tillatDagersHullIPeriode: Long): Boolean =
    sluttDato.isAfter(startDato?.minusDays(tillatDagersHullIPeriode))

fun LocalDate.startOfDayInstant() = this.atStartOfDay(ZoneId.systemDefault()).toInstant()
