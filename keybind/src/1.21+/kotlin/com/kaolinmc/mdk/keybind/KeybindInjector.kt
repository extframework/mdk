package com.kaolinmc.mdk.keybind

import com.kaolinmc.mixin.api.Field
import com.kaolinmc.mixin.api.FieldAccessType
import com.kaolinmc.mixin.api.InjectCode
import com.kaolinmc.mixin.api.Mixin
import com.kaolinmc.mixin.api.Select
import com.kaolinmc.mixin.api.Stack
import net.minecraft.client.KeyMapping
import net.minecraft.client.Options

@Mixin(Options::class)
abstract class KeybindInjector {
    @InjectCode(
        "<init>",
        point = Select(
            field = Field(
                Options::class,
                "keyMappings",
                FieldAccessType.SET
            )
        )
    )
    fun injectMappings(
        stack: Stack
    ) {
        val mappings = Keybinds.keybinds.map {
            (it.access as DefaultKeybindAccess).mapping
        }

        val keys = stack.getRelative<Array<KeyMapping>>(0)
        stack.setRelative(0, keys + mappings)
    }
}