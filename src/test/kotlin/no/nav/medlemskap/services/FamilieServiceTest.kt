package no.nav.medlemskap.services

import assertk.assertThat
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.test.runBlockingTest
import no.nav.medlemskap.common.CoroutinesTestExtension
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.arbeidsforhold.*
import no.nav.medlemskap.domene.barn.PersonhistorikkBarn
import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.services.aareg.AaRegService
import no.nav.medlemskap.services.pdl.PdlService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.time.LocalDate

internal class FamilieServiceTest {
    lateinit var aaRegService: AaRegService
    lateinit var pdlService: PdlService
    lateinit var familieService: FamilieService

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun init() {
        aaRegService = mockkClass(AaRegService::class)
        pdlService = mockkClass(PdlService::class)

        familieService = FamilieService(aaRegService, pdlService)
    }

    @Test
    fun hentDataOmBarn() = runBlockingTest {
        val fnrTilBarnUnder25år = "25079528660"
        val callId = "12"

        coEvery { pdlService.hentPersonHistorikkTilBarn(fnrTilBarnUnder25år, callId) } returns personhistorikkBarn(
            fnrTilBarnUnder25år
        )

        val dataOmBarn = familieService.hentDataOmBarn(listOf(fnrTilBarnUnder25år), callId)

        dataOmBarn.map { it.personhistorikkBarn.ident }.shouldContainExactly(fnrTilBarnUnder25år)
    }

    @Test
    fun hentDataOmEktefelle_som_finnes_i_pdl_uten_arbeidsforhold() = runBlockingTest {
        val fnrEktefelle = "10019448164"
        val callId = "12"
        val startDatoForYtelse = LocalDate.of(2020, 1, 1)
        val periode = InputPeriode(startDatoForYtelse, startDatoForYtelse.plusDays(10))

        coEvery { pdlService.hentPersonHistorikkTilEktefelle(fnrEktefelle, startDatoForYtelse, callId) } returns personhistorikkEktefelle(
            fnrEktefelle
        )

        coEvery {
            aaRegService.hentArbeidsforhold(
                fnr = fnrEktefelle,
                fraOgMed = Arbeidsforhold.fraOgMedDatoForArbeidsforhold(startDatoForYtelse),
                tilOgMed = periode.tom,
                callId = callId
            )
        } returns emptyList()

        val ektefelle =
            familieService.hentDataOmEktefelle(fnrEktefelle, callId, periode, startDatoForYtelse)

        assertThat { ektefelle?.personhistorikkEktefelle?.ident == fnrEktefelle }
        assertThat { ektefelle?.arbeidsforholdEktefelle?.isEmpty() }
    }

    @Test
    fun hentDataOmEktefelle_som_finnes_i_pdl_og_har_arbeidsforhold() = runBlockingTest {
        val fnrEktefelle = "10019448164"
        val callId = "12"
        val startDatoForYtelse = LocalDate.of(2020, 1, 1)
        val periode = InputPeriode(startDatoForYtelse, startDatoForYtelse.plusDays(10))

        coEvery { pdlService.hentPersonHistorikkTilEktefelle(fnrEktefelle, startDatoForYtelse, callId) } returns personhistorikkEktefelle(
            fnrEktefelle
        )

        coEvery {
            aaRegService.hentArbeidsforhold(
                fnr = fnrEktefelle,
                fraOgMed = Arbeidsforhold.fraOgMedDatoForArbeidsforhold(startDatoForYtelse),
                tilOgMed = periode.tom,
                callId = callId
            )
        } returns listOf(arbeidsforhold())

        val ektefelle =
            familieService.hentDataOmEktefelle(fnrEktefelle, callId, periode, startDatoForYtelse)

        assertThat { ektefelle?.personhistorikkEktefelle?.ident == fnrEktefelle }
        assertThat { ektefelle?.arbeidsforholdEktefelle?.size == 1 }
    }

    @Test
    fun hentDataOmEktefelle_som_ikke_finnes_i_pdl() = runBlockingTest {
        val fnrEktefelle = "10019448164"
        val callId = "12"
        val startDatoForYtelse = LocalDate.of(2020, 1, 1)
        val periode = InputPeriode(startDatoForYtelse, startDatoForYtelse.plusDays(10))

        coEvery { pdlService.hentPersonHistorikkTilEktefelle(fnrEktefelle, startDatoForYtelse, callId) } returns null

        val ektefelle =
            familieService.hentDataOmEktefelle(fnrEktefelle, callId, periode, startDatoForYtelse)

        assertThat { ektefelle == null }
    }

    private fun personhistorikkBarn(ident: String): PersonhistorikkBarn {
        val barn = PersonhistorikkBarn(ident, listOf(Adresse("NOR", null, null, false)), emptyList(), emptyList(), emptyList())

        return barn
    }

    private fun personhistorikkEktefelle(fnrEktefelle: String): PersonhistorikkEktefelle {
        return PersonhistorikkEktefelle(
            ident = fnrEktefelle,
            barn = emptyList(),
            statsborgerskap = emptyList(),
            bostedsadresser = emptyList(),
            kontaktadresser = emptyList(),
            oppholdsadresser = emptyList()
        )
    }

    private fun arbeidsforhold(): Arbeidsforhold {

        return Arbeidsforhold(
            periode = Periode(null, null),
            utenlandsopphold = null,
            arbeidsgivertype = OpplysningspliktigArbeidsgiverType.Organisasjon,
            arbeidsgiver = Arbeidsgiver("Navn", "123", listOf(Ansatte(5, null)), null, null),
            arbeidsforholdstype = Arbeidsforholdstype.NORMALT,
            arbeidsavtaler = emptyList(),
            permisjonPermittering = null
        )
    }
}
