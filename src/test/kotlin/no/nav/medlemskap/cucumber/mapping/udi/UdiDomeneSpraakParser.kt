package no.nav.medlemskap.cucumber.mapping.udi

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.BasisDomeneParser.Companion.mapDataTable
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import no.udi.mt_1067_nav_data.v1.*
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

class UdiDomeneSpraakParser {
    fun mapHarArbeidsadgang(dataTable: DataTable): JaNeiUavklart {
        return mapDataTable(dataTable, HarArbeidsadgangMapper())[0]
    }

    fun mapArbeidsomfang(dataTable: DataTable): ArbeidOmfangKategori {
        return mapDataTable(dataTable, ArbeidsomfangTypeMapper())[0]
    }

    fun mapforesporselfodselsnummer(dataTable: DataTable): String {
        return mapDataTable(dataTable, ForesporselfodselsnummerMapper())[0]
    }

    fun mapArbeidsadgangType(dataTable: DataTable): ArbeidsadgangType {
        return mapDataTable(dataTable, ArbeidsadgangTypeMapper())[0]
    }

    fun mapUttrekkstidspunkt(dataTable: DataTable): XMLGregorianCalendar {
        return mapDataTable(dataTable, UttrekkstidspunktMapper())[0]
    }

    fun mapEffektueringsdato(dataTable: DataTable): XMLGregorianCalendar? {
        return mapDataTable(dataTable, EffektueringsdatoMapper())[0]
    }

    fun mapUavklart(dataTable: DataTable): Uavklart {
        return mapDataTable(dataTable, UavklartMapper())[0]
    }

    fun mapOppholdstillatelse(dataTable: DataTable): Oppholdstillatelse? {
        return mapDataTable(dataTable, OppholdstillatelseMapper())[0]
    }

    fun mapPeriode(dataTable: DataTable): Periode {
        return mapDataTable(dataTable, PeriodeMapper())[0]
    }

    fun mapEOsOpppholdgrunnlagOppholdsrett(dataTable: DataTable): EOSellerEFTAGrunnlagskategoriOppholdsrett {
        return mapDataTable(dataTable, BeslutninOmOppholdsrettMapper())[0]
    }

    fun mapEOsOpppholdgrunnlagOppholdstillatelse(dataTable: DataTable): EOSellerEFTAGrunnlagskategoriOppholdstillatelse {
        return mapDataTable(dataTable, KategoriOppholdstillatelseMapper())[0]
    }

    fun mapOppholdPaSammeVilkar(dataTable: DataTable): OppholdPaSammeVilkar {
        return mapDataTable(dataTable, OppholdPaSammeVilkarMapper())[0]
    }

    fun mapJaNeiUavklart(dataTable: DataTable): JaNeiUavklart {
        return mapDataTable(dataTable, JaNeiUavklartMapper())[0]
    }

    fun mapAvgjorelsesdato(dataTable: DataTable): XMLGregorianCalendar? {
        return mapDataTable(dataTable, AvgjorelsesDatoMapper())[0]
    }

    fun mapOvrigIkkeOpphold(dataTable: DataTable): OvrigIkkeOppholdsKategori {
        return mapDataTable(dataTable, OvrigIkkeOppholdKategoriMapper())[0]
    }

