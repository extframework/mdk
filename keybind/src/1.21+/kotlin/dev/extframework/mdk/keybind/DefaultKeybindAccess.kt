package dev.extframework.mdk.keybind

import net.minecraft.server.Bootstrap

class DefaultKeybindAccess(
    val mapping: ListenableKeyMapping
) : Keybind.Access {
    override val isDown: Boolean
        get() = mapping.isDown

    override fun registerListener(listener: (Boolean) -> Unit) {
        mapping.listeners.add(listener)
    }
}