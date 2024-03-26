import io.izzel.taboolib.gradle.*


plugins {
    java
    id("io.izzel.taboolib") version "2.0.11"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

taboolib {
    env {
        install(UNIVERSAL)
        install(BUKKIT_ALL)
        install(METRICS)
    }
    description {
        name = "YuBattleMusic"
        desc("A simple plugin for create your own battle music")
        contributors {
            name("L1An")
        }
    }
    version { taboolib = "6.1.1-beta17" }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.withType<Jar> {
    destinationDirectory.set(file("$projectDir/build-jar"))
}