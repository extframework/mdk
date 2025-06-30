package dev.extframework.mdk.resource

import dev.extframework.archives.ArchiveReference
import dev.extframework.core.main.MainPartitionMetadata
import dev.extframework.core.minecraft.partition.MinecraftPartitionMetadata
import dev.extframework.minecraft.client.api.MinecraftExtensionInitializer
import dev.extframework.tooling.api.ExtensionLoader
import dev.extframework.tooling.api.environment.ExtensionEnvironment
import dev.extframework.tooling.api.extension.ExtensionNode
import dev.extframework.tooling.api.extension.partition.ExtensionPartitionContainer
import dev.extframework.tooling.api.extension.partition.artifact.PartitionDescriptor
import dev.extframework.tooling.api.tweaker.EnvironmentTweaker

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