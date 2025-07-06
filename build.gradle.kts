import com.kaolinmc.gradle.common.*
import com.kaolinmc.core.main.main
import com.kaolinmc.kiln.publish.ExtensionPublication
import com.kaolinmc.minecraft.MojangNamespaces
import com.kaolinmc.minecraft.task.LaunchMinecraft
import kotlin.jvm.java

plugins {
    kotlin("jvm") version "2.1.20"
    id("kaolin.kiln") version "0.1"
    id("com.kaolinmc.common") version "0.1"
}

extension {
    metadata {
        name = "Minecraft Development Kit"
        app = "minecraft"
        developers = listOf("kaolin")
        description = "A tooling kit for Minecraft development"
    }
    partitions {
        main {
            extensionClass = "com.kaolinmc.mdk.MinecraftDevelopmentKit"
        }
    }
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
    apply(plugin = "com.kaolinmc.common")
    apply(plugin = "kaolin.kiln")

    group = "com.kaolinmc"
    version = "1.0.1-BETA"

    repositories {
        kaolin()
        mavenCentral()
        mavenLocal()
    }

    kotlin {
        jvmToolchain(8)
    }

    publishing {
        publications {
            create("prod", ExtensionPublication::class.java)
        }
        repositories {
            maven {
                url = uri("https://repo.kaolinmc.com")
                credentials {
                    password = properties["creds.ext.key"] as? String
                }
            }
        }
    }
}
