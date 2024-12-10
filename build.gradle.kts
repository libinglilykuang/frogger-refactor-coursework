plugins {
    application
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "p4_group_8_repo"
version = "1.0.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.openjfx:javafx-controls:22.0.1")
    implementation("org.openjfx:javafx-fxml:22.0.1")
    implementation("org.openjfx:javafx-media:22.0.1")

    implementation("org.apache.commons:commons-csv:1.12.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("com.pholser:junit-quickcheck-core:1.0")
    testImplementation("com.pholser:junit-quickcheck-generators:1.0")
    testImplementation("org.mockito:mockito-core:5.14.+")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("com.opencsv:opencsv:5.8")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "p4_group_8_repo.view.Main"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

javafx {
    version = "22.0.1"
    modules = listOf("javafx.controls","javafx.fxml","javafx.media" )
}