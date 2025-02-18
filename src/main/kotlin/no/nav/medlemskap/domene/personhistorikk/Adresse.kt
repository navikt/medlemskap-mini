package no.nav.medlemskap.domene.personhistorikk

import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Periode
import java.time.LocalDate

data class Adresse(
    val landkode: String,
    val fom: LocalDate?,
    val tom: LocalDate?,
    val historisk: Boolean
) {
    private val periode = Periode(fom, tom)

    fun overlapper(annenPeriode: Periode): Boolean {
        return periode.overlapper(annenPeriode)
    }

    companion object {
        fun List<Adresse>.adresserForKontrollperiode(kontrollperiode: Kontrollperiode): List<Adresse> =
            this.filter { it.overlapper(kontrollperiode.periode) }

        fun List<Adresse>.landkodeTilAdresserForKontrollPeriode(kontrollperiode: Kontrollperiode): List<String> =
            this.adresserForKontrollperiode(kontrollperiode).map { it.landkode }

        // Metode som filtrerer vekk to caser hvor bruker har kun en kontaktadresse eller oppholdsadresse:
        // Kontaktadresser/Oppholdsadresser som er historiske og har ingen fom- og tom-dato (ignoreres)
        // Kontaktadresser/Oppholdsadresser som er historiske og fom-dato før 2017 uten tom-dato (ignoreres)
        fun List<Adresse>.landkodeForIkkeHistoriskeAdresserForKontrollperiode(kontrollperiode: Kontrollperiode): List<String> =
            this.adresserForKontrollperiode(kontrollperiode).filterNot {
                it.tom == null && it.historisk && (it.fom == null || it.fom.isBefore(LocalDate.of(2017, 1, 1)))
            }.map { it.landkode }
    }
}
