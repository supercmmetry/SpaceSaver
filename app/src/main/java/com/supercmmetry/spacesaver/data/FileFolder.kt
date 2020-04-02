package com.supercmmetry.spacesaver.data

import java.io.File
import java.io.Serializable

data class FileFolder(val path: String) : Serializable {
    var isFile: Boolean = false
        private set

    var inCount: Int = 0
        private set

    var name: String = ""
        private set

    var size: Long = 0L
        private set

    fun getExtension(): String {
        val splArr = name.split(".")
        return splArr[splArr.size - 1]
    }

    init {
        isFile = File(path).isFile
        val file = File(path)
        if (!isFile) {
            inCount = file.walkTopDown().maxDepth(1).filter { it.isFile }.toList().size
        }
        name = file.name
        if (isFile) {
            size = file.length()
        }
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
