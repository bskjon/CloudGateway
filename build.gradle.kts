plugins {
    id("org.springframework.boot") version "2.6.0-M3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    java
    kotlin("plugin.spring") version "1.5.31"
}

group = "no.iktdev.cloud.gateway"
version = "1.0-SNAPSHOT"
base.archivesBaseName = "gateway"

tasks {
    bootJar {
        archiveBaseName.set("gateway")
    }
}


repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.mockito:mockito-core:3.+")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

    implementation("com.google.firebase:firebase-admin:8.1.0")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework:spring-websocket:5.3.11")
    implementation("com.google.code.gson:gson:2.8.7")


}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}