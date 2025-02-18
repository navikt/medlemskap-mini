package no.nav.medlemskap.clients.aareg

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

data class AaRegAnsettelsesperiode(
    val bruksperiode: AaRegBruksperiode,
    val periode: AaRegPeriode,
    val sporingsinformasjon: AaRegSporingsinformasjon,
    val varslingskode: String?
)

data class AaRegAntallTimerForTimeloennet(
    val antallTimer: Double,
    val periode: AaRegPeriode?,
    val rapporteringsperiode: YearMonth,
    val sporingsinformasjon: AaRegSporingsinformasjon
)

data class AaRegArbeidsavtale(
    val antallTimerPrUke: Double?,
    val arbeidstidsordning: String?,
    val beregnetAntallTimerPrUke: Double?,
    val bruksperiode: AaRegBruksperiode,
    val gyldighetsperiode: AaRegGyldighetsperiode,
    val sistLoennsendring: String?,
    val sistStillingsendring: String?,
    val sporingsinformasjon: AaRegSporingsinformasjon,
    val stillingsprosent: Double?,
    val yrke: String,
    val fartsomraade: String?,
    val skipsregister: String?,
    val skipstype: String?

)

data class AaRegArbeidsforhold(
    val ansettelsesperiode: AaRegAnsettelsesperiode,
    val antallTimerForTimeloennet: List<AaRegAntallTimerForTimeloennet>?,
    val arbeidsavtaler: List<AaRegArbeidsavtale>,
    val arbeidsforholdId: String?,
    val arbeidsgiver: AaRegOpplysningspliktigArbeidsgiver,
    val arbeidstaker: AaRegPerson,
    val innrapportertEtterAOrdningen: Boolean,
    val navArbeidsforholdId: Int,
    val opplysningspliktig: AaRegOpplysningspliktigArbeidsgiver,
    val permisjonPermitteringer: List<AaRegPermisjonPermittering>?,
    val registrert: LocalDateTime,
    val sistBekreftet: LocalDateTime?,
    val sporingsinformasjon: AaRegSporingsinformasjon,
    val type: String,
    val utenlandsopphold: List<AaRegUtenlandsopphold>?
)

data class AaRegArbeidsgiverArbeidsforhold(
    val antall: Int,
    val arbeidsforhold: List<AaRegArbeidsforhold>
)

data class AaRegBruksperiode(
    val fom: LocalDateTime,
    val tom: LocalDateTime?
)

enum class AaRegOpplysningspliktigArbeidsgiverType {
    Organisasjon, Person
}

data class AaRegGyldighetsperiode(
    val fom: LocalDate,
    val tom: LocalDate?
)

data class AaRegOpplysningspliktigArbeidsgiver(
    val type: AaRegOpplysningspliktigArbeidsgiverType,
    val organisasjonsnummer: String?,
    val aktoerId: String?,
    val offentligIdent: String?
)

enum class AaRegOrganisasjonType {
    Organisasjon
}

data class AaRegOrganisasjon(
    val type: AaRegOrganisasjonType,
    val organisasjonsnummer: String
)

data class AaRegPeriode(
    val fom: LocalDate?,
    val tom: LocalDate?
)

data class AaRegPermisjonPermittering(
    val periode: AaRegPeriode?,
    val permisjonPermitteringId: String,
    val prosent: Double?,
    val sporingsinformasjon: AaRegSporingsinformasjon,
    val type: String,
    val varslingskode: String?
)

enum class AaRegPersonType {
    Person
}

data class AaRegPerson(
    val type: AaRegPersonType,
    val aktoerId: String,
    val offentligIdent: String
)

data class AaRegSporingsinformasjon(
    val endretAv: String?,
    val endretKilde: String?,
    val endretKildeReferanse: String?,
    val endretTidspunkt: LocalDateTime?,
    val opprettetAv: String,
    val opprettetKilde: String?,
    val opprettetKildereferanse: String?,
    val opprettetTidspunkt: LocalDateTime?
)

data class AaRegTjenestefeilResponse(
    val melding: String
)

data class AaRegUtenlandsopphold(
    val landkode: String,
    val periode: AaRegPeriode?,
    val rapporteringsperiode: YearMonth,
    val sporingsinformasjon: AaRegSporingsinformasjon
)
