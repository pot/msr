plugins {
    id("java")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.nateweisz:api")
    implementation("net.minestom:minestom-snapshots:620ebe5d6b")
    implementation("com.palantir.javapoet:javapoet:0.6.0")
    implementation("com.google.auto.service:auto-service:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")
}
