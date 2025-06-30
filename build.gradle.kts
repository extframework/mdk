import dev.extframework.gradle.common.extFramework
import dev.extframework.gradle.common.mixin
import dev.extframework.core.main.main
import dev.extframework.minecraft.MojangNamespaces
import dev.extframework.minecraft.minecraft
import dev.extframework.minecraft.task.LaunchMinecraft

plugins {
    kotlin("jvm") version "2.1.20"
    id("dev.extframework") version "1.4.1"
    id("dev.extframework.common") version "1.1"
}

extension {
    metadata {
        name = "Minecraft Development Kit"
        app = "minecraft"
        developers = listOf("extframework")
        description = "A tooling kit for Minecraft development"
    }
    partitions {
        main {
            extensionClass = "dev.extframework.mdk.MinecraftDevelopmentKit"
        }
    }
}

dependencyManagement {
    mixin("1.0.3-SNAPSHOT")
}

val exts = listOf(
    ":",
    ":init-hook",
    ":resource-loader",
    ":keybind"
)

val publishAll by tasks.registering {
    exts.forEach { project ->
        dependsOn(project(project).tasks.named("publishExtension"))
    }
}

val publishAllLocally by tasks.registering {
    exts.forEach { project ->
        dependsOn(project(project).tasks.named("publishToMavenLocal"))
    }
}

val launch1_21_4 by tasks.registering(LaunchMinecraft::class) {
    dependsOn(publishAllLocally)
    targetNamespace = MojangNamespaces.deobfuscated.identifier
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
    })
    mcVersion = "1.21.4"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "dev.extframework.common")
    apply(plugin = "dev.extframework")

    group = "dev.extframework"
    version = "1.0-BETA"

    repositories {
        extFramework()
        mavenCentral()
        mavenLocal()
    }

    kotlin {
        jvmToolchain(8)
    }
}
