package no.nav.medlemskap.domene

import java.time.LocalDate
import java.time.LocalDateTime

data class Oppholdstillatelse(
    val uttrekkstidspunkt: LocalDateTime?,
    val foresporselsfodselsnummer: String?,
    val avgjoerelse: Boolean?,
    val gjeldendeOppholdsstatus: GjeldendeOppholdsstatus?,
    val arbeidsadgang: Arbeidsadgang?,
    val uavklartFlyktningstatus: Boolean?,
    val harFlyktningstatus: Boolean?,
    val udiFeilKode: Int? = null
) {

    fun harPermanentOppholdstillatelse(periode: Periode): Boolean {
        return gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.periode?.encloses(periode) == true &&
            gjeldendeOppholdsstatus.oppholdstillatelsePaSammeVilkar.type == OppholdstillaelsePaSammeVilkarType.PERMANENT
    }

    fun harGyldigArbeidstillatelseForPeriode(periode: Periode): Boolean {
        return arbeidsadgang != null && arbeidsadgang.harArbeidsadgang &&
            harGyldigArbeidsomfang() &&
            arbeidsadgang.arbeidsadgangType != null &&
            arbeidsadgang.arbeidsadgangType == ArbeidsadgangType.GENERELL &&
            arbeidsadgang.periode.enclosesAndFomNotNull(periode) &&
            gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.periode?.equals(arbeidsadgang.periode) ?: false
    }

    fun harGyldigOppholdstillatelseForPeriode(periode: Periode): Boolean {
        return gjeldendeOppholdsstatus != null && gjeldendeOppholdsstatus.oppholdstillatelsePaSammeVilkar?.harTillatelse == true &&
            gjeldendeOppholdsstatus.oppholdstillatelsePaSammeVilkar.periode?.enclosesAndFomNotNull(periode) ?: false
    }

    private fun harGyldigArbeidsomfang(): Boolean {
        val erArbeidsomfangHeltid = arbeidsadgang?.arbeidsomfang == ArbeidomfangKategori.KUN_ARBEID_HELTID
        val arbeidsadgangtypeErGenerellOgArbeidsomfangNull =
            arbeidsadgang?.arbeidsadgangType == ArbeidsadgangType.GENERELL && arbeidsadgang.arbeidsomfang == null

        return when {
            erArbeidsomfangHeltid -> true
            arbeidsadgangtypeErGenerellOgArbeidsomfangNull -> true
            else -> false
        }
    }

    fun overlapperOppholdsstatusPeriodene(): Boolean {
        val oppholdPaSammeVilkarPeriode = gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.periode
        val eosEllerEFTAPeriode = gjeldendeOppholdsstatus?.eosellerEFTAOpphold?.periode

        if (harFlereOppholdsstatuser() && oppholdPaSammeVilkarPeriode != null && eosEllerEFTAPeriode != null) {
            return oppholdPaSammeVilkarPeriode.overlapper(eosEllerEFTAPeriode)
        }

        return false
    }

    fun harFlereOppholdsstatuser(): Boolean =
        (gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar != null && gjeldendeOppholdsstatus.eosellerEFTAOpphold != null)
}

data class Arbeidsadgang(
    val periode: Periode,
    val harArbeidsadgang: Boolean,
    val arbeidsadgangType: ArbeidsadgangType?,
    val arbeidsomfang: ArbeidomfangKategori?
)

data class OppholdstillatelsePaSammeVilkar(
    val periode: Periode?,
    val type: OppholdstillaelsePaSammeVilkarType?,
    val harTillatelse: Boolean?,
    val soknadIkkeAvgjort: Boolean?
)

data class GjeldendeOppholdsstatus(
    val oppholdstillatelsePaSammeVilkar: OppholdstillatelsePaSammeVilkar?,
    val eosellerEFTAOpphold: EOSellerEFTAOpphold?,
    val uavklart: Uavklart?,
    val ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum: IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum?
)

data class IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
    val utvistMedInnreiseForbud: UtvistMedInnreiseForbud?,
    var avslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak: AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak?,
    var ovrigIkkeOpphold: OvrigIkkeOpphold?
)

data class UtvistMedInnreiseForbud(
    val innreiseForbud: JaNeiUavklart?
)

data class AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak(
    val avgjorelsesDato: LocalDate?
)

data class OvrigIkkeOpphold(
    val ovrigIkkeOppholdsKategori: OvrigIkkeOppholdsKategori?
)

data class Uavklart(
    val uavklart: Boolean = false
)

data class EOSellerEFTAOpphold(
    val periode: Periode?,
    val eosellerEFTAOppholdType: EOSellerEFTAOppholdType,
    val eosellerEFTAGrunnlagskategoriOppholdsrettType: EOSellerEFTAGrunnlagskategoriOppholdsrettType?,
    val eosellerEFTAGrunnlagskategoriOppholdstillatelseType: EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType?
)

enum class EOSellerEFTAGrunnlagskategoriOppholdsrettType(val kodeverdi: String) {
    VARIG("Varig"),
    INGEN_INFORMASJON("IngenInformasjon"),
    FAMILIE("Familie"),
    TJENESTEYTING_ELLER_ETABLERING("TjenesteytingEllerEtablering"),
    UAVKLART("Uavklart");

