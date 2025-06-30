package dev.extframework.mdk.keybind

import dev.extframework.core.capability.Capability1
import dev.extframework.core.capability.defining
import dev.extframework.core.entrypoint.Entrypoint
import dev.extframework.core.minecraft.api.TargetCapabilities

class Keybinds : Entrypoint() {
    override fun init() {
        println("INIT?")
    }

    companion object {
        val keybinds = mutableListOf<Keybind>()
        val registerKeybind by TargetCapabilities.defining<Capability1<Keybind, Keybind.Access>>()

        fun registerKeybind(
            bind: Keybind
        ) {
            keybinds.add(bind)
        }
    }
}