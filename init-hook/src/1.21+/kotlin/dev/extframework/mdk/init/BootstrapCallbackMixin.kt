package dev.extframework.mdk.init

import dev.extframework.mixin.api.InjectCode
import dev.extframework.mixin.api.Invoke
import dev.extframework.mixin.api.Mixin
import dev.extframework.mixin.api.Select
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.Bootstrap

@Mixin(Bootstrap::class)
object BootstrapCallbackMixin {
    @InjectCode(
        "bootStrap",
        point = Select(
            invoke = Invoke(
                BuiltInRegistries::class,
                method = "bootStrap()V"
            )
        )
    )
    @JvmStatic
    fun run() {
        initCallbacks.forEach { it() }
    }
}