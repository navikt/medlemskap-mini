package no.nav.medlemskap.services.udi
import no.nav.medlemskap.domene.*
import no.nav.medlemskap.domene.JaNeiUavklart.Companion.fraJaNeiUavklartVerdi
import no.udi.mt_1067_nav_data.v1.HentPersonstatusResultat
import no.udi.mt_1067_nav_data.v1.JaNeiUavklart
import java.time.LocalDate
import java.time.LocalDateTime
import javax.xml.datatype.XMLGregorianCalendar

object UdiMapper {
    fun mapTilOppholdstillatelse(oppholdstillatelse: HentPersonstatusResultat): Oppholdstillatelse {
        return Oppholdstillatelse(
            uttrekkstidspunkt = oppholdstillatelse.uttrekkstidspunkt.asDate(),
            foresporselsfodselsnummer = oppholdstillatelse.foresporselsfodselsnummer,
            gjeldendeOppholdsstatus = mapGjeldendeOppholdsstatus(oppholdstillatelse.gjeldendeOppholdsstatus),
            avgjoerelse = null,
            harFlyktningstatus = null,
            uavklartFlyktningstatus = null,
            arbeidsadgang = mapArbeidsadgang(oppholdstillatelse.arbeidsadgang)
        )
    }

    private fun mapArbeidsadgang(arbeidsadgang: no.udi.mt_1067_nav_data.v1.Arbeidsadgang?): Arbeidsadgang? {
        if (arbeidsadgang == null) {
            return null
        }
        return Arbeidsadgang(
            harArbeidsadgang = arbeidsadgang.harArbeidsadgang == JaNeiUavklart.JA,
            arbeidsadgangType = ArbeidsadgangType.fraArbeidsadgangType(arbeidsadgang.typeArbeidsadgang?.value()),
            arbeidsomfang = ArbeidomfangKategori.fraArbeidomfang(arbeidsadgang.arbeidsOmfang?.value()),
            periode = mapPeriode(arbeidsadgang.arbeidsadgangsPeriode)
        )
    }

    private fun mapPeriode(periode: no.udi.mt_1067_nav_data.v1.Periode?): Periode {
        return Periode(
            fom = periode?.fra.asLocalDate(),
            tom = periode?.til.asLocalDate()
        )
    }

