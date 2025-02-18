package no.nav.medlemskap.clients.udi

/*
class UdiClientTest {

    @Test
    fun `Tester response 200 OK`() {

        val udiRetry = retryRegistry.retry("UDI")
        val udiInterface: MT1067NAVV1Interface = mockk()

        val hentPersonstatusResponseType = mapResponseTilHentPersonstatusResponseType()
        coEvery { udiInterface.hentPersonstatus(any()) } returns hentPersonstatusResponseType

        val udiClient = UdiClient(udiInterface, udiRetry)
        val response = runBlocking { udiClient.hentOppholdstatusResultat("10109000398") }

        Assertions.assertNotNull(response)
        Assertions.assertEquals(OppholdstillatelseKategori.MIDLERTIDIG, response?.gjeldendeOppholdsstatus?.oppholdstillatelseEllerOppholdsPaSammeVilkar?.oppholdstillatelse?.oppholdstillatelseType)
    }

    private fun mapResponseTilHentPersonstatusResponseType(): HentPersonstatusResponseType {
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
*/
