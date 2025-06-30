package dev.extframework.mdk.resource

import dev.extframework.mixin.api.*
import net.minecraft.client.Minecraft
import net.minecraft.server.packs.repository.PackRepository
import net.minecraft.server.packs.repository.RepositorySource

@Mixin(Minecraft::class)
class PackSourceInjector {
    @InjectCode(
        "<init>",
        point = Select(
            invoke = Invoke(
                PackRepository::class,
                "<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"
            )
        )
    )
    fun injectPackSource(
        stack: Stack
    ) {
        val sources = stack.getRelative<Array<RepositorySource>>(0)

        val value: Array<RepositorySource> = sources + ExtensionRepositorySource()
        stack.replaceLast(value)
    }
}