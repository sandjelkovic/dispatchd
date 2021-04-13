import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.3.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.spring") version "1.4.0" apply false
    kotlin("plugin.jpa") version "1.4.0"
}

extra["springBootAdminVersion"] = "2.3.0"
extra["springCloudVersion"] = "Hoxton.SR8"
extra["arrowVersion"] = "0.10.4"
extra["mockkVersion"] = "1.11.0"
extra["striktVersion"] = "0.27.0"
extra["muLoggingVersion"] = "2.0.6"
extra["assertkVersion"] = "0.10"

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
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "11"
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
    }
}