    private fun mapGjeldendeOppholdsstatus(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus?): GjeldendeOppholdsstatus? {
        if (gjeldendeOppholdsstatus != null) {
            return GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = mapOppholdstillatelseEllerOppholdsPaSammeVilkar(gjeldendeOppholdsstatus),
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = mapIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(gjeldendeOppholdsstatus),
                eosellerEFTAOpphold = mapEOSEllerEFTAOpphold(gjeldendeOppholdsstatus),
                uavklart = mapUavklart(gjeldendeOppholdsstatus)
            )
        }
        return null
    }

    private fun mapUavklart(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): Uavklart? {
        if (gjeldendeOppholdsstatus.uavklart != null) {
            return Uavklart(
                uavklart = true
            )
        }
        return null
    }

    private fun mapIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum? {

        if (gjeldendeOppholdsstatus.ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum != null) {
            return IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
                utvistMedInnreiseForbud = UtvistMedInnreiseForbud(
                    innreiseForbud =
                        fraJaNeiUavklartVerdi(
                            gjeldendeOppholdsstatus
                                .ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
                                ?.utvistMedInnreiseForbud
                                ?.innreiseForbud
                                ?.value()
                        )
                ),
                avslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak =
                    AvslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak(
                        gjeldendeOppholdsstatus
                            .ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
                            ?.avslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak
                            ?.avgjorelsesDato
                            .asLocalDate()
                    ),
                ovrigIkkeOpphold = OvrigIkkeOpphold(
                    ovrigIkkeOppholdsKategori = OvrigIkkeOppholdsKategori.fraOvrigIkkeOppholdsKategoriType(
                        gjeldendeOppholdsstatus
                            .ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum
                            ?.ovrigIkkeOpphold
                            ?.arsak
                            ?.value()
                    )
                )
            )
        }
        return null
    }

    private fun mapEOSEllerEFTAOpphold(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): EOSellerEFTAOpphold? {
        if (gjeldendeOppholdsstatus.eoSellerEFTAOpphold != null) {
            if (gjeldendeOppholdsstatus.eoSellerEFTAOpphold.eoSellerEFTABeslutningOmOppholdsrett != null) {
                return mapEOSEllerEFTABeslutningOmOppholdsrett(gjeldendeOppholdsstatus)
            }
            if (gjeldendeOppholdsstatus.eoSellerEFTAOpphold.eoSellerEFTAOppholdstillatelse != null) {
                return mapEoSellerEFTAOppholdstillatelse(gjeldendeOppholdsstatus)
            }
            if (gjeldendeOppholdsstatus.eoSellerEFTAOpphold.eoSellerEFTAVedtakOmVarigOppholdsrett != null) {
                return mapEOSellerEFTAVedtakOmVarigOppholdsrett(gjeldendeOppholdsstatus)
            }
        }
        return null
    }

    private fun mapEOSellerEFTAVedtakOmVarigOppholdsrett(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): EOSellerEFTAOpphold {
        val eoSellerEFTAVedtakOmVarigOppholdsrett = gjeldendeOppholdsstatus.eoSellerEFTAOpphold.eoSellerEFTAVedtakOmVarigOppholdsrett
        return EOSellerEFTAOpphold(
            periode = Periode(
                fom = eoSellerEFTAVedtakOmVarigOppholdsrett.oppholdsrettsPeriode.fra.asLocalDate(),
                tom = eoSellerEFTAVedtakOmVarigOppholdsrett.oppholdsrettsPeriode.til.asLocalDate()
            ),
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null,
            eosellerEFTAGrunnlagskategoriOppholdsrettType = EOSellerEFTAGrunnlagskategoriOppholdsrettType
                .fraEOSellerEFTAGrunnlagskategoriOppholdsrettType(eoSellerEFTAVedtakOmVarigOppholdsrett.eosOppholdsgrunnlag.value()),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_VEDTAK_OM_VARIG_OPPHOLDSRETT
        )
    }

    private fun mapEoSellerEFTAOppholdstillatelse(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): EOSellerEFTAOpphold {
        val eosEllerEftaOppholdstillatelse = gjeldendeOppholdsstatus.eoSellerEFTAOpphold.eoSellerEFTAOppholdstillatelse
        return EOSellerEFTAOpphold(
            periode = Periode(
                fom = eosEllerEftaOppholdstillatelse.oppholdstillatelsePeriode.fra.asLocalDate(),
                tom = eosEllerEftaOppholdstillatelse.oppholdstillatelsePeriode.til.asLocalDate()
            ),
            eosellerEFTAGrunnlagskategoriOppholdsrettType = null,
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType =
                EOSellerEFTAGrunnlagskategoriOppholdsTillatelseType
                    .fraEOSellerEFTAGrunnlagskategoriOppholdsTillatelseType(
                        eosEllerEftaOppholdstillatelse.eosOppholdsgrunnlag.value()
                    ),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_OPPHOLDSTILLATELSE
        )
    }

    private fun mapEOSEllerEFTABeslutningOmOppholdsrett(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): EOSellerEFTAOpphold {
        val eoSellerEFTABeslutningOmOppholdsrett = gjeldendeOppholdsstatus.eoSellerEFTAOpphold.eoSellerEFTABeslutningOmOppholdsrett
        return EOSellerEFTAOpphold(
            periode = Periode(
                fom =
                    eoSellerEFTABeslutningOmOppholdsrett.oppholdsrettsPeriode.fra.asLocalDate(),
                tom =
                    eoSellerEFTABeslutningOmOppholdsrett.oppholdsrettsPeriode.til.asLocalDate()
            ),
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null,
            eosellerEFTAGrunnlagskategoriOppholdsrettType =
                EOSellerEFTAGrunnlagskategoriOppholdsrettType
                    .fraEOSellerEFTAGrunnlagskategoriOppholdsrettType(
                        eoSellerEFTABeslutningOmOppholdsrett.eosOppholdsgrunnlag.value()
                    ),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT
        )
    }

    private fun mapOppholdstillatelseEllerOppholdsPaSammeVilkar(gjeldendeOppholdsstatus: no.udi.mt_1067_nav_data.v1.GjeldendeOppholdsstatus): OppholdstillatelsePaSammeVilkar? {

        if (gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar != null) {
            val oppholdstillatelsePaSammeVilkarPeriode = Periode(
                gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar?.oppholdstillatelsePeriode?.fra.asLocalDate(),
                gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar?.oppholdstillatelsePeriode?.til.asLocalDate()
            )
            val oppholdstillatelsePaSammeVilkarType =
                OppholdstillaelsePaSammeVilkarType.fraOppholdstillatelsePaSammeVilkarTypeVerdi(
                    gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar?.oppholdstillatelse?.oppholdstillatelseType?.value()
                )

            val harTillatelse = gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar?.oppholdstillatelse?.oppholdstillatelseType != null

            val soknadIkkeAvgjort: Boolean = gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar.oppholdPaSammeVilkar != null

            return OppholdstillatelsePaSammeVilkar(
                periode = oppholdstillatelsePaSammeVilkarPeriode,
                harTillatelse = harTillatelse,
                soknadIkkeAvgjort = soknadIkkeAvgjort,
                type = oppholdstillatelsePaSammeVilkarType
            )
        }
        return null
    }

    private fun XMLGregorianCalendar?.asLocalDate(): LocalDate? = this?.toGregorianCalendar()?.toZonedDateTime()?.toLocalDate()

    private fun XMLGregorianCalendar?.asDate(): LocalDateTime? = this?.toGregorianCalendar()?.toZonedDateTime()?.toLocalDateTime()
}
