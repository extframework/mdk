package com.kaolinmc.mdk.resource

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.MetadataSectionType
import net.minecraft.server.packs.resources.IoSupplier
import java.io.InputStream
import java.nio.file.Paths

class ExtensionPackResources(
    val location: PackLocationInfo,
) : PackResources {
    override fun getRootResource(vararg paths: String): IoSupplier<InputStream>? {
        if (paths.getOrNull(0) == "pack.png") {
            return IoSupplier {
                ResourceLoader.getPackIcon()
            }
        }
        return null
    }

    override fun getResource(
        p0: PackType,
        p1: ResourceLocation
    ): IoSupplier<InputStream>? {
        return null
    }

    override fun listResources(
        type: PackType,
        namespace: String,
        path: String,
        output: PackResources.ResourceOutput
    ) {
        val basePath = Paths.get(type.directory, namespace, path).toString()

        ResourceTweaker.Companion.namespacedResources[namespace]
            ?.flatMap { ref ->
                ref.reader.entries()
                    .filter { it.name.startsWith(basePath) }
                    .filterNot { it.isDirectory }
            }
            ?.forEach {
                val resource = ResourceLocation.fromNamespaceAndPath(
                    namespace,
                    path + "/" + it.name.removePrefix("$basePath/")
                )

                output.accept(
                    resource
                ) {
                    it.open()
                }
            }
    }

    override fun getNamespaces(p0: PackType): Set<String> {
        return ResourceTweaker.Companion.namespacedResources.entries
            .filter { it.value.isNotEmpty() }
            .map { it.key }
            .toSet()
    }

    override fun <T : Any?> getMetadataSection(p0: MetadataSectionType<T?>): T? {
        return null
    }

    override fun location(): PackLocationInfo {
        return this.location
    }

    override fun close() {  }
}