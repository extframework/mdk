import dev.extframework.core.main.main
import dev.extframework.minecraft.MojangNamespaces
import dev.extframework.minecraft.minecraft

extension {
    partitions {
        main {
            extensionClass = "dev.extframework.mdk.keybind.Keybinds"
        }
        minecraft("1.21+") {
            mappings = MojangNamespaces.deobfuscated
            entrypoint = "dev.extframework.mdk.keybind.Initializer"
            dependencies {
                minecraft("1.21")
            }
            supportVersions("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4")
        }
    }
}