    companion object {
        fun fraEOSellerEFTAGrunnlagskategoriOppholdsrettType(
            eosEllerEFTAGrunnlagskategoriOppholdsrettType: String?
        ): EOSellerEFTAGrunnlagskategoriOppholdsrettType? {
            return EOSellerEFTAGrunnlagskategoriOppholdsrettType.values()
                .firstOrNull { it.kodeverdi == eosEllerEFTAGrunnlagskategoriOppholdsrettType }
        }
    }
}

enum class EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType(val kodeverdi: String) {
    EGNE_MIDLER_ELLER_FASTE_PERIODISKE_YTELSER("EgneMidlerEllerFastePeriodiskeYtelser"),
    ARBEID("Arbeid"),
    UTDANNING("Utdanning"),
    FAMILIE("Familie"),
    TJENESTEYTING_ELLER_ETABLERING("TjenesteytingEllerEtablering"),
    UAVKLART("Uavklart");

    companion object {
        fun fraEOSellerEFTAGrunnlagskategoriOppholdsTillatelseType(
            eosEllerEFTAGrunnlagskategoriOppholdsTillatelseType: String?
        ): EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType? {
            return EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType.values()
                .firstOrNull { it.kodeverdi == eosEllerEFTAGrunnlagskategoriOppholdsTillatelseType }
        }
    }
}

enum class OvrigIkkeOppholdsKategori(val kodeverdi: String) {
    OPPHEVET_INNREISEFORBUD("OpphevetInnreiseforbud"),
    ANNULERING_AV_VISUM("AnnuleringAvVisum"),
    UTLOPT_OPPHOLDSTILLATELSE("UtloptOppholdstillatelse"),
    UTLOPT_EO_SELLER_EFTA_OPPHOLDSRETT_ELLER_EO_SELLER_EFTA_OPPHOLDSTILLATELSE("UtloptEOSellerEFTAOppholdsrettEllerEOSellerEFTAOppholdstillatelse");

    companion object {
        fun fraOvrigIkkeOppholdsKategoriType(
            ovrigIkkeOppholdsKategori: String?
        ): OvrigIkkeOppholdsKategori? {
            return OvrigIkkeOppholdsKategori.values()
                .firstOrNull { it.kodeverdi == ovrigIkkeOppholdsKategori }
        }
    }
}

enum class EOSellerEFTAOppholdType(val kodeverdi: String) {
    EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT("EOSellerEFTABeslutningOmOppholdsrett"),
    EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT("EOSellerEFTAVedtakOmVarigOppholdsrett"),
    EOS_ELLER_EFTA_OPPHOLDSTILLATELSE("EOSellerEFTAOppholdstillatelse");

    companion object {
        fun fraEOSellerEFTAOppholdTypeVerdi(EOSellerEFTAOppholdTypeVerdi: String?): EOSellerEFTAOppholdType? {
            return values().firstOrNull { it.kodeverdi == EOSellerEFTAOppholdTypeVerdi }
        }
    }
}

enum class ArbeidomfangKategori(val kodeverdi: String) {
    INGEN_KRAV_TIL_STILLINGSPROSENT("IngenKravTilStillingsprosent"),
    KUN_ARBEID_HELTID("KunArbeidHeltid"),
    KUN_ARBEID_DELTID("KunArbeidDeltid"),
    DELTID_SAMT_FERIER_HELTID("DeltidSamtFerierHeltid"),
    UAVKLART("Uavklart");

    companion object {
        fun fraArbeidomfang(arbeidsomfangVerdi: String?): ArbeidomfangKategori? {
            return values().firstOrNull { it.kodeverdi == arbeidsomfangVerdi }
        }
    }
}

enum class OppholdstillaelsePaSammeVilkarType(val kodeverdi: String) {
    PERMANENT("Permanent"),
    MIDLERTIDIG("Midlertidig");

    companion object {
        fun fraOppholdstillatelsePaSammeVilkarTypeVerdi(oppholdPaSammeVilkarVerdi: String?): OppholdstillaelsePaSammeVilkarType? {
            return values().firstOrNull { it.kodeverdi == oppholdPaSammeVilkarVerdi }
        }
    }
}

enum class ArbeidsadgangType(val kodeverdi: String) {
    BESTEMT_ARBEIDSGIVER_ELLER_OPPDRAGSGIVER("BestemtArbeidsgiverEllerOppdragsgiver"),
    BESTEMT_ARBEID_ELLER_OPPDRAG("BestemtArbeidEllerOppdrag"),
    BESTEMT_ARBEIDSGIVER_OG_ARBEID_ELLER_BESTEMT_OPPDRAGSGIVER_OG_OPPDRAG("BestemtArbeidsgiverOgArbeidEllerBestemtOppdragsgiverOgOppdrag"),
    GENERELL("Generell"),
    UAVKLART("Uavklart");

    companion object {
        fun fraArbeidsadgangType(kodeverdi: String?): ArbeidsadgangType? {
            return values().firstOrNull { it.kodeverdi == kodeverdi }
        }
    }
}

enum class JaNeiUavklart(val jaNeiUavklart: String) {
    JA("Ja"),
    NEI("Nei"),
    UAVKLART("Uavklart");

    companion object {
        fun fraJaNeiUavklartVerdi(jaNeiUavklartVerdi: String?): JaNeiUavklart? {
            if (jaNeiUavklartVerdi.isNullOrEmpty()) return null
            return valueOf(jaNeiUavklartVerdi.toUpperCase())
        }
    }
}
