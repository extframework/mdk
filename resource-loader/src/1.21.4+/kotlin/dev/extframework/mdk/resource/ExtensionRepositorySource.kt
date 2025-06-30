package dev.extframework.mdk.resource

import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackSelectionConfig
import net.minecraft.server.packs.repository.Pack
import net.minecraft.server.packs.repository.PackCompatibility
import net.minecraft.server.packs.repository.PackSource
import net.minecraft.server.packs.repository.RepositorySource
import net.minecraft.world.flag.FeatureFlagSet
import java.util.*
import java.util.function.Consumer


class ExtensionRepositorySource : RepositorySource {
    val location = PackLocationInfo(
        "extensions", Component.literal("Extension Resources"), PackSource.create({ it }, true), Optional.empty()
    )

    val resources = ExtensionPackResources(location)

    val pack = Pack(
        location, object : Pack.ResourcesSupplier {
            override fun openPrimary(p0: PackLocationInfo): PackResources {
                return resources
            }

            override fun openFull(
                p0: PackLocationInfo, p1: Pack.Metadata
            ): PackResources {
                return resources
            }
        }, Pack.Metadata(
            Component.literal("All resources for extframework extensions"),
            PackCompatibility.COMPATIBLE,
            FeatureFlagSet.of(),
            mutableListOf(),
        ), PackSelectionConfig(
            true, Pack.Position.BOTTOM, true
        )
    )

    override fun loadPacks(consumer: Consumer<Pack>) {
        consumer.accept(
            pack
        )
    }
}

