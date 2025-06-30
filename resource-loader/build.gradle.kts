import dev.extframework.core.main.main
import dev.extframework.gradle.common.mixin
import dev.extframework.minecraft.MojangNamespaces
import dev.extframework.minecraft.minecraft
import dev.extframework.minecraft.task.LaunchMinecraft
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
    partitions {
        main {
            extensionClass = "dev.extframework.mdk.resource.ResourceLoader"
        }
        tweaker {
            tweakerClass = "dev.extframework.mdk.resource.ResourceTweaker"
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