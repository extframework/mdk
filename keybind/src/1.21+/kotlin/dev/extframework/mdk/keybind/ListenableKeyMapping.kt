package dev.extframework.mdk.keybind

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraft.server.Bootstrap

class ListenableKeyMapping(
    name: String,
    type: InputConstants.Type,
    key: Int,
    category: String
) : KeyMapping(
    name, type, key, category
) {
    val listeners = mutableListOf<(Boolean) -> Unit>()

    override fun isDown(): Boolean {
        return super.isDown()
    }

    override fun setDown(down: Boolean) {
        super.setDown(down)
        listeners.forEach { it(down) }
    }
}