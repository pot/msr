plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "dev.nateweisz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.nateweisz:api")
    implementation(project(":processor"))
    annotationProcessor(project(":processor"))
    compileOnly("net.minestom:minestom-snapshots:620ebe5d6b")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}