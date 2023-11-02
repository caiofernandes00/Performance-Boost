import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.allopen") version "1.9.10"
    id("io.quarkus")
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

repositories {
    mavenCentral()
    mavenLocal()
}

allprojects {
    group = "org.acme"
    version = "1.0.0-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            javaParameters = true
        }
    }
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    val quarkusPlatformGroupId: String by project
    val quarkusPlatformArtifactId: String by project
    val quarkusPlatformVersion: String by project

    apply {
        plugin("io.quarkus")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.serialization")
    }

    dependencies {
        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("io.quarkus:quarkus-arc")
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-rest-client-reactive-kotlin-serialization")
        implementation("io.quarkus:quarkus-resteasy-reactive-kotlin-serialization")
        implementation("io.quarkus:quarkus-resteasy-reactive-kotlin")
        implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
        implementation("io.quarkus:quarkus-reactive-pg-client")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.quarkus:quarkus-smallrye-health")
        implementation("io.quarkus:quarkus-flyway")

        testImplementation("io.quarkus:quarkus-junit5-mockito")
        testImplementation("io.rest-assured:rest-assured")
        testImplementation("org.jeasy:easy-random:5.0.0")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<Test> {
        systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    }

    allOpen {
        annotation("jakarta.ws.rs.Path")
        annotation("jakarta.enterprise.context.ApplicationScoped")
        annotation("io.quarkus.test.junit.QuarkusTest")
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
    }
}
