package com.supercmmetry.spacesaver.data

import java.io.File

data class FileFolder(val path: String) {
    var isFile: Boolean = false
        private set

    var inCount: Int = 0
        private set

    var name: String = ""
        private set

    init {
        isFile = File(path).isFile
        val file = File(path)
        if (!isFile) {
            inCount = file.walkTopDown().maxDepth(1).toList().size
        }
        name = file.name
    }

    fun getObjects(): List<FileFolder> {
        val ffList = mutableListOf<FileFolder>()
        for (file in File(path).walkTopDown().maxDepth(1).filter { !it.isHidden }.toList()) {
            ffList.add(FileFolder(file.absolutePath))
        }

        return if (ffList.size == 1) {
            listOf()
        } else {
            ffList.subList(1, ffList.size - 1)
        }
    }

}
