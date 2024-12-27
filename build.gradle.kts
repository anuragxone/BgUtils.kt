plugins {
    kotlin("jvm") version "2.1.0"
}

group = "com.anuragxone.bgutils"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // ktor.io dependencies
    val ktor_version = "3.0.3"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    // ktor logging
    implementation("ch.qos.logback:logback-classic:1.5.15")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}