    class BeslutninOmOppholdsrettMapper() : RadMapper<EOSellerEFTAGrunnlagskategoriOppholdsrett> {
        override fun mapRad(rad: Map<String, String>): EOSellerEFTAGrunnlagskategoriOppholdsrett {
            return EOSellerEFTAGrunnlagskategoriOppholdsrett.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT, rad))
        }
    }

    class KategoriOppholdstillatelseMapper() : RadMapper<EOSellerEFTAGrunnlagskategoriOppholdstillatelse> {
        override fun mapRad(rad: Map<String, String>): EOSellerEFTAGrunnlagskategoriOppholdstillatelse {
            return EOSellerEFTAGrunnlagskategoriOppholdstillatelse.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.EOS_ELLER_EFTA_KATEGORI_OPPHOLDSTILLATELSE, rad))
        }
    }
    class UavklartMapper() : RadMapper<Uavklart> {
        override fun mapRad(rad: Map<String, String>): Uavklart {
            val uavklart = Uavklart()
            return Uavklart()
        }
    }

    fun mapEOSEllerEftaOpphold(dataTable: DataTable) {
    }

    class OvrigIkkeOppholdKategoriMapper() : RadMapper<OvrigIkkeOppholdsKategori> {
        override fun mapRad(rad: Map<String, String>): OvrigIkkeOppholdsKategori {
            return OvrigIkkeOppholdsKategori.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.OVRIG_IKKE_OPPHOLD, rad))
        }
    }

    class AvgjorelsesDatoMapper() : RadMapper<XMLGregorianCalendar> {
        override fun mapRad(rad: Map<String, String>): XMLGregorianCalendar {
            val avgjorelseLocalDateTime = BasisDomeneParser.parseString(UdiDomenebegrep.AVGJORELSESDATO, rad)
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(avgjorelseLocalDateTime)
        }
    }
    class JaNeiUavklartMapper() : RadMapper<JaNeiUavklart> {
        override fun mapRad(rad: Map<String, String>): JaNeiUavklart {
            return JaNeiUavklart.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.JA_NEI_UAVKLART, rad))
        }
    }

    class HarArbeidsadgangMapper() : RadMapper<JaNeiUavklart> {
        override fun mapRad(rad: Map<String, String>): JaNeiUavklart {
            return JaNeiUavklart.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.ARBEIDSADGANG, rad))
        }
    }

    class OppholdPaSammeVilkarMapper() : RadMapper<OppholdPaSammeVilkar> {
        override fun mapRad(rad: Map<String, String>): OppholdPaSammeVilkar {
            return OppholdPaSammeVilkar()
        }
    }

    class OppholdstillatelseMapper() : RadMapper<Oppholdstillatelse> {
        override fun mapRad(rad: Map<String, String>): Oppholdstillatelse {
            val vedtaksdato = BasisDomeneParser.parseString(UdiDomenebegrep.VEDTAKSDATO, rad)
            val vedtaksdatoXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(vedtaksdato)
            val oppholdstillatelse = Oppholdstillatelse()

            oppholdstillatelse.oppholdstillatelseType = OppholdstillatelseKategori.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.OPPHOLDSTYPE, rad))
            oppholdstillatelse.vedtaksDato = vedtaksdatoXMLGregorianCalendar
            return oppholdstillatelse
        }
    }

    class ArbeidsomfangTypeMapper() : RadMapper<ArbeidOmfangKategori> {
        override fun mapRad(rad: Map<String, String>): ArbeidOmfangKategori {
            return ArbeidOmfangKategori.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.ARBEIDOMFANG_KATEGORI, rad))
        }
    }

    class ForesporselfodselsnummerMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return BasisDomeneParser.parseString(UdiDomenebegrep.FORESPORSELSFODSELSNUMMER, rad)
        }
    }

    class ArbeidsadgangTypeMapper() : RadMapper<ArbeidsadgangType> {
        override fun mapRad(rad: Map<String, String>): ArbeidsadgangType {
            return ArbeidsadgangType.valueOf(BasisDomeneParser.parseString(UdiDomenebegrep.ARBEIDSADGANG_TYPE, rad))
        }
    }

    class PeriodeMapper() : RadMapper<Periode> {
        override fun mapRad(rad: Map<String, String>): Periode {
            val fom = BasisDomeneParser.parseValgfriString(UdiDomenebegrep.GYLDIG_FRA_OG_MED, rad)
            val tom = BasisDomeneParser.parseValgfriString(UdiDomenebegrep.GYLDIG_TIL_OG_MED, rad)
            val periode = Periode()
            if (fom != null) {
                periode.fra = DatatypeFactory.newInstance().newXMLGregorianCalendar(fom)
            }
            if (tom != null) {
                periode.til = DatatypeFactory.newInstance().newXMLGregorianCalendar(tom)
            }
            return periode
        }
    }

    class UttrekkstidspunktMapper() : RadMapper<XMLGregorianCalendar> {
        override fun mapRad(rad: Map<String, String>): XMLGregorianCalendar {

            val uttrekkstidpunktLocalDateTime = BasisDomeneParser.parseString(UdiDomenebegrep.UTTREKKSTIDSPUNKT, rad)
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(uttrekkstidpunktLocalDateTime)
        }
    }

    class EffektueringsdatoMapper() : RadMapper<XMLGregorianCalendar> {
        override fun mapRad(rad: Map<String, String>): XMLGregorianCalendar {

            val uttrekkstidpunktLocalDateTime = BasisDomeneParser.parseString(UdiDomenebegrep.EFFEKTUERINGSDATO, rad)
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(uttrekkstidpunktLocalDateTime)
        }
    }
}

enum class UdiDomenebegrep(val nøkkel: String) : Domenenøkkel {
    ARBEIDSADGANG("Arbeidsadgang"),
    ARBEIDOMFANG_KATEGORI("ArbeidomfangKategori"),
    ARBEIDSADGANG_TYPE("ArbeidsadgangType"),
    AVGJORELSESDATO("Avgjørelsesdato"),
    EOS_ELLER_EFTA_KATEGORI_OPPHOLDSRETT("EOSellerEFTAGrunnlagskategoriOppholdsrett"),
    EOS_ELLER_EFTA_KATEGORI_OPPHOLDSTILLATELSE("EOSellerEFTAGrunnlagskategoriOppholdstillatelse"),
    EFFEKTUERINGSDATO("Effektueringsdato"),
    FORESPORSELSFODSELSNUMMER("Foresporselsfodselsnummer"),
    OPPHOLDSTILLATELSE("Oppholdstillatelse"),
    OPPHOLD_PA_SAMME_VILKAR("OppholdPaSammeVilkar"),
    OVRIG_IKKE_OPPHOLD("OvrigIkkeOppholdsKategori"),
    OPPHOLDSTYPE("OppholdstillatelseType"),
    JA_NEI_UAVKLART("JaNeiUavklart"),
    GYLDIG_FRA_OG_MED("Gyldig fra og med"),
    GYLDIG_TIL_OG_MED("Gyldig til og med"),
    UTTREKKSTIDSPUNKT("Uttrekkstidspunkt"),
    VEDTAKSDATO("Vedtaksdato")
    ;

    override fun nøkkel(): String {
        return nøkkel
    }
}
