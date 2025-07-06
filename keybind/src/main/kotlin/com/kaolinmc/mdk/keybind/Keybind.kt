package com.kaolinmc.mdk.keybind

import com.kaolinmc.mdk.keybind.Keybinds.Companion.registerKeybind


open class Keybind(
    val name: String,
    val type: Type,
    val key: Int,
    val category: String
) {
    val access: Access = registerKeybind.call(this)

    val isDown: Boolean
        get() = access.isDown

    fun registerListener(
        listener: (Boolean) -> Unit
    ) {
        access.registerListener(listener)
    }

    enum class Type {
        KEYBOARD,
        MOUSE,
        SCAN_CODE
    }

    interface Access {
        val isDown: Boolean

        fun registerListener(
            listener: (Boolean) -> Unit
        )
    }
}