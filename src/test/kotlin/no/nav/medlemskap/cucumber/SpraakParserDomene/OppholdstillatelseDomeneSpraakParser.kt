package no.nav.medlemskap.cucumber.SpraakParserDomene

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import no.nav.medlemskap.domene.*
import no.nav.medlemskap.regler.common.Datohjelper
import java.time.LocalDate
import java.time.LocalDateTime

object OppholdstillatelseDomeneSpraakParser : BasisDomeneParser() {

    fun mapHarArbeidstilgang(dataTable: DataTable): Boolean {
        return mapDataTable(dataTable, HarArbeidstilgangMapper())[0]
    }

    fun mapJaNeiUavklart(dataTable: DataTable): JaNeiUavklart {
        return mapDataTable(dataTable, JaNeiUavklartMapper())[0]
    }

    fun mapforesporselfodselsnummer(dataTable: DataTable): String {
        return mapDataTable(dataTable, ForesporselfodselsnummerMapper())[0]
    }

    fun mapUttrekkstidspunkt(dataTable: DataTable): LocalDateTime? {
        return mapDataTable(dataTable, UttrekkstidspunktMapper())[0]
    }

    fun mapArbeidsomfangKategori(dataTable: DataTable): ArbeidomfangKategori? {
        return mapDataTable(dataTable, ArbeidsomfangKategoriMapper())[0]
    }

    fun mapArbeidsadgangType(dataTable: DataTable): ArbeidsadgangType {
        return mapDataTable(dataTable, ArbeidsadgangTypeMapper())[0]
    }

