import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "org.smdc.quickchatroom"
version = "alpha0.01"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.tinylog:tinylog-api:2.6.1")
    implementation("org.tinylog:tinylog-impl:2.6.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("org.smdc.quickchatroom.core.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.smdc.quickchatroom.core.MainKt"
    }

    // 包括依赖
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    //
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({ configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) } })
}
