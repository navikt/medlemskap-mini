package no.nav.medlemskap.domene

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packages = ["no.nav.medlemskap"])
class DomeneDependenciesTest {

    @ArchTest
    val `klasser i domenet skal ikke importere eksterne klasser fra baksystemene` =
        noClasses().that().resideInAPackage("no.nav.medlemskap.domene")
            .and().haveNameNotMatching(".*Test.*").should().dependOnClassesThat().resideInAPackage("no.nav.medlemskap.clients..")

    @ArchTest
    val `ingen regler skal resonnere over eksterne klasser fra baksystemene` =
        noClasses().that().resideInAPackage("no.nav.medlemskap.regler..")
            .should().dependOnClassesThat().resideInAPackage("no.nav.medlemskap.clients..")
}
