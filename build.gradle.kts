import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71" apply false
    kotlin("plugin.jpa") version "1.3.72"
}

extra["springBootAdminVersion"] = "2.2.3"
extra["springCloudVersion"] = "Hoxton.SR4"
extra["arrowVersion"] = "0.10.4"
extra["mockkVersion"] = "1.10.0"
extra["striktVersion"] = "0.20.0"
extra["kxjtimeVersion"] = "0.1.0"
extra["muLoggingVersion"] = "1.7.7"
extra["assertkVersion"] = "0.10"
extra["mockitoKotlinVersion"] = "1.5.0"

//implementation("io.arrow-kt:arrow-core:$arrowVersion")
//implementation("io.arrow-kt:arrow-syntax:$arrowVersion")

allprojects {
    group = "com.sandjelkovic.dispatchd"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
        jcenter()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
    tasks.withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    repositories {
        mavenCentral()
        jcenter()
//        maven { url = uri("https://repo.spring.io/milestone") }
    }
}
