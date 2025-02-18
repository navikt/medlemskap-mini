package no.nav.medlemskap.clients.medl

import java.time.LocalDate
import java.time.LocalDateTime

data class MedlMedlemskapsunntak(
    val dekning: String?,
    val fraOgMed: LocalDate,
    val grunnlag: String,
    val helsedel: Boolean,
    val ident: String,
    val lovvalg: String,
    val lovvalgsland: String?,
    val medlem: Boolean,
    val status: String,
    val statusaarsak: String?,
    val tilOgMed: LocalDate,
    val unntakId: Int,
    val sporingsinformasjon: MedlSporingsinformasjon?,
    val studieinformasjon: MedlStudieinformasjon?
)

data class MedlSporingsinformasjon(
    val besluttet: LocalDate?,
    val kilde: String,
    val kildedokument: String?,
    val opprettet: LocalDateTime,
    val opprettetAv: String,
    val registrert: LocalDate?,
    val sistEndret: LocalDateTime,
    val sistEndretAv: String,
    val versjon: String
)

data class MedlStudieinformasjon(
    val delstudie: Boolean,
    val soeknadInnvilget: Boolean,
    val statsborgerland: String,
    val studieland: String?
)
