import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.gradle.DokkaTask
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN

plugins {
    java
    kotlin("jvm") version "1.3.71"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    pmd
    checkstyle
    id("com.dorongold.task-tree") version "1.5"
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
    id("org.jetbrains.dokka") version "0.10.1"
    id("org.sonarqube") version "2.8"
}

group = "ru.adavliatov.gradle-template-kotlin-single"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.7.4")

    // jUnit 5 runner
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")

    // kotest
    runtimeOnly("io.kotest:kotest-core-jvm:4.0.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.1") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.1") // for kotest core jvm assertions
}

pmd {
    isConsoleOutput = true
    toolVersion = "6.21.0"
    rulePriority = 5
//    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
}

checkstyle {
}

sonarqube {
}

detekt {
    failFast = true
    buildUponDefaultConfig = true

    reports {
        xml.enabled = true
        txt.enabled = true
    }
}

ktlint {
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    outputColorName.set("RED")
    enableExperimentalRules.set(true)
    reporters {
        reporter(PLAIN)
        reporter(CHECKSTYLE)
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
        kotlinOptions.allWarningsAsErrors = true
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
        kotlinOptions.allWarningsAsErrors = true
    }
    withType<Test> {
        useJUnitPlatform()
    }

    withType<Detekt> {
        jvmTarget = "11"

        dependsOn("ktlintFormat")
    }
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
        disableAutoconfiguration = false
        cacheRoot = "default"

        configuration {
            moduleName = "template"
            includeNonPublic = false
            skipDeprecated = false
            reportUndocumented = true
            skipEmptyPackages = true

            targets = listOf("JVM")
            platform = "JVM"

            jdkVersion = 11
            noStdlibLink = false
            noJdkLink = false

            perPackageOption {
                prefix = "kotlin"
                skipDeprecated = false
                reportUndocumented = true
                includeNonPublic = false
            }
            perPackageOption {
                prefix = "kotlin.internal"
                suppress = true
            }
        }
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
