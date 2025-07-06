import com.kaolinmc.core.main.main
import com.kaolinmc.gradle.common.mixin
import com.kaolinmc.minecraft.MojangNamespaces
import com.kaolinmc.minecraft.minecraft
import com.kaolinmc.minecraft.task.LaunchMinecraft
import org.gradle.kotlin.dsl.assign

repositories {
    mavenCentral()
}

val launch1_21_4 by tasks.registering(LaunchMinecraft::class) {
    dependsOn(tasks.named("publishToMavenLocal"))
    targetNamespace = MojangNamespaces.deobfuscated.identifier
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
    })
    mcVersion = "1.21.4"
}

dependencies {
}

extension {
    metadata {
        name = "MDK Resource Loader"
        app = "minecraft"
        developers = listOf("kaolin")
        description = "Resource loading for the MDK"
    }
    partitions {
        main {
            extensionClass = "com.kaolinmc.mdk.resource.ResourceLoader"
        }
        tweaker {
            tweakerClass = "com.kaolinmc.mdk.resource.ResourceTweaker"
            dependencies {
                implementation(mixin())
            }
        }
        minecraft("1.21.4+") {
            mappings = MojangNamespaces.deobfuscated
            dependencies {
                minecraft("1.21.4")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}