package no.nav.medlemskap.clients.udi

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.medlemskap.services.udi.UdiMapper.mapTilOppholdstillatelse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import v1.mt_1067_nav.no.udi.HentPersonstatusResponseType

class UdiMapperTest {

    @Test
    fun mapUdiResponseTest() {
        val hentPersonstatusResponseTypeObject = readFromTilHentPersonstatusResponseTypeString()
        val resultat = hentPersonstatusResponseTypeObject.resultat
        val mappedOppholdstillatelser = mapTilOppholdstillatelse(resultat)

        val gjeldendeOppholdsstatus = resultat.gjeldendeOppholdsstatus
        val gjeldendeOppholdsstatusPaSammeVilkar = gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar

        Assertions.assertEquals(resultat.foresporselsfodselsnummer, mappedOppholdstillatelser.foresporselsfodselsnummer)

        val oppholdstillatelsePeriode =
            resultat.gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar.oppholdstillatelsePeriode
        Assertions.assertEquals(mappedOppholdstillatelser.gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.periode?.fom, oppholdstillatelsePeriode.fra.toGregorianCalendar().toZonedDateTime().toLocalDate())
        Assertions.assertEquals(mappedOppholdstillatelser.gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.periode?.tom, oppholdstillatelsePeriode.til.toGregorianCalendar().toZonedDateTime().toLocalDate())
        Assertions.assertNotNull(resultat.gjeldendeOppholdsstatus.oppholdstillatelseEllerOppholdsPaSammeVilkar.oppholdstillatelse.oppholdstillatelseType)
        Assertions.assertTrue(mappedOppholdstillatelser.gjeldendeOppholdsstatus?.oppholdstillatelsePaSammeVilkar?.harTillatelse!!)
    }

    private fun readFromTilHentPersonstatusResponseTypeString(): HentPersonstatusResponseType {

        val xmlMapper = XmlMapper(
            JacksonXmlModule().apply {
                setDefaultUseWrapper(false)
            }
        ).apply {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
        xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)

        return xmlMapper.readValue(utvidetHentPersonResponse)
    }

    private val utvidetHentPersonResponse =
        """
        <ns4:HentUtvidetPersonstatusResponseType xmlns="http://udi.no/MT_1067_NAV_Data/v1" xmlns:ns2="http://udi.no/Common/v2" xmlns:ns4="no.udi.personstatus.v2" xmlns:ns3="http://udi.no.MT_1067_NAV.v1">
            <ns3:Resultat>
                <Uttrekkstidspunkt>2020-12-18T12:16:50.2294816+01:00</Uttrekkstidspunkt>
                <Foresporselsfodselsnummer>17031050804</Foresporselsfodselsnummer>
                <GjeldendePerson>
                    <Fodselsnummer>17031050804</Fodselsnummer>
                    <Navn>
                        <Fornavn>INTELLIGENT</Fornavn>
                        <Etternavn>BUKSESELE</Etternavn>
                    </Navn>
                    <Fodselsdato>
                        <Ar>2010</Ar>
                        <Maned>3</Maned>
                        <Dag>17</Dag>
                    </Fodselsdato>
                    <Alias/>
                </GjeldendePerson>
                <GjeldendeOppholdsstatus>
                    <OppholdstillatelseEllerOppholdsPaSammeVilkar>
                        <OppholdstillatelsePeriode>
                            <Fra>2019-10-03T00:00:00</Fra>
                            <Til>2021-09-10T00:00:00</Til>
                        </OppholdstillatelsePeriode>
                        <Effektueringsdato>2019-10-03T12:25:34</Effektueringsdato>
                        <Oppholdstillatelse>
                            <OppholdstillatelseType>Midlertidig</OppholdstillatelseType>
                            <VedtaksDato>2019-09-13T12:25:33</VedtaksDato>
                        </Oppholdstillatelse>
                    </OppholdstillatelseEllerOppholdsPaSammeVilkar>
                </GjeldendeOppholdsstatus>
                <UavklartFlyktningstatus xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
                <HarFlyktningstatus xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
                <HistorikkHarFlyktningstatus xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
            </ns3:Resultat>
        </ns4:HentUtvidetPersonstatusResponseType>
        """.trimIndent()
}
