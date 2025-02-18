package no.nav.medlemskap.regler.common

object Funksjoner {

    val List<Any>.antall: Number
        get() = size

    infix fun Any?.erDelAv(liste: List<Any>) = liste.contains(this)

    infix fun List<Any>.alleEr(anyObject: Any) = this.all { it == anyObject }

    infix fun List<String>.harAlle(strings: List<String>) = this.all { strings.contains(it) }

    infix fun String?.er(string: String) = this != null && this == string

    infix fun List<String>.inneholderNoe(liste: List<String>) = this.any { it in liste }

    infix fun Map<String, String>.finnesI(liste: List<String>) = this.keys.intersect(liste).isNotEmpty()

    fun List<Any?>?.isNotNullOrEmpty(): Boolean = this != null && this.isNotEmpty()

    fun List<Any>?.erTom() = this == null || this.isNullOrEmpty()

    fun List<Any>?.erIkkeTom() = !erTom()

    fun List<String?>?.finnes() = this != null && this.isNotEmpty()

    infix fun List<Int?>.finnesMindreEnn(tall: Int) = this.any { p -> p == null || p < tall }
}
