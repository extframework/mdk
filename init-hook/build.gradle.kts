import com.kaolinmc.core.main.main
import com.kaolinmc.minecraft.MojangNamespaces
import com.kaolinmc.minecraft.minecraft

extension {
    metadata {
        name = "MDK Init hooks"
        app = "minecraft"
        developers = listOf("kaolin")
        description = "A list of hooks into the initialization phase of Minecraft"
    }
    partitions {
        main {
            extensionClass = "com.kaolinmc.mdk.init.InitHook"
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