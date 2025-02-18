package no.nav.medlemskap.services.ereg

import no.nav.medlemskap.clients.ereg.Navn
import no.nav.medlemskap.clients.ereg.Organisasjon
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.arbeidsforhold.Ansatte
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsgiver
import no.nav.medlemskap.domene.arbeidsforhold.JuridiskEnhet

fun mapOrganisasjonTilArbeidsgiver(organisasjon: Organisasjon, juridiskeEnheter: List<Organisasjon>?): Arbeidsgiver {

    val ansatte = organisasjon.organisasjonDetaljer?.ansatte
    val konkursStatus = organisasjon.organisasjonDetaljer?.statuser?.map { it -> it?.kode }
    return Arbeidsgiver(
        navn = finnOrgNavn(organisasjon.navn),
        organisasjonsnummer = organisasjon.organisasjonsnummer,
        ansatte = mapAnsatte(ansatte),
        konkursStatus = konkursStatus,
        juridiskeEnheter = mapJuridiskeEnheter(juridiskeEnheter)
    )
}

fun mapJuridiskeEnheter(juridiskeEnheter: List<Organisasjon>?): List<JuridiskEnhet?>? {
    return juridiskeEnheter?.map {
        JuridiskEnhet(
            organisasjonsnummer = it.organisasjonsnummer,
            enhetstype = it.organisasjonDetaljer?.enhetstyper?.first()?.enhetstype,
            antallAnsatte = it.organisasjonDetaljer?.ansatte?.first()?.antall
        )
    }
}

fun mapAnsatte(ansatte: List<no.nav.medlemskap.clients.ereg.Ansatte>?): List<Ansatte>? =
    ansatte?.map {
        Ansatte(
            antall = it.antall,
            gyldighetsperiode = mapGyldighetsperiode(it.gyldighetsperiode)
        )
    }

fun mapGyldighetsperiode(gyldighetsperiode: no.nav.medlemskap.clients.ereg.Gyldighetsperiode?): Periode? =
    gyldighetsperiode?.let { Periode(fom = it.fom, tom = it.tom) }

fun mapBruksperiode(bruksperiode: no.nav.medlemskap.clients.ereg.Bruksperiode?): Periode? =
    bruksperiode?.let { Periode(fom = it.fom, tom = it.tom) }

fun finnOrgNavn(orgNavn: Navn?): String {
    return orgNavn?.navnelinje1
        ?: orgNavn?.navnelinje2
        ?: orgNavn?.navnelinje3
        ?: orgNavn?.navnelinje4
        ?: orgNavn?.navnelinje5
        ?: orgNavn?.redigertnavn
        ?: "null"
}
