package com.kaolinmc.mdk.resource

import com.kaolinmc.core.entrypoint.Entrypoint
import java.io.InputStream

class ResourceLoader : Entrypoint() {
    companion object {
        fun getPackIcon() : InputStream {
            return ResourceLoader::class.java.classLoader.getResourceAsStream("/icon.png")!!
        }
    }

    override fun init() {  }
}