    fun mapAvgjorelseDato(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, AvgjorelseDatoMapper())[0]
    }

    fun mapOvrigIkkeOppholdsKategori(dataTable: DataTable): OvrigIkkeOppholdsKategori {
        return mapDataTable(dataTable, OvrigIkkeOppholdsKategoriMapper())[0]
    }

    fun mapPeriode(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, PeriodeMapper())[0]
    }

    fun mapEosEllerEftaPeriode(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, EosEllerEftaPeriodeMapper())[0]
    }

    fun mapOppholdsrettType(dataTable: DataTable): EOSellerEFTAGrunnlagskategoriOppholdsrettType {
        return mapDataTable(dataTable, OppholdsrettTypeMapper())[0]
    }

    fun mapOppholdstillatelse(dataTable: DataTable): Oppholdstillatelse {
        return mapDataTable(dataTable, OppholdstillatelseMapper())[0]
    }

    fun mapArbeidstilgang(dataTable: DataTable): Arbeidsadgang {
        return mapDataTable(dataTable, ArbeidsadgangMapper())[0]
    }

    fun mapOppholdstillatelsePaSammeVilkar(dataTable: DataTable): OppholdstillatelsePaSammeVilkar {
        return mapDataTable(dataTable, HarOppholdMapper())[0]
    }

    fun mapUavklart(dataTable: DataTable): Uavklart {
        return mapDataTable(dataTable, UavklartMapper())[0]
    }

    fun mapEOSElllerEFTAOppholdKlasse(dataTable: DataTable): EOSellerEFTAOpphold {
        return mapDataTable(dataTable, EOSElllerEFTAOppholdKlasseMapper())[0]
    }

    fun mapOppholdType(dataTable: DataTable): EOSellerEFTAOppholdType {
        return mapDataTable(dataTable, EOSellerEFTAOppholdMapper())[0]
    }

    fun mapHarTillatelse(dataTable: DataTable): Boolean {
        return mapDataTable(dataTable, HarTillatelseMapper())[0]
    }

    fun mapSoknadIkkeAvgjort(dataTable: DataTable): Boolean {
        return mapDataTable(dataTable, HarSoknadIkkeAvgjortMapper())[0]
    }

    fun mapOppholdstillatelsePaSammeVilkarType(dataTable: DataTable): OppholdstillaelsePaSammeVilkarType {
        return mapDataTable(dataTable, OppholdstillaelsePaSammeVilkarTypeMapper())[0]
    }

    class OppholdstillaelsePaSammeVilkarTypeMapper() : RadMapper<OppholdstillaelsePaSammeVilkarType> {
        override fun mapRad(rad: Map<String, String>): OppholdstillaelsePaSammeVilkarType {
            return OppholdstillaelsePaSammeVilkarType.valueOf(parseString(OppholdstillatelseDomenebegrep.OPPHOLDSTILLATELSE_TYPE, rad))
        }
    }
    class OppholdsrettTypeMapper() : RadMapper<EOSellerEFTAGrunnlagskategoriOppholdsrettType> {
        override fun mapRad(rad: Map<String, String>): EOSellerEFTAGrunnlagskategoriOppholdsrettType {
            return EOSellerEFTAGrunnlagskategoriOppholdsrettType.valueOf(parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT, rad))
        }
    }

    class EOSellerEFTAOppholdMapper : RadMapper<EOSellerEFTAOppholdType> {
        override fun mapRad(rad: Map<String, String>): EOSellerEFTAOppholdType {
            return EOSellerEFTAOppholdType.valueOf(parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_OPPHOLD, rad))
        }
    }

    class HarSoknadIkkeAvgjortMapper() : RadMapper<Boolean> {
        override fun mapRad(rad: Map<String, String>): Boolean {
            return parseBoolean(OppholdstillatelseDomenebegrep.OPPHOLDSTILLATELSE_PÅ_SAMME_VILKÅR_FLAGG, rad)
        }
    }

    class HarTillatelseMapper() : RadMapper<Boolean> {
        override fun mapRad(rad: Map<String, String>): Boolean {
            return parseBoolean(OppholdstillatelseDomenebegrep.HAR_OPPHOLDSTILLATELSE, rad)
        }
    }

    class EOSElllerEFTAOppholdKlasseMapper() : RadMapper<EOSellerEFTAOpphold> {
        override fun mapRad(rad: Map<String, String>): EOSellerEFTAOpphold {

            val EOSellerEFTAGrunnlagskategoriOppholdsrettType = if (parseValgfriString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT, rad) != null
            ) {
                EOSellerEFTAGrunnlagskategoriOppholdsrettType.valueOf(parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT, rad))
            } else { null }

            val EOSellerEFTAGrunnlagskategoriOppholdstillatelseType = if (parseValgfriString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSTILLATELSE, rad) != null
            ) {
                EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType.valueOf(parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSTILLATELSE, rad))
            } else { null }

            return EOSellerEFTAOpphold(
                periode = Periode(
                    fom = parseValgfriDato(OppholdstillatelseDomenebegrep.GYLDIG_FRA_OG_MED, rad),
                    tom = parseValgfriDato(OppholdstillatelseDomenebegrep.GYLDIG_TIL_OG_MED, rad)
                ),
                eosellerEFTAOppholdType = EOSellerEFTAOppholdType.valueOf(
                    parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_OPPHOLD, rad)
                ),
                eosellerEFTAGrunnlagskategoriOppholdsrettType = EOSellerEFTAGrunnlagskategoriOppholdsrettType,
                eosellerEFTAGrunnlagskategoriOppholdstillatelseType = EOSellerEFTAGrunnlagskategoriOppholdstillatelseType
            )
        }
    }

    class UavklartMapper() : RadMapper<Uavklart> {
        override fun mapRad(rad: Map<String, String>): Uavklart {
            return Uavklart(
                parseBooleanMedBooleanVerdi(OppholdstillatelseDomenebegrep.UAVKLART, rad)
            )
        }
    }

    class JaNeiUavklartMapper() : RadMapper<JaNeiUavklart> {
        override fun mapRad(rad: Map<String, String>): JaNeiUavklart {
            return JaNeiUavklart.valueOf(parseString(OppholdstillatelseDomenebegrep.JA_NEI_UAVKLART, rad))
        }
    }

    class PeriodeMapper() : RadMapper<Periode> {
        override fun mapRad(rad: Map<String, String>): Periode {
            return Periode(
                fom = parseDato(OppholdstillatelseDomenebegrep.GYLDIG_FRA_OG_MED, rad),
                tom = parseDato(OppholdstillatelseDomenebegrep.GYLDIG_TIL_OG_MED, rad)
            )
        }
    }

    class EosEllerEftaPeriodeMapper() : RadMapper<Periode> {
        override fun mapRad(rad: Map<String, String>): Periode {
            return Periode(
                fom = parseDato(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_PERIODE_FOM, rad),
                tom = parseDato(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_PERIODE_TOM, rad)
            )
        }
    }

    class OvrigIkkeOppholdsKategoriMapper() : RadMapper<OvrigIkkeOppholdsKategori> {
        override fun mapRad(rad: Map<String, String>): OvrigIkkeOppholdsKategori {
            return OvrigIkkeOppholdsKategori.valueOf(parseString(OppholdstillatelseDomenebegrep.OVRIG_IKKE_OPPHOLD_KATEGORI, rad))
        }
    }

    class AvgjorelseDatoMapper() : RadMapper<LocalDate> {
        override fun mapRad(rad: Map<String, String>): LocalDate {
            return parseDato(OppholdstillatelseDomenebegrep.AVGJORELSEDATO, rad)
        }
    }

    class HarOppholdMapper() : RadMapper<OppholdstillatelsePaSammeVilkar> {
        override fun mapRad(rad: Map<String, String>): OppholdstillatelsePaSammeVilkar {
            return OppholdstillatelsePaSammeVilkar(
                periode = Periode(
                    fom = parseDato(OppholdstillatelseDomenebegrep.GYLDIG_FRA_OG_MED, rad),
                    tom = parseDato(OppholdstillatelseDomenebegrep.GYLDIG_TIL_OG_MED, rad)
                ),
                harTillatelse = parseBooleanMedBooleanVerdi(OppholdstillatelseDomenebegrep.HAR_OPPHOLD, rad),
                type = OppholdstillaelsePaSammeVilkarType.valueOf(parseString(OppholdstillatelseDomenebegrep.OPPHOLDSTILLATELSE_TYPE, rad)),
                soknadIkkeAvgjort = false

            )
        }
    }

    class ArbeidsomfangKategoriMapper() : RadMapper<ArbeidomfangKategori?> {
        override fun mapRad(rad: Map<String, String>): ArbeidomfangKategori? {
            return parseValgfriString(OppholdstillatelseDomenebegrep.ARBEIDOMFANG_KATEGORI, rad)?.let {
                ArbeidomfangKategori.valueOf(it)
            }
        }
    }

    class ForesporselfodselsnummerMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(OppholdstillatelseDomenebegrep.FORESPORSELSFODSELSNUMMER, rad)
        }
    }

    class ArbeidsadgangTypeMapper() : RadMapper<ArbeidsadgangType> {
        override fun mapRad(rad: Map<String, String>): ArbeidsadgangType {
            return ArbeidsadgangType.valueOf(parseString(OppholdstillatelseDomenebegrep.ARBEIDSADGANG_TYPE, rad))
        }
    }

    class UttrekkstidspunktMapper() : RadMapper<LocalDateTime?> {
        override fun mapRad(rad: Map<String, String>): LocalDateTime? {
            return Datohjelper.parseIsoDatoTid(parseString(OppholdstillatelseDomenebegrep.UTTREKKSTIDSPUNKT, rad))
        }
    }

    class HarArbeidstilgangMapper() : RadMapper<Boolean> {
        override fun mapRad(rad: Map<String, String>): Boolean {
            return parseBooleanMedBooleanVerdi(OppholdstillatelseDomenebegrep.ARBEIDSADGANG, rad)
        }
    }

    class OppholdstillatelseMapper : RadMapper<Oppholdstillatelse> {
        override fun mapRad(rad: Map<String, String>): Oppholdstillatelse {
            val periode = Periode(
                parseValgfriDato(OppholdstillatelseDomenebegrep.GYLDIG_FRA_OG_MED, rad),
                parseValgfriDato(OppholdstillatelseDomenebegrep.GYLDIG_TIL_OG_MED, rad)
            )

            val gjeldendeOppholdstillatelse =
                when (
                    parseValgfriString(OppholdstillatelseDomenebegrep.OPPHOLDSTILLATELSE_KLASSE, rad)
                ) {
                    "Uavklart" -> createGjeldendeOppholdstatusMedUavklartOppholdstillatelse()
                    "EOSellerEFTAOpphold" -> createGjeldendeOppholdsstatusMedEOSellerEFTAOpphold(periode, rad)
                    "IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum" ->
                        createGjeldendeOppholdstatusMedIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(periode, rad)
                    else -> createGjeldendeOppholdstatusMedOppholdstillatelsePaSammeVilkar(periode, rad)
                }

            return Oppholdstillatelse(
                null,
                "fnr",
                parseValgfriBoolean(OppholdstillatelseDomenebegrep.AVGJOERELSE.nøkkel(), rad),
                gjeldendeOppholdstillatelse,
                Arbeidsadgang(periode, true, null, null),
                parseValgfriBoolean(OppholdstillatelseDomenebegrep.UAVKLART_FLYKTNINGSTATUS.nøkkel(), rad),
                parseValgfriBoolean(OppholdstillatelseDomenebegrep.HAR_FLYKTNINGSTATUS.nøkkel(), rad)

            )
        }

        private fun createGjeldendeOppholdsstatusMedEOSellerEFTAOpphold(
            periode: Periode,
            rad: Map<String, String>
        ): GjeldendeOppholdsstatus {
            return GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null,
                uavklart = null,
                eosellerEFTAOpphold = EOSellerEFTAOpphold(
                    periode = periode,
                    eosellerEFTAOppholdType = EOSellerEFTAOppholdType.valueOf(
                        parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_OPPHOLD, rad)
                    ),
                    eosellerEFTAGrunnlagskategoriOppholdsrettType =
                        parseValgfriString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT, rad)
                            ?.let { EOSellerEFTAGrunnlagskategoriOppholdsrettType.valueOf(it) },
                    eosellerEFTAGrunnlagskategoriOppholdstillatelseType =
                        EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType.valueOf(
                            parseString(OppholdstillatelseDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSTILLATELSE, rad)
                        )
                )
            )
        }

        private fun createGjeldendeOppholdstatusMedOppholdstillatelsePaSammeVilkar(
            periode: Periode,
            rad: Map<String, String>
        ): GjeldendeOppholdsstatus {
            return GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = OppholdstillatelsePaSammeVilkar(
                    periode = periode,
                    harTillatelse = parseValgfriBoolean(OppholdstillatelseDomenebegrep.HAR_OPPHOLDSTILLATELSE.nøkkel(), rad),
                    type = OppholdstillaelsePaSammeVilkarType.valueOf(parseString(OppholdstillatelseDomenebegrep.OPPHOLDSTILLATELSE_TYPE, rad)),
                    soknadIkkeAvgjort = parseBoolean(OppholdstillatelseDomenebegrep.OPPHOLDSTILLATELSE_PÅ_SAMME_VILKÅR_FLAGG, rad)
                ),
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null,
                eosellerEFTAOpphold = null,
                uavklart = null
            )
        }

        private fun createGjeldendeOppholdstatusMedIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
            periode: Periode,
            rad: Map<String, String>
        ): GjeldendeOppholdsstatus {
            return GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum =
                    IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
                        utvistMedInnreiseForbud = UtvistMedInnreiseForbud(
                            JaNeiUavklart.valueOf(parseString(OppholdstillatelseDomenebegrep.UTVISTMEDINNREISEFORBUD, rad))
                        ),
                        avslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak =
                            AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak(avgjorelsesDato = periode.fom),
                        ovrigIkkeOpphold = OvrigIkkeOpphold(
                            ovrigIkkeOppholdsKategori =
                                OvrigIkkeOppholdsKategori.valueOf(
                                    parseString(OppholdstillatelseDomenebegrep.OVRIG_IKKE_OPPHOLD_KATEGORI, rad)
                                )
                        )
                    ),
                eosellerEFTAOpphold = null,
                uavklart = null
            )
        }

        private fun createGjeldendeOppholdstatusMedUavklartOppholdstillatelse(): GjeldendeOppholdsstatus {
            return GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null,
                eosellerEFTAOpphold = null,
                uavklart = Uavklart()
            )
        }
    }

    class ArbeidsadgangMapper : RadMapper<Arbeidsadgang> {
        override fun mapRad(rad: Map<String, String>): Arbeidsadgang {
            val periode = Periode(
                parseValgfriDato(OppholdstillatelseDomenebegrep.GYLDIG_FRA_OG_MED, rad),
                parseValgfriDato(OppholdstillatelseDomenebegrep.GYLDIG_TIL_OG_MED, rad)
            )

            return Arbeidsadgang(
                periode,
                parseBoolean(OppholdstillatelseDomenebegrep.ARBEIDSADGANG, rad),
                ArbeidsadgangTypeMapper().mapRad(rad),
                ArbeidsomfangKategoriMapper().mapRad(rad)
            )
        }
    }
}

