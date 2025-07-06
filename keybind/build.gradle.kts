import com.kaolinmc.core.main.main
import com.kaolinmc.minecraft.MojangNamespaces
import com.kaolinmc.minecraft.minecraft

extension {
    metadata {
        name = "MDK Keybinding"
        app = "minecraft"
        developers = listOf("kaolin")
        description = "The Keybinding API in the MDK"
    }
    partitions {
        main {
            extensionClass = "com.kaolinmc.mdk.keybind.Keybinds"
        }
        minecraft("1.21+") {
            mappings = MojangNamespaces.deobfuscated
            entrypoint = "com.kaolinmc.mdk.keybind.Initializer"
            dependencies {
                minecraft("1.21")
            }
            supportVersions("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4")
        }
    }
}