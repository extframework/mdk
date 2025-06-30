@file:JvmName("Init")

package dev.extframework.mdk.init

val initCallbacks = ArrayList<() -> Unit>()

fun onInit(callback: () -> Unit) {
    initCallbacks.add(callback)
}