enum class OppholdstillatelseDomenebegrep(val nøkkel: String) : Domenenøkkel {
    ARBEIDSADGANG("Arbeidsadgang"),
    ARBEIDOMFANG_KATEGORI("ArbeidomfangKategori"),
    ARBEIDSADGANG_TYPE("ArbeidsadgangType"),
    AVGJOERELSE("Avgjørelse"),
    AVGJORELSEDATO("Avgjorelsesdato"),
    EOS_ELLER_EFTA_OPPHOLD("EOSEllerEFTAOpphold"),
    EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT("EOSellerEFTAGrunnlagskategoriOppholdsrett"),
    EOS_ELLER_EFTA_KATEGORI_OPPHOLDSTILLATELSE("EOSellerEFTAGrunnlagskategoriOppholdstillatelse"),
    EOS_ELLER_EFTA_PERIODE_FOM("EØS eller EFTA Gyldig fra og med"),
    EOS_ELLER_EFTA_PERIODE_TOM("EØS eller EFTA Gyldig til og med"),
    HAR_OPPHOLD("Har opphold"),
    HAR_OPPHOLDSTILLATELSE("Har tillatelse"),
    HAR_FLYKTNINGSTATUS("Har flyktningstatus"),
    JA_NEI_UAVKLART("JaNeiUavklart"),
    OVRIG_IKKE_OPPHOLD_KATEGORI("OvrigIkkeOppholdsKategori"),
    GYLDIG_FRA_OG_MED("Gyldig fra og med"),
    GYLDIG_TIL_OG_MED("Gyldig til og med"),
    OPPHOLDSTILLATELSE_TYPE("Type"),
    OPPHOLDSTILLATELSE_KLASSE("Klasse"),
    OPPHOLDSTILLATELSE_PÅ_SAMME_VILKÅR_FLAGG("Oppholdstillatelse på samme vilkår flagg"),
    FORESPORSELSFODSELSNUMMER("Foresporselsfodselsnummer"),
    UAVKLART("Uavklart"),
    UAVKLART_FLYKTNINGSTATUS("Uavklart flyktningstatus"),
    UTTREKKSTIDSPUNKT("Uttrekkstidspunkt"),
    UTVISTMEDINNREISEFORBUD("Utvist med innreiseforbud")
    ;

    override fun nøkkel(): String {
        return nøkkel
    }
}
