package dev.extframework.mdk.resource

import dev.extframework.core.entrypoint.Entrypoint
import java.io.InputStream

class ResourceLoader : Entrypoint() {
    companion object {
        fun getPackIcon() : InputStream {
            return ResourceLoader::class.java.classLoader.getResourceAsStream("/icon.png")!!
        }
    }

    override fun init() {  }
}