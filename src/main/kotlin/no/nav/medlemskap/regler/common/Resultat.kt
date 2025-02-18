package no.nav.medlemskap.regler.common

import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.domene.Statsborgerskapskategori

enum class Svar {
    JA, NEI, UAVKLART
}

data class Resultat(
    val regelId: RegelId,
    val svar: Svar,
    val begrunnelse: String = regelId.begrunnelse(svar),
    var harDekning: Svar? = null,
    var dekning: String = "",
    val delresultat: List<Resultat> = listOf(),
    private val konklusjonstype: Konklusjonstype = Konklusjonstype.REGEL,
    private val årsak: Årsak? = null,
    val årsaker: List<Årsak> = if (årsak == null) delresultat.finnÅrsaker() else listOf(årsak)
) {
    val avklaring = regelId.avklaring

    fun erMedlemskonklusjon(): Boolean {
        return konklusjonstype == Konklusjonstype.MEDLEM
    }

    fun erRegelflytKonklusjon(): Boolean {
        return konklusjonstype == Konklusjonstype.REGELFLYT
    }

    fun erKonklusjon(): Boolean {
        return erMedlemskonklusjon() || erRegelflytKonklusjon()
    }

    fun finnRegelResultat(regelId: RegelId): Resultat? {
        return finnRegelResultat(this, regelId)
    }

    fun finnÅrsaker(): List<Årsak> {
        return finnÅrsaker(this)
    }

    fun hentÅrsak(): Årsak? {
        return årsak
    }

    fun erReglerOverstyrt(): Svar {
        return finnRegelResultat(RegelId.REGEL_0_5)?.svar!!
    }

    fun erEøsBorger(): Boolean {
        return finnRegelResultat(RegelId.REGEL_2)?.svar == Svar.JA
    }

    fun erNorskBorger(): Boolean {
        return finnRegelResultat(RegelId.REGEL_11)?.svar == Svar.JA
    }

    fun erEktefelleEOS(): Boolean {
        return finnRegelResultat(RegelId.REGEL_28)?.svar == Svar.JA && finnRegelResultat(RegelId.REGEL_29)?.svar == Svar.JA
    }

    fun erFamilieEOS(): Boolean {
        return erEktefelleEOS()
    }

    fun erTredjelandsborger(): Boolean {
        return !erEøsBorger()
    }

    fun bestemStatsborgerskapskategori(): Statsborgerskapskategori {
        return when {
            erNorskBorger() -> Statsborgerskapskategori.NORSK_BORGER
            erEøsBorger() -> Statsborgerskapskategori.EØS_BORGER
            erFamilieEOS() -> Statsborgerskapskategori.TREDJELANDSBORGER_MED_EOS_FAMILIE
            else -> Statsborgerskapskategori.TREDJELANDSBORGER
        }
    }

    fun tilJson(): String {
        return objectMapper.writeValueAsString(this)
    }

    companion object {
        private fun finnRegelResultat(resultat: Resultat, regelId: RegelId): Resultat? {
            var regelResultat = finnDelresultat(resultat, regelId)
            if (regelResultat != null) {
                return regelResultat
            }

            resultat.delresultat.forEach { delresultat ->
                regelResultat = finnRegelResultat(delresultat, regelId)
                if (regelResultat != null) {
                    return regelResultat
                }
            }

            return regelResultat
        }

        fun finnDelresultat(resultat: Resultat, regelId: RegelId): Resultat? {
            return resultat.delresultat.find { it.regelId == regelId }
        }

        fun finnÅrsak(resultat: Resultat): Årsak? {
            if (resultat.svar == Svar.JA && resultat.erKonklusjon()) {
                return null
            }

            if (resultat.hentÅrsak() == null && resultat.delresultat.isNotEmpty()) {
                return finnÅrsak(resultat.delresultat.last())
            }

            return resultat.årsak
        }

        fun finnÅrsaker(resultat: Resultat): List<Årsak> {
            if (resultat.svar == Svar.JA && resultat.erKonklusjon()) {
                return emptyList()
            }

            if (resultat.regelId == RegelId.REGEL_MEDLEM_KONKLUSJON) {
                return resultat
                    .delresultat.filter { !it.erKonklusjon() || it.erKonklusjon() && it.svar != Svar.JA }
                    .mapNotNull { finnÅrsak(it) }
            }

            if (resultat.erKonklusjon() && resultat.delresultat.isNotEmpty()) {
                val sisteResultat = resultat.delresultat.last()

                return listOf(finnÅrsak(sisteResultat)).filterNotNull()
            }

            return emptyList()
        }

        fun List<Resultat>.finnÅrsaker(): List<Årsak> {
            return this.mapNotNull { finnÅrsak(it) }
        }

        fun ja(regelId: RegelId, konklusjonstype: Konklusjonstype = Konklusjonstype.REGEL, dekning: String = "") = Resultat(
            regelId = regelId,
            svar = Svar.JA,
            harDekning = if (dekning != "") Svar.JA else null,
            dekning = dekning,
            konklusjonstype = konklusjonstype,
            årsak = null
        )

        fun nei(regelId: RegelId, konklusjonstype: Konklusjonstype = Konklusjonstype.REGEL, dekning: String = "", årsak: Årsak? = null) = Resultat(
            regelId = regelId,
            svar = Svar.NEI,
            harDekning = if (dekning != "") Svar.NEI else null,
            dekning = dekning,
            konklusjonstype = konklusjonstype,
            årsak = årsak
        )

        fun uavklart(regelId: RegelId, konklusjonstype: Konklusjonstype = Konklusjonstype.REGEL, årsak: Årsak? = null) = Resultat(
            regelId,
            svar = Svar.UAVKLART,
            konklusjonstype = konklusjonstype,
            årsak = årsak
        )

        fun List<Resultat>.utenKonklusjon(): List<Resultat> {
            return this.filterNot { it.erKonklusjon() }
        }
    }
}
