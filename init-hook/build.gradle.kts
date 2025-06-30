import dev.extframework.core.main.main
import dev.extframework.minecraft.MojangNamespaces
import dev.extframework.minecraft.minecraft

extension {
    metadata {
        name = "MDK Init hooks"
        app = "minecraft"
        developers = listOf("extframework")
        description = "A list of hooks into the initialization phase of Minecraft"
    }
    partitions {
        main {
            extensionClass = "dev.extframework.mdk.init.InitHook"
        }
        minecraft("1.21+") {
            mappings = MojangNamespaces.deobfuscated
            dependencies {
                minecraft("1.21")
            }
            supportVersions("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4")
        }
    }
}