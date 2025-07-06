@file:JvmName("Init")

package com.kaolinmc.mdk.init

val initCallbacks = ArrayList<() -> Unit>()

fun onInit(callback: () -> Unit) {
    initCallbacks.add(callback)
}