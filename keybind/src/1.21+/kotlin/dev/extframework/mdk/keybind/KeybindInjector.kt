package dev.extframework.mdk.keybind

import dev.extframework.mixin.api.Field
import dev.extframework.mixin.api.FieldAccessType
import dev.extframework.mixin.api.InjectCode
import dev.extframework.mixin.api.Mixin
import dev.extframework.mixin.api.Select
import dev.extframework.mixin.api.Stack
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