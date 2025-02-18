package no.nav.medlemskap.services

import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.domene.ektefelle.DataOmEktefelle
import no.nav.medlemskap.services.aareg.AaRegService
import no.nav.medlemskap.services.pdl.PdlService
import java.time.LocalDate

class FamilieService(private val aaRegService: AaRegService, private val pdlService: PdlService) {

    suspend fun hentDataOmBarn(
        fnrTilBarn: List<String>,
        callId: String
    ): List<DataOmBarn> {
        return fnrTilBarn
            .mapNotNull { pdlService.hentPersonHistorikkTilBarn(it, callId) }
            .map { DataOmBarn(it) }
    }

    suspend fun hentDataOmEktefelle(
        fnrTilEktefelle: String?,
        callId: String,
        periode: InputPeriode,
        startDatoForYtelse: LocalDate
    ): DataOmEktefelle? {
        if (fnrTilEktefelle == null) {
            return null
        }

        val personhistorikkEktefelle =
            pdlService.hentPersonHistorikkTilEktefelle(fnrTilEktefelle, startDatoForYtelse, callId) ?: return null
        val arbeidsforholdEktefelle = try {
            aaRegService.hentArbeidsforhold(
                fnr = fnrTilEktefelle,
                fraOgMed = Arbeidsforhold.fraOgMedDatoForArbeidsforhold(startDatoForYtelse),
                tilOgMed = periode.tom,
                callId = callId
            )
        } catch (t: Exception) {
            emptyList<Arbeidsforhold>()
        }

        return DataOmEktefelle(
            personhistorikkEktefelle = personhistorikkEktefelle,
            arbeidsforholdEktefelle = arbeidsforholdEktefelle
        )
    }
}
