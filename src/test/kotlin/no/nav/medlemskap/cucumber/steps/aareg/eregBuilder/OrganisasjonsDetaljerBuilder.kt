package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.*
import no.nav.medlemskap.clients.ereg.Organisasjon
import java.time.LocalDate

class OrganisasjonsDetaljerBuilder {

    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()
    var ansattBuilder = AnsattBuilder()
    var navnBuilder = NavnBuilder()
    var mvaBuilder = MVABuilder()
    var statusBuilder = StatusBuilder()
    var enhetstyperBuilder = EnhetstypeBuilder()
    var epostadresseBuilder = EpostadresseBuilder()
    var telefonnummerBuilder = TelefonnummerBuilder()
    var internettadresseBuilder = InternettadresseBuilder()
    var formaalBuilder = FormaalBuilder()
    var forretningsAdresserBuilder = ForetningsadresserBuilder()
    var naeringBuilder = NaeringBuilder()
    var navSpesifikkInformasjonBuilder = NavSpesifikkInformasjonBuilder()
    var hjemlandRegistreBuilder = HjemlandRegistreBuilder()
    var postadresseBulder = PostadresseBuilder()
    var inngarIJuridiskEnhetBuilder = JuridiskEnhetBuilder()
    var underlagtHjemlandLovgivningForetaksformBuilder = UnderlagtHjemlandLovgivningForetaksformBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var ansatte = mutableListOf<Ansatte>(ansattBuilder.build())
    var dublettAv = null
    var navn = mutableListOf<Navn>(navnBuilder.build())
    var registreringsdato = LocalDate.now()
    var registrertMVA = mutableListOf<MVA>(mvaBuilder.build())
    var sistEndret = LocalDate.now()
    var statuser = mutableListOf<Status>(statusBuilder.build())
    var enhetstyper = mutableListOf<Enhetstyper>(enhetstyperBuilder.build())
    var stiftelsesdato = LocalDate.now()
    var epostAdresser = mutableListOf<Epostadresse>(epostadresseBuilder.build())
    var telefonnummer = mutableListOf<Telefonnummer>(telefonnummerBuilder.build())
    var internettadresse = mutableListOf<Internettadresse>(internettadresseBuilder.build())
    var formaal = mutableListOf<Formaal>(formaalBuilder.build())
    var forretningsAdresser = mutableListOf<ForretningsAdresser>(forretningsAdresserBuilder.build())
    var maalform = String()
    var mobiltelefonnummer = mutableListOf<Telefonnummer>(telefonnummerBuilder.build())
    var dubletter = mutableListOf<Organisasjon>()
    var naering = mutableListOf<Naering>(naeringBuilder.build())
    var navSpesifikkInformasjon = navSpesifikkInformasjonBuilder.build()
    var opphoersdato = LocalDate.now()
    var hjemlandregistre = mutableListOf<Hjemlandregistre>(hjemlandRegistreBuilder.build())
    var postAdresse = postadresseBulder.build()
    var telefaksnummer = mutableListOf<Telefonnummer>(telefonnummerBuilder.build())
    var inngaarIJuridiskEnheter = mutableListOf<JuridiskEnhet>(inngarIJuridiskEnhetBuilder.build())
    var underlagtHjemlandLovgivningForetaksform = mutableListOf(underlagtHjemlandLovgivningForetaksformBuilder.build())

    fun build(): Organisasjonsdetaljer =
        Organisasjonsdetaljer(
            ansatte = ansatte,
            dublettAv = dublettAv,
            navn = navn,
            registreringsdato = registreringsdato,
            registrertMVA = registrertMVA,
            sistEndret = sistEndret,
            statuser = statuser,
            enhetstyper = enhetstyper,
            stiftelsesdato = stiftelsesdato,
            epostAdresser = epostAdresser,
            telefonnummer = telefonnummer,
            internettadresser = internettadresse,
            formaal = formaal,
            forretningsAdresser = forretningsAdresser,
            maalform = maalform,
            mobiltelefonnummer = mobiltelefonnummer,
            dubletter = dubletter,
            naeringer = naering,
            navSpesifikkInformasjon = navSpesifikkInformasjon,
            opphoersdato = opphoersdato,
            hjemlandregistre = hjemlandregistre,
            postAdresse = postAdresse,
            telefaksnummer = telefaksnummer,
            inngaarIJuridiskEnheter = inngaarIJuridiskEnheter,
            underlagtHjemlandLovgivningForetaksform = underlagtHjemlandLovgivningForetaksform
        )
}
