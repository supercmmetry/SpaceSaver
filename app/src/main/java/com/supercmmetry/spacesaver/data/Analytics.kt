package com.supercmmetry.spacesaver.data

import android.content.Context
import android.os.StatFs
import android.os.storage.StorageVolume

data class Analytics(private val storageVolume: StorageVolume, private val context: Context) {
    val storageVolumeName = storageVolume.getDescription(context)

    var totalBytes: Long = 0
        private set

    var usedBytes: Long = 0
        private set

    var percentageUsedSpace: Int = 0
        private set

    var mountPoint: String = ""
        private set

    fun generate() {
        mountPoint =
            storageVolume.javaClass.getMethod("getPathFile").invoke(storageVolume).toString()
        val statfs = StatFs(mountPoint)
        totalBytes = statfs.totalBytes
        usedBytes = totalBytes - statfs.freeBytes
        percentageUsedSpace = (100 * usedBytes.toDouble() / totalBytes.toDouble()).toInt()
    }
}