package no.nav.medlemskap.services.aareg

import mu.KotlinLogging
import no.nav.medlemskap.clients.aareg.*
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.arbeidsforhold.*
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.arbeidsforhold.PermisjonPermittering
import no.nav.medlemskap.domene.arbeidsforhold.Utenlandsopphold
import no.nav.medlemskap.services.ereg.mapOrganisasjonTilArbeidsgiver

private val logger = KotlinLogging.logger { }

fun mapAaregResultat(arbeidsforhold: List<AaRegArbeidsforhold>, mappedOrganisasjonAsArbeidsgiver: List<Arbeidsgiver>): List<Arbeidsforhold> {
    return arbeidsforhold.map {
        Arbeidsforhold(
            periode = mapPeriodeFraArbeidsforhold(it),
            utenlandsopphold = mapUtenlandsopphold(it),
            arbeidsforholdstype = Arbeidsforholdstype.fraArbeidsforholdtypeVerdi(it.type),
            arbeidsgivertype = mapArbeidsgiverType(it.arbeidsgiver.type),
            arbeidsgiver = mappedOrganisasjonAsArbeidsgiver.first { p -> p.organisasjonsnummer == it.arbeidsgiver.organisasjonsnummer },
            arbeidsavtaler = mapArbeidsAvtaler(it),
            permisjonPermittering = mapPermisjonsPermittering(it)
        )
    }
}

fun mapArbeidsforhold(arbeidsforholdMedOrganisasjon: List<ArbeidsforholdOrganisasjon>): List<Arbeidsforhold> {
    return arbeidsforholdMedOrganisasjon.map {
        Arbeidsforhold(
            periode = mapPeriodeFraArbeidsforhold(it.arbeidsforhold),
            utenlandsopphold = mapUtenlandsopphold(it.arbeidsforhold),
            arbeidsforholdstype = Arbeidsforholdstype.fraArbeidsforholdtypeVerdi(it.arbeidsforhold.type),
            arbeidsgivertype = mapArbeidsgiverType(it.arbeidsforhold.arbeidsgiver.type),
            arbeidsgiver = mapOrganisasjonTilArbeidsgiver(it.organisasjon, it.juridiskeEnheter),
            arbeidsavtaler = mapArbeidsAvtaler(it.arbeidsforhold),
            permisjonPermittering = mapPermisjonsPermittering(it.arbeidsforhold)
        )
    }
}
fun mapPermisjonsPermittering(arbeidsforhold: AaRegArbeidsforhold): List<PermisjonPermittering>? {
    return arbeidsforhold.permisjonPermitteringer?.map {
        try {
            val type = PermisjonPermitteringType.fraPermisjonPermitteringVerdi(it.type)
        } catch (e: Exception) {
            logger.warn("Ukjent permisjonspermiterings verdi: ${it.type}")
        }

        PermisjonPermittering(
            periode = mapPeriodeTilPermisjonsPermitteringer(it),
            type = PermisjonPermitteringType.fraPermisjonPermitteringVerdi(it.type),
            varslingskode = it.varslingskode,
            permisjonPermitteringId = it.permisjonPermitteringId,
            prosent = it.prosent
        )
    }
}

fun mapArbeidsgiverType(type: AaRegOpplysningspliktigArbeidsgiverType): OpplysningspliktigArbeidsgiverType =
    when (type) {
        AaRegOpplysningspliktigArbeidsgiverType.Person -> OpplysningspliktigArbeidsgiverType.Person
        AaRegOpplysningspliktigArbeidsgiverType.Organisasjon -> OpplysningspliktigArbeidsgiverType.Organisasjon
    }

fun mapPeriodeFraArbeidsforhold(arbeidsforhold: AaRegArbeidsforhold): Periode {
    return Periode(
        fom = arbeidsforhold.ansettelsesperiode.periode.fom,
        tom = arbeidsforhold.ansettelsesperiode.periode.tom
    )
}

fun mapArbeidsAvtaler(arbeidsforhold: AaRegArbeidsforhold): List<Arbeidsavtale> {
    return arbeidsforhold.arbeidsavtaler.map {
        Arbeidsavtale(
            periode = mapPeriodeTilArbeidsavtale(it),
            gyldighetsperiode = Periode(it.gyldighetsperiode.fom, it.gyldighetsperiode.tom),
            skipsregister = Skipsregister.fraSkipsregisterVerdi(it.skipsregister),

            beregnetAntallTimerPrUke = it.beregnetAntallTimerPrUke,
            stillingsprosent = it.stillingsprosent,
            yrkeskode = it.yrke,
            fartsomraade = Fartsomraade.fraFartsomraadeVerdi(it.fartsomraade),
            skipstype = Skipstype.fraSkipstypeVerdi(it.skipstype)
        )
    }
}

fun mapPeriodeTilArbeidsavtale(arbeidsavtale: AaRegArbeidsavtale): Periode {
    return Periode(
        fom = arbeidsavtale.bruksperiode.fom.toLocalDate(),
        tom = arbeidsavtale.bruksperiode.tom?.toLocalDate()
    )
}

fun mapUtenlandsopphold(arbeidsforhold: AaRegArbeidsforhold): List<Utenlandsopphold>? {
    return arbeidsforhold.utenlandsopphold?.map {
        Utenlandsopphold(
            landkode = it.landkode,
            periode = mapPeriodeTilUtenlandsopphold(it),
            rapporteringsperiode = it.rapporteringsperiode
        )
    }
}

fun mapPeriodeTilUtenlandsopphold(utenlandsopphold: AaRegUtenlandsopphold): Periode {
    return Periode(
        fom = utenlandsopphold.periode?.fom,
        tom = utenlandsopphold.periode?.tom
    )
}

fun mapPeriodeTilPermisjonsPermitteringer(permittering: AaRegPermisjonPermittering): Periode {
    return Periode(
        fom = permittering.periode?.fom,
        tom = permittering.periode?.tom
    )
}
