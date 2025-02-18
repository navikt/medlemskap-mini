import com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val graphqlKotlinClientVersion = "5.3.1"
val coroutinesVersion = "1.5.2"

plugins {
    kotlin("jvm")
    id("com.expediagroup.graphql")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.expediagroup:graphql-kotlin-ktor-client:$graphqlKotlinClientVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.1")
}

val graphqlGenerateClient by tasks.getting(com.expediagroup.graphql.plugin.gradle.tasks.GraphQLGenerateClientTask::class) {
    packageName.set("no.nav.medlemskap.clients.pdl.generated")
    schemaFile.set(file("${project.projectDir}/src/main/resources/pdl/pdl-api-sdl.graphqls"))
    queryFileDirectory.set("${project.projectDir}/src/main/resources/pdl")
    serializer.set(GraphQLSerializer.KOTLINX)
    allowDeprecatedFields.set(true)
}

java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "20"
    }
}