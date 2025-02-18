package no.nav.medlemskap.domene.arbeidsforhold

import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.finnOverlappendePermisjoner
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.totaltantallDagerIKontrollPeriode

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PermisjonPermiteringTest {

    @Test
    fun finnOverlappendePerioderTest_medLikKontrollperiodeSomPermisjonspermittering() {
        val kontrollperiode = Kontrollperiode(fom = LocalDate.now(), tom = LocalDate.now())
        val permisjonPermittering = PermisjonPermittering(
            Periode(fom = LocalDate.now(),
                tom = LocalDate.now()),
                permisjonPermitteringId = "",
                prosent = 1.0,
                PermisjonPermitteringType.PERMISJON,
                varslingskode = "",
            )
        val arbeidsforhold = genererDummyArbeidsforhold(permisjonPermittering)
        val funnet = listOf(arbeidsforhold).finnOverlappendePermisjoner(kontrollPeriode = kontrollperiode)
        Assertions.assertTrue(funnet.isNotEmpty())
    }

    @Test
    fun finnOverlappendePerioderTest_DerPermisjonpermitteringErForKontrollperiode() {
        val kontrollperiode = Kontrollperiode(fom = LocalDate.now(), tom = LocalDate.now())
        val permisjonPermittering = PermisjonPermittering(
            Periode(fom = LocalDate.now().minusYears(1),
                tom = LocalDate.now().minusDays(1)),
            permisjonPermitteringId = "",
            prosent = 1.0,
            PermisjonPermitteringType.PERMISJON,
            varslingskode = "",
        )
        val arbeidsforhold = genererDummyArbeidsforhold(permisjonPermittering)
        val funnet = listOf(arbeidsforhold).finnOverlappendePermisjoner(kontrollPeriode = kontrollperiode)
        Assertions.assertTrue(funnet.isEmpty())
    }
    /*Sjekker overlappende eller samme dato.*/
    @Test
    fun finnOverlappendePerioderTest_DerPermisjonpermitteringStarterForKontrollperiodeOgEnderI() {
        val kontrollperiode = Kontrollperiode(fom = LocalDate.now(), tom = LocalDate.now())
        val permisjonPermittering = PermisjonPermittering(
            Periode(fom = LocalDate.now().minusYears(1),
                tom = LocalDate.now()),
            permisjonPermitteringId = "",
            prosent = 1.0,
            PermisjonPermitteringType.PERMISJON,
            varslingskode = "",
        )
        val arbeidsforhold = genererDummyArbeidsforhold(permisjonPermittering)
        val funnet = listOf(arbeidsforhold).finnOverlappendePermisjoner(kontrollPeriode = kontrollperiode)
        Assertions.assertTrue(funnet.isNotEmpty())
    }

    @Test
    fun finnOverlappendePerioderTest_DerPermisjonpermitteringStarterForKontrollperiodeOgEnderEtter() {
        val kontrollperiode = Kontrollperiode(fom = LocalDate.now(), tom = LocalDate.now())
        val permisjonPermittering = PermisjonPermittering(
            Periode(fom = LocalDate.now().minusYears(1),
                tom = LocalDate.now().plusYears(1)),
            permisjonPermitteringId = "",
            prosent = 1.0,
            PermisjonPermitteringType.PERMISJON,
            varslingskode = "",
        )
        val arbeidsforhold = genererDummyArbeidsforhold(permisjonPermittering)
        val funnet = listOf(arbeidsforhold).finnOverlappendePermisjoner(kontrollPeriode = kontrollperiode)
        Assertions.assertTrue(funnet.isNotEmpty())
    }
    @Test
    fun TellAlleDagerIPermisjonsTestMedEnPermitering() {
        val kontrollperiode = Kontrollperiode(LocalDate.now().minusYears(1), LocalDate.now())
        val permisjonPermittering = PermisjonPermittering(
            Periode(fom = LocalDate.now().minusDays(10),
                tom = LocalDate.now().plusDays(10)),
            permisjonPermitteringId = "",
            prosent = 1.0,
            PermisjonPermitteringType.PERMISJON,
            varslingskode = "",
        )

        val antallDagerPermisjon = listOf(permisjonPermittering).totaltantallDagerIKontrollPeriode(kontrollperiode)
        Assertions.assertEquals(20,antallDagerPermisjon,"Totalt antall dager er ikke kalkulert korrekt")
    }
    @Test
    fun TellAlleDagerIPermisjonsTestFlerePermiteringer() {
        val kontrollperiode = Kontrollperiode(LocalDate.now().minusYears(1), LocalDate.now())
        val permisjonPermittering = PermisjonPermittering(
            Periode(fom = LocalDate.now().minusDays(10),
                tom = LocalDate.now().plusDays(10)),
            permisjonPermitteringId = "",
            prosent = 1.0,
            PermisjonPermitteringType.PERMISJON,
            varslingskode = "",
        )

        val antallDagerPermisjon = listOf(permisjonPermittering,permisjonPermittering).totaltantallDagerIKontrollPeriode(kontrollperiode)
        Assertions.assertEquals(40,antallDagerPermisjon,"Totalt antall dager er ikke kalkulert korrekt")
    }

    private fun genererDummyArbeidsforhold(permisjonPermittering: PermisjonPermittering): Arbeidsforhold {
        val arbeidsforhold = Arbeidsforhold(
            periode = Periode(fom = LocalDate.now(), tom = LocalDate.now()),
            utenlandsopphold = emptyList(),
            arbeidsgivertype = OpplysningspliktigArbeidsgiverType.Organisasjon,
            arbeidsgiver = Arbeidsgiver(
                navn = "",
                organisasjonsnummer = "",
                ansatte = emptyList(),
                konkursStatus = emptyList(),
                juridiskeEnheter = emptyList()
            ),
            arbeidsforholdstype = Arbeidsforholdstype.NORMALT,
            arbeidsavtaler = emptyList(),
            permisjonPermittering = listOf(permisjonPermittering),
        )
        return arbeidsforhold
    }

}