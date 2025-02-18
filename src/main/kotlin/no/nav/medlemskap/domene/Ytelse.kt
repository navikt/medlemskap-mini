package no.nav.medlemskap.domene

enum class Ytelse {
    SYKEPENGER,
    BARNE_BRILLER,
    DAGPENGER,
    ENSLIG_FORSORGER,
    LOVME,
    LOVME_GCP,
    MEDLEMSKAP_BARN,
    MIN_VEI,
    LOVME_FUNKSJONELLE_TESTER;

    companion object {

        val clientIdToYtelseMap: Map<String, Ytelse> = hashMapOf(
            "a16f1075-4482-4577-baab-d2a7323655fa" to SYKEPENGER, // Prod (skal utgå) - sparkel-medlemskap
            "2a9e52c1-a4f5-41cf-a5ce-b9ec9e83b588" to SYKEPENGER, // Prod:tbd - sparkel-medlemskap
            "4487496e-9ba3-4c3a-a083-f45ca3878775" to SYKEPENGER, // dev
            "bd721a1b-6ee9-4836-ae29-c0a3b6825e61" to DAGPENGER, // Prod - medlemskap-dagpengelytter
            "d13c07e8-5159-4647-a9c1-0790d5bc9af9" to DAGPENGER, // Dev - medlemskap-dagpengelytter
            "4de2bae4-f86a-4c12-aeef-74177c6e724a" to ENSLIG_FORSORGER, // Prod - familie-ef-soknad-api
            "ea03f30a-413b-4f0e-bfae-477aad57de67" to ENSLIG_FORSORGER, // dev
            "496b0ded-cfdb-4430-b7b6-b568504de005" to LOVME, // dev
            "2719da58-489e-4185-9ee6-74b7e93763d2" to LOVME_GCP, // dev
            "d24ecfa9-ffa7-48dc-a9f3-f8ae3138e603" to LOVME_FUNKSJONELLE_TESTER, // dev
            "ee472fd1-3621-4600-a6ac-69d3662e993f" to SYKEPENGER, // sykepenger-lytter-dev
            "60c58925-ad42-45b8-8ce7-0038abfb5dff" to SYKEPENGER, // sykepenger-lytter-prod
            "23600ac9-019c-445d-87a4-2df4996e6f63" to LOVME_GCP, // Prod verifisering
            "27266322-a9c0-4ae5-829a-ba8890e84e19" to BARNE_BRILLER, // dev
            "df226766-a7c2-4372-81e1-354ec75ffd55" to BARNE_BRILLER, // prod
            "35b73682-a318-443d-8557-2e241e3c5ab3" to BARNE_BRILLER, // dev-medlemskap-barn
            "a3482af9-c083-474a-9fc5-35fced197aef" to BARNE_BRILLER, // PROD-medlemskap-barn
            "d176f75c-2b15-4690-8609-718bcc8d5154" to MIN_VEI // DEV minvei
        )

        fun fromClientId(clientId: String?): Ytelse? = clientIdToYtelseMap[clientId]
        fun toMedlemskapClientId(): String = clientIdToYtelseMap.filter { it.value == LOVME }.keys.first()
        fun Ytelse.name(): String = this.name.lowercase().capitalize()
    }
}
