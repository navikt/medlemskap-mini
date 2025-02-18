package no.nav.medlemskap.domene.personhistorikk

import no.bekk.bekkopen.person.FodselsnummerValidator
import no.nav.medlemskap.domene.Fødselsnummer.Companion.hentBursdagsAar
import java.time.LocalDate

data class ForelderBarnRelasjon(
    val relatertPersonsIdent: String?,
    val relatertPersonsRolle: ForelderBarnRelasjonRolle,
    val minRolleForPerson: ForelderBarnRelasjonRolle?
) {
    companion object {
        fun String.erBarnUnder25Aar(førsteDatoForYtelse: LocalDate) =
            (førsteDatoForYtelse.year - this.hentBursdagsAar().toInt()) <= 25

        fun hentFnrTilEktefelle(personHistorikkFraPdl: Personhistorikk?): String? {
            val fnrTilEktefelle =
                personHistorikkFraPdl?.sivilstand
                    ?.filter { it.relatertVedSivilstand != null }
                    ?.filter { FodselsnummerValidator.isValid(it.relatertVedSivilstand) }
                    ?.filter { it.type == Sivilstandstype.GIFT || it.type == Sivilstandstype.REGISTRERT_PARTNER }
                    ?.map { it.relatertVedSivilstand }
                    ?.lastOrNull()
            return fnrTilEktefelle
        }

        fun hentFnrTilBarn(familierelasjoner: List<ForelderBarnRelasjon>, førsteDatoForYtelse: LocalDate): List<String> {
            return familierelasjoner
                .filter { FodselsnummerValidator.isValid(it.relatertPersonsIdent) }
                .filter { it.erBarn() }
                .filter { it.relatertPersonsIdent != null && it.relatertPersonsIdent.erBarnUnder25Aar(førsteDatoForYtelse) }
                .map { it.relatertPersonsIdent!! }
        }

        fun ForelderBarnRelasjon.erBarn() = this.relatertPersonsRolle == ForelderBarnRelasjonRolle.BARN
    }
}
