plugins {
    application
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "uk.ac.nott.cs.comp2013"
version = "1.0.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    implementation("org.openjfx:javafx-controls:22.0.1")
    implementation("org.openjfx:javafx-fxml:22.0.1")
    implementation("org.openjfx:javafx-media:22.0.1")

    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation ("org.testfx:testfx-junit5:4.0.16-alpha")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("com.pholser:junit-quickcheck-core:1.0")
    testImplementation("com.pholser:junit-quickcheck-generators:1.0")

    testImplementation("org.mockito:mockito-core:5.14.+")
    //mockitoAgent("net.bytebuddy:byte-buddy-agent:1.15.4") { isTransitive = false }
    mockitoAgent("org.mockito:mockito-core:5.14.+") { isTransitive = false }
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs(
        "--module-path", configurations.testRuntimeClasspath.get().asPath,
        "--add-modules", "javafx.controls,javafx.fxml,javafx.media",
        "--add-opens", "javafx.graphics/com.sun.javafx.application=ALL-UNNAMED",
        "--add-opens", "javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED",
        "-javaagent:${mockitoAgent.asPath}"
    )
}

application {
    mainClass = "uk.ac.nott.cs.comp2013.froggergame.Main"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

javafx {
    version = "22.0.1"
    modules = listOf("javafx.controls","javafx.fxml","javafx.media" )
}