package com.kaolinmc.mdk.keybind

class DefaultKeybindAccess(
    val mapping: ListenableKeyMapping
) : Keybind.Access {
    override val isDown: Boolean
        get() = mapping.isDown

    override fun registerListener(listener: (Boolean) -> Unit) {
        mapping.listeners.add(listener)
    }
}