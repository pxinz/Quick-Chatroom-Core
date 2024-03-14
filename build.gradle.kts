import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.9.22"

plugins {
    kotlin("jvm") version "1.9.22"
    application
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "org.smdc.quickchatroom"
version = "alpha0.01"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.tinylog:tinylog-api:2.6.1")
    implementation("org.tinylog:tinylog-impl:2.6.1")
    implementation("com.alibaba:fastjson:2.0.32")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
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

tasks.dokkaHtml {
}
