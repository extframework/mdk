package com.kaolinmc.mdk.keybind

import com.mojang.blaze3d.platform.InputConstants
import com.kaolinmc.core.entrypoint.Entrypoint
import com.kaolinmc.mdk.keybind.Keybinds.Companion.registerKeybind

class Initializer : Entrypoint() {
    override fun init() {
        registerKeybind += { bind ->
            DefaultKeybindAccess(
                ListenableKeyMapping(
                    bind.name,
                    when (bind.type) {
                        Keybind.Type.KEYBOARD -> InputConstants.Type.KEYSYM
                        Keybind.Type.MOUSE -> InputConstants.Type.MOUSE
                        Keybind.Type.SCAN_CODE -> InputConstants.Type.SCANCODE
                    },
                    bind.key,
                    bind.category
                )
            )
        }
    }
}