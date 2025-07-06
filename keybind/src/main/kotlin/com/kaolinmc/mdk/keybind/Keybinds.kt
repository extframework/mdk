package com.kaolinmc.mdk.keybind

import com.kaolinmc.core.capability.Capability1
import com.kaolinmc.core.capability.defining
import com.kaolinmc.core.entrypoint.Entrypoint
import com.kaolinmc.core.minecraft.api.TargetCapabilities

class Keybinds : Entrypoint() {
    override fun init() {}

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