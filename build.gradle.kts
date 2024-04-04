import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// 基础信息
group = "org.smdc.quickchatroom"
archivesName = "core"
version = "alpha0.01"

// 插件
plugins {
    // Kotlin
    kotlin("jvm") version "1.9.22"
    // Run
    application
    // Doc
    id("org.jetbrains.dokka") version "latest.release"
    // Publish
    id("maven-publish")
}

// 仓库
repositories {
    // Maven中心库
    mavenCentral()
//    // Quick-Chatroom-Core
//    maven {
//        name = "GitHubPackages"
//        url = uri("https://maven.pkg.github.com/pxinz/Quick-Chatroom-Core")
//        credentials {
//            username = properties["publish.github.GITHUB_ACTOR"] as String
//            password = properties["publish.github.GITHUB_TOKEN"] as String
//        }
//    }
}

// 依赖项
dependencies {
    // TinyLog 日志记录库
    implementation("org.tinylog:tinylog-api:latest.release")
    implementation("org.tinylog:tinylog-impl:latest.release")
    // FastJson2 json解析库
    implementation("com.alibaba.fastjson2:fastjson2:latest.release")
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

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/pxinz/Quick-Chatroom-Core")
            credentials {
                username = properties["publish.github.GITHUB_ACTOR"] as String
                password = properties["publish.github.GITHUB_TOKEN"] as String
            }
        }
    }

    publications {
        create<MavenPublication>("main") {
            groupId = "org.smdc.quickchatroom"
            artifactId = "core"
            from(components["java"])
        }
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.smdc.quickchatroom.core.MainKt"
    }

    // 包括依赖
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({ configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) } })
}
