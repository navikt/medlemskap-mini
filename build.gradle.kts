import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "2.1.3"
val kafkaVersion = "3.3.1"
val jacksonVersion = "2.14.1"
val prometheusVersion = "0.16.0"
val logbackVersion = "1.5.16"
val logstashVersion = "7.2"
val konfigVersion = "1.6.10.0"
val kotlinLoggerVersion = "1.8.3"
val tjenestespesifikasjonerVersion = "1.2019.12.18-12.22-ce897c4eb2c1"
val coroutinesVersion = "1.5.2"
val wireMockVersion = "2.35.0"
val mockkVersion = "1.10.5"
val junitJupiterVersion = "5.7.0"
val assertkVersion = "0.23"
val restAssuredVersion = "4.3.3"
val resilience4jVersion = "2.3.0"
val threetenVersion = "1.5.0"
val kotlinReflectVersion = "1.7.21"
val cucumberVersion = "7.17.0"
val nocommonsVersion = "0.9.0"
val graphqlKotlinClientVersion = "8.3.0"
val archUnitVersion = "0.14.1"
val jsonassertVersion = "1.5.3"
val xmlSchemaVersion = "2.2.5"
val jaxwsToolsVersion = "2.3.1"
val activationVersion = "1.1.1"
val nvi18nVersion = "1.27"
val kotestVersion = "4.2.5"
val swaggerRequestValidatorVersion = "2.33.1"
// Temporary to fix high severity Snyk vulnerabilities:
val nettyVersion = "4.1.116.Final"
val commonsCodecVersion = "3.2.2"
val httpClientVersion = "4.5.14"
val jettyWebAppVersion = "9.4.43.v20210629"
val jacksonDataformatYamlVersion = "2.10.4"
val guavaVersion = "33.4.0-jre"

val mainClass = "no.nav.medlemskap.ApplicationKt"

fun tjenestespesifikasjon(name: String) = "no.nav.tjenestespesifikasjoner:$name:$tjenestespesifikasjonerVersion"

plugins {
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.expediagroup.graphql") version "4.0.0" apply false
    id("com.github.ben-manes.versions") version "0.29.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

val githubUser: String by project
val githubPassword: String by project

allprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://build.shibboleth.net/nexus/content/repositories/releases/")
        maven {
            url = uri("https://maven.pkg.github.com/navikt/tjenestespesifikasjoner")
            credentials {
                username = githubUser
                password = githubPassword
            }
            content {
                excludeGroup("com.expediagroup")
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "20"
    }

    tasks.withType<Wrapper> {
        gradleVersion = "8.6"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(path = ":pdl-client", configuration = "archives"))
    implementation(project(path = ":saf-client", configuration = "archives"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion") {
        exclude(group = "io.netty", module = "netty-codec")
        exclude(group = "io.netty", module = "netty-codec-http")
    }
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-id-jvm:$ktorVersion")
    implementation("com.expediagroup:graphql-kotlin-ktor-client:$graphqlKotlinClientVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:2.1.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.1")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer:$ktorVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:latest.release")
    implementation("io.micrometer:micrometer-registry-influx:latest.release")
    implementation("io.prometheus:simpleclient_hotspot:$prometheusVersion")
    implementation("io.prometheus:simpleclient_common:$prometheusVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("com.natpryce:konfig:$konfigVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggerVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.threeten:threeten-extra:$threetenVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinReflectVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinReflectVersion")
    implementation("javax.activation:activation:$activationVersion")
    implementation("org.apache.ws.xmlschema:xmlschema-core:$xmlSchemaVersion")
    implementation("com.sun.xml.ws:jaxws-tools:$jaxwsToolsVersion") {
        exclude(group = "com.sun.xml.ws", module = "policy")
    }
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation(tjenestespesifikasjon("udi-personstatus-v1"))

    implementation("io.github.resilience4j:resilience4j-retry:$resilience4jVersion")
    implementation("io.github.resilience4j:resilience4j-kotlin:$resilience4jVersion")
    implementation("no.bekk.bekkopen:nocommons:$nocommonsVersion")
    implementation("com.expediagroup:graphql-kotlin-ktor-client:$graphqlKotlinClientVersion")
    implementation("com.neovisionaries:nv-i18n:$nvi18nVersion")

    implementation("commons-collections:commons-collections:$commonsCodecVersion")
    implementation("org.apache.httpcomponents:httpclient:$httpClientVersion")
    implementation("com.google.guava:guava:$guavaVersion")
    testImplementation("org.eclipse.jetty:jetty-webapp:$jettyWebAppVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("com.github.tomakehurst:wiremock-jre8:$wireMockVersion") {
        implementation("commons-fileupload:commons-fileupload:1.5")
        exclude(group = "junit")
        exclude(group = "org.eclipse.jetty", module = "jetty-server")
    }
    testImplementation("io.mockk:mockk:$mockkVersion")
    // testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:$assertkVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")

    testImplementation(platform("io.cucumber:cucumber-bom:7.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testImplementation("com.tngtech.archunit:archunit:$archUnitVersion")
    testImplementation("com.tngtech.archunit:archunit-junit5:$archUnitVersion")
    testImplementation("org.skyscreamer:jsonassert:$jsonassertVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")

    testImplementation("com.atlassian.oai:swagger-request-validator-core:$swaggerRequestValidatorVersion") {
        implementation("org.mozilla:rhino:1.7.14")
        exclude(group = "com.fasterxml.jackson.dataformat", module = "jackson-dataformat-yaml")
    }
    testImplementation("com.atlassian.oai:swagger-request-validator-restassured:$swaggerRequestValidatorVersion")

    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "20"
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<ShadowJar> {
        archiveBaseName.set("app")
        archiveClassifier.set("")
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to mainClass
                )
            )
        }
    }

    test {
        useJUnitPlatform()
        //Trengs inntil videre for bytebuddy med java 16, som brukes av mockk.
        jvmArgs = listOf("-Dnet.bytebuddy.experimental=true")
        java.targetCompatibility = JavaVersion.VERSION_20
        java.sourceCompatibility = JavaVersion.VERSION_20
    }

}
