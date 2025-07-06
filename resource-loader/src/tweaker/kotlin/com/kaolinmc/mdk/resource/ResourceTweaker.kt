package com.kaolinmc.mdk.resource

import com.kaolinmc.archives.ArchiveReference
import com.kaolinmc.core.main.MainPartitionMetadata
import com.kaolinmc.core.minecraft.partition.MinecraftPartitionMetadata
import com.kaolinmc.minecraft.client.api.MinecraftExtensionInitializer
import com.kaolinmc.tooling.api.ExtensionLoader
import com.kaolinmc.tooling.api.environment.ExtensionEnvironment
import com.kaolinmc.tooling.api.extension.ExtensionNode
import com.kaolinmc.tooling.api.extension.partition.ExtensionPartitionContainer
import com.kaolinmc.tooling.api.extension.partition.artifact.PartitionDescriptor
import com.kaolinmc.tooling.api.tweaker.EnvironmentTweaker

class ResourceTweaker : EnvironmentTweaker {
    override fun tweak(environment: ExtensionEnvironment) {
        val delegate = environment[MinecraftExtensionInitializer]
        environment += object : MinecraftExtensionInitializer {
            override suspend fun initialize(nodes: List<ExtensionNode>) {
                delegate.initialize(nodes)

                for (node in nodes.toSet()) {
                    val metadata = environment[ExtensionLoader].graph.nodes
                        .map { it.value.value }
                        .map { it.descriptor }
                        .filterIsInstance<PartitionDescriptor>()
                        .filter { it.extension == node.descriptor }
                        .map { environment[ExtensionLoader].graph.nodes[it]?.value }
                        .filterIsInstance<ExtensionPartitionContainer<*, *>>()
                        .map { it.metadata }

                    val namespace = node.runtimeModel.attributes["minecraft.namespace"] ?: node.descriptor.artifact

                    val refs = namespacedResources.getOrPut(namespace) { ArrayList() }

                    refs.addAll(
                        metadata
                            .filterIsInstance<MinecraftPartitionMetadata>()
                            .mapNotNull { it.archive })

                    refs.addAll(
                        metadata
                            .filterIsInstance<MainPartitionMetadata>()
                            .mapNotNull { it.archive })
                }
            }
        }
    }

    companion object {
        val namespacedResources = HashMap<String, MutableList<ArchiveReference>>()
    }
}