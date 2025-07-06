package com.kaolinmc.mdk.init

import com.kaolinmc.mixin.api.InjectCode
import com.kaolinmc.mixin.api.Invoke
import com.kaolinmc.mixin.api.Mixin
import com.kaolinmc.mixin.api.Select
import net.minecraft.server.Bootstrap
import net.minecraft.world.level.block.FireBlock

@Mixin(Bootstrap::class)
object BootstrapCallbackMixin {
    @InjectCode(
        "bootStrap",
        point = Select(
            invoke = Invoke(
                FireBlock::class,
                method = "bootStrap()V"
            )
        )
    )
    @JvmStatic
    fun run() {
        initCallbacks.forEach { it() }
